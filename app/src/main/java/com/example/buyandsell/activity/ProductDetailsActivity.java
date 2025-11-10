package com.example.buyandsell.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.CountDownTimer;

import com.example.buyandsell.R;
import com.example.buyandsell.data.AppDatabase;
import com.example.buyandsell.data.Converters;
import com.example.buyandsell.data.ProductEntity;
import com.example.buyandsell.adapters.ImagePagerAdapter;

import java.util.List;
import java.util.ArrayList;
import android.net.Uri;
import java.io.File;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ProductDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_PRODUCT_ID = "productId";

    private ViewPager2 viewPager;
    private TextView tvTitle, tvDesc, tvMeta;
    private Button btnBid;
    private CountDownTimer bidTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        viewPager = findViewById(R.id.vp_images);
        tvTitle = findViewById(R.id.tv_title);
        tvDesc = findViewById(R.id.tv_desc);
        tvMeta = findViewById(R.id.tv_meta);
        btnBid = findViewById(R.id.btn_bid);

        btnBid.setEnabled(true);
        btnBid.setOnClickListener(v -> Toast.makeText(this, "Bidding coming soon", Toast.LENGTH_SHORT).show());

        String productId = getIntent().getStringExtra(EXTRA_PRODUCT_ID);
        if (productId == null) { finish(); return; }
        loadProduct(productId);
    }

    private void loadProduct(final String productId) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final ProductEntity product = AppDatabase.getInstance(ProductDetailsActivity.this)
                        .productDao().getById(productId);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (product == null) { finish(); return; }
                        tvTitle.setText(product.title);
                        tvDesc.setText(product.description);
                        String meta = "Used Years: " + product.usedYears +
                                "\nDamages: " + (product.damages == null ? "-" : product.damages) +
                                "\nPickup: " + (product.pickupAddress == null ? "-" : product.pickupAddress);
                        tvMeta.setText(meta);

                        List<String> uris = Converters.toStringList(product.imageUrisJson);
                        ImagePagerAdapter adapter = new ImagePagerAdapter(uris);
                        viewPager.setAdapter(adapter);
                        if (uris == null || uris.isEmpty()) {
                            viewPager.setVisibility(android.view.View.GONE);
                        } else {
                            viewPager.setVisibility(android.view.View.VISIBLE);
                        }

                        // One-time migration: copy external content URIs to internal files
                        if (needsImageMigration(uris)) {
                            AsyncTask.execute(new Runnable() {
                                @Override public void run() {
                                    List<String> migrated = migrateImagesToInternal(uris, product.productId);
                                    if (!migrated.isEmpty()) {
                                        product.imageUrisJson = Converters.fromStringList(migrated);
                                        AppDatabase.getInstance(ProductDetailsActivity.this).productDao().update(product);
                                        runOnUiThread(new Runnable() {
                                            @Override public void run() {
                                                ImagePagerAdapter a = new ImagePagerAdapter(migrated);
                                                viewPager.setAdapter(a);
                                                viewPager.setVisibility(android.view.View.VISIBLE);
                                            }
                                        });
                                    }
                                }
                            });
                        }

                        // Show ongoing bid countdown if applicable
                        if (bidTimer != null) { bidTimer.cancel(); bidTimer = null; }
                        long now = System.currentTimeMillis();
                        if ("live".equalsIgnoreCase(product.status) && product.bidEndTimeEpochMs > now) {
                            long millisRemaining = product.bidEndTimeEpochMs - now;
                            bidTimer = new CountDownTimer(millisRemaining, 1000L) {
                                @Override public void onTick(long ms) {
                                    tvMeta.setText(meta + "\nBid ends in: " + formatDuration(ms));
                                }
                                @Override public void onFinish() {
                                    tvMeta.setText(meta + "\nBid ended");
                                }
                            };
                            bidTimer.start();
                        }
                    }
                });
            }
        });
    }

    private boolean needsImageMigration(List<String> uris) {
        if (uris == null) return false;
        for (String u : uris) {
            if (u == null) continue;
            String s = u.toLowerCase();
            if (s.startsWith("content://")) return true;
        }
        return false;
    }

    private List<String> migrateImagesToInternal(List<String> source, String productId) {
        List<String> stored = new ArrayList<>();
        File dir = new File(getFilesDir(), "product_images/" + productId);
        if (!dir.exists()) dir.mkdirs();
        int index = 0;
        for (String s : source) {
            if (s == null) continue;
            try {
                Uri uri = Uri.parse(s);
                File dst = new File(dir, "img_" + index++ + ".jpg");
                InputStream in = getContentResolver().openInputStream(uri);
                if (in == null) continue;
                OutputStream out = new FileOutputStream(dst);
                byte[] buf = new byte[8192];
                int n;
                while ((n = in.read(buf)) != -1) { out.write(buf, 0, n); }
                out.flush();
                try { in.close(); } catch (Exception ignored) {}
                try { out.close(); } catch (Exception ignored) {}
                stored.add(Uri.fromFile(dst).toString());
            } catch (Exception ignored) {
                // skip bad uri
            }
        }
        return stored;
    }

    private String formatDuration(long ms) {
        long totalSeconds = Math.max(0L, ms / 1000L);
        long hours = totalSeconds / 3600L;
        long minutes = (totalSeconds % 3600L) / 60L;
        long seconds = totalSeconds % 60L;
        String h = hours < 10 ? ("0" + hours) : String.valueOf(hours);
        String m = minutes < 10 ? ("0" + minutes) : String.valueOf(minutes);
        String s = seconds < 10 ? ("0" + seconds) : String.valueOf(seconds);
        return h + ":" + m + ":" + s;
    }

    @Override
    protected void onDestroy() {
        if (bidTimer != null) { bidTimer.cancel(); bidTimer = null; }
        super.onDestroy();
    }
}



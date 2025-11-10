package com.example.buyandsell.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buyandsell.R;
import com.example.buyandsell.data.AppDatabase;
import com.example.buyandsell.data.ProductEntity;
import com.example.buyandsell.data.NotificationEntity;

public class ProductReviewActivity extends AppCompatActivity {

    private TextView tvTitle, tvDesc, tvDetails;
    private RatingBar ratingBar;
    private Button btnSubmit;
    private ProductEntity product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_review);

        tvTitle = findViewById(R.id.tv_title);
        tvDesc = findViewById(R.id.tv_desc);
        tvDetails = findViewById(R.id.tv_details);
        ratingBar = findViewById(R.id.rating_bar);
        btnSubmit = findViewById(R.id.btn_submit);

        String productId = getIntent().getStringExtra("productId");
        loadProduct(productId);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitReview();
            }
        });
    }

    private void loadProduct(final String productId) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                product = AppDatabase.getInstance(ProductReviewActivity.this).productDao().getById(productId);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (product == null) { finish(); return; }
                        tvTitle.setText(product.title);
                        tvDesc.setText(product.description);
                        String d = "Used Years: " + product.usedYears + "\nInvoice: " + (product.invoiceUri == null ? "-" : product.invoiceUri) +
                                "\nDamages: " + product.damages +
                                "\nPayment: " + product.paymentMethod +
                                "\nAddresses: " + product.pickupAddress;
                        tvDetails.setText(d);
                    }
                });
            }
        });
    }

    private void submitReview() {
        final float rating = ratingBar.getRating();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (product == null) return;
                product.adminRating = rating;
                if (rating > 2f) {
                    product.status = "live";
                    product.bidEndTimeEpochMs = System.currentTimeMillis() + 2 * 60 * 60 * 1000; // 2 hours
                } else {
                    product.status = "rejected";
                    product.bidEndTimeEpochMs = 0;
                }
                AppDatabase db = AppDatabase.getInstance(ProductReviewActivity.this);
                db.productDao().update(product);

                if ("live".equals(product.status)) {
                    // Save notification for seller only
                    long now = System.currentTimeMillis();
                    NotificationEntity n = new NotificationEntity();
                    n.recipientId = product.sellerId;
                    n.title = "Your product is live";
                    n.message = "Your product '" + product.title + "' is now available for bid";
                    n.productId = product.productId;
                    n.timestamp = now;
                    db.notificationDao().insert(n);

                    // System notification
                    sendSystemNotification(product.title);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ProductReviewActivity.this, "Review submitted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }

    private void sendSystemNotification(String productTitle) {
        String channelId = "new_products";
        android.app.NotificationManager nm = (android.app.NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            android.app.NotificationChannel ch = new android.app.NotificationChannel(channelId, "New Products", android.app.NotificationManager.IMPORTANCE_DEFAULT);
            nm.createNotificationChannel(ch);
        }
        android.app.Notification notification = new androidx.core.app.NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("New product listed for bid")
                .setContentText(productTitle + " is now live")
                .setAutoCancel(true)
                .build();
        nm.notify((int) System.currentTimeMillis(), notification);
    }
}



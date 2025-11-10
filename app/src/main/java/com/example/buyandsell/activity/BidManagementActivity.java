package com.example.buyandsell.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buyandsell.R;
import com.example.buyandsell.data.AppDatabase;
import com.example.buyandsell.data.BidEntity;
import com.example.buyandsell.data.ProductEntity;

import java.util.ArrayList;
import java.util.List;

public class BidManagementActivity extends AppCompatActivity {

    private TextView tvTitle, tvDesc, tvMeta;
    private RecyclerView rvBids;
    private Button btnRemoveBid;
    private ProductEntity product;
    private BidsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_management);

        tvTitle = findViewById(R.id.tv_title);
        tvDesc = findViewById(R.id.tv_desc);
        tvMeta = findViewById(R.id.tv_meta);
        rvBids = findViewById(R.id.rv_bids);
        btnRemoveBid = findViewById(R.id.btn_remove_bid);
        rvBids.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BidsAdapter(new ArrayList<>());
        rvBids.setAdapter(adapter);

        String productId = getIntent().getStringExtra("productId");
        if (productId == null) { finish(); return; }
        load(productId);

        btnRemoveBid.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { removeOngoingBid(); }
        });
    }

    private void load(final String productId) {
        AsyncTask.execute(new Runnable() {
            @Override public void run() {
                AppDatabase db = AppDatabase.getInstance(BidManagementActivity.this);
                product = db.productDao().getById(productId);
                final List<BidEntity> bids = db.bidDao().getByProduct(productId);
                runOnUiThread(new Runnable() {
                    @Override public void run() {
                        if (product == null) { finish(); return; }
                        tvTitle.setText(product.title);
                        tvDesc.setText(product.description);
                        tvMeta.setText("Used Years: " + product.usedYears + "  |  Payment: " + product.paymentMethod);
                        adapter.setItems(bids);
                    }
                });
            }
        });
    }

    private void removeOngoingBid() {
        if (product == null) return;
        AsyncTask.execute(new Runnable() {
            @Override public void run() {
                AppDatabase db = AppDatabase.getInstance(BidManagementActivity.this);
                // Delete bids and mark product as removed
                db.bidDao().deleteByProduct(product.productId);
                product.status = "removed";
                product.bidEndTimeEpochMs = 0;
                db.productDao().update(product);
                runOnUiThread(new Runnable() {
                    @Override public void run() {
                        Toast.makeText(BidManagementActivity.this, "Bid removed", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }

    private static class BidsAdapter extends RecyclerView.Adapter<BidVH> {
        private List<BidEntity> items;
        BidsAdapter(List<BidEntity> items) { this.items = items; }
        void setItems(List<BidEntity> newItems) { this.items = newItems; notifyDataSetChanged(); }
        @Override public BidVH onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_bid_row, null);
            return new BidVH(v);
        }
        @Override public void onBindViewHolder(BidVH holder, int position) { holder.bind(items.get(position)); }
        @Override public int getItemCount() { return items.size(); }
    }

    private static class BidVH extends RecyclerView.ViewHolder {
        TextView tvBidder, tvAmount, tvTime;
        BidVH(View itemView) {
            super(itemView);
            tvBidder = itemView.findViewById(R.id.tv_bidder);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
        void bind(BidEntity e) {
            tvBidder.setText("Bidder: " + e.bidderId);
            tvAmount.setText("Amount: ₹" + String.format(java.util.Locale.getDefault(), "%.2f", e.amount));
            java.text.DateFormat df = java.text.DateFormat.getDateTimeInstance();
            tvTime.setText(df.format(new java.util.Date(e.createdAtEpochMs)));
        }
    }
}



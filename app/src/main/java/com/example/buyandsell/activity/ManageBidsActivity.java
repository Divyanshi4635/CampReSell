package com.example.buyandsell.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.buyandsell.R;
import com.example.buyandsell.data.AppDatabase;
import com.example.buyandsell.data.ProductEntity;

import java.util.ArrayList;
import java.util.List;

public class ManageBidsActivity extends AppCompatActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, ManageBidsActivity.class);
    }

    private RecyclerView recyclerView;
    private TextView emptyView;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bids);

        recyclerView = findViewById(R.id.rv_live_products);
        emptyView = findViewById(R.id.tv_empty);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        loadLiveProducts();
    }

    private void loadLiveProducts() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List<ProductEntity> list = AppDatabase.getInstance(ManageBidsActivity.this)
                        .productDao().getByStatus("live");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setItems(list);
                        emptyView.setVisibility(list.isEmpty() ? View.VISIBLE : View.GONE);
                    }
                });
            }
        });
    }

    private class Adapter extends RecyclerView.Adapter<VH> {
        private List<ProductEntity> items;
        Adapter(List<ProductEntity> items) { this.items = items; }
        void setItems(List<ProductEntity> newItems) { this.items = newItems; notifyDataSetChanged(); }
        @Override public VH onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
            View v = getLayoutInflater().inflate(R.layout.item_live_product_with_bids, parent, false);
            return new VH(v);
        }
        @Override public void onBindViewHolder(VH holder, int position) { holder.bind(items.get(position)); }
        @Override public int getItemCount() { return items.size(); }
    }

    private class VH extends RecyclerView.ViewHolder {
        TextView tvTitle, tvCount;
        ProductEntity bound;
        VH(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvCount = itemView.findViewById(R.id.tv_bid_count);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent i = new Intent(ManageBidsActivity.this, BidManagementActivity.class);
                    i.putExtra("productId", bound.productId);
                    startActivity(i);
                }
            });
        }
        void bind(ProductEntity e) {
            bound = e;
            tvTitle.setText(e.title);
            AsyncTask.execute(new Runnable() {
                @Override public void run() {
                    final int c = AppDatabase.getInstance(ManageBidsActivity.this).bidDao().countByProduct(e.productId);
                    runOnUiThread(new Runnable() {
                        @Override public void run() { tvCount.setText(c + " bids"); }
                    });
                }
            });
        }
    }
}



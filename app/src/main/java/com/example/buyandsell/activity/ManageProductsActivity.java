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

public class ManageProductsActivity extends AppCompatActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, ManageProductsActivity.class);
    }

    private RecyclerView recyclerView;
    private TextView emptyView;
    private PendingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_products);

        recyclerView = findViewById(R.id.rv_pending_products);
        emptyView = findViewById(R.id.tv_empty);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PendingAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        loadPending();
    }

    private void loadPending() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List<ProductEntity> list = AppDatabase.getInstance(ManageProductsActivity.this)
                        .productDao().getByStatus("pending");
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

    private class PendingAdapter extends RecyclerView.Adapter<PendingViewHolder> {
        private List<ProductEntity> items;
        PendingAdapter(List<ProductEntity> items) { this.items = items; }
        void setItems(List<ProductEntity> newItems) { this.items = newItems; notifyDataSetChanged(); }
        @Override public PendingViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
            View v = getLayoutInflater().inflate(R.layout.item_pending_product, parent, false);
            return new PendingViewHolder(v);
        }
        @Override public void onBindViewHolder(PendingViewHolder holder, int position) { holder.bind(items.get(position)); }
        @Override public int getItemCount() { return items.size(); }
    }

    private class PendingViewHolder extends RecyclerView.ViewHolder {
        TextView title, seller;
        ProductEntity bound;
        PendingViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            seller = itemView.findViewById(R.id.tv_seller);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ManageProductsActivity.this, ProductReviewActivity.class);
                    i.putExtra("productId", bound.productId);
                    startActivity(i);
                }
            });
        }
        void bind(ProductEntity e) {
            bound = e;
            title.setText(e.title);
            seller.setText("Seller: " + e.sellerId);
        }
    }
}



package com.example.buyandsell.adapters;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyandsell.R;
import com.example.buyandsell.data.ProductEntity;

import java.util.List;

public class LiveBidAdapter extends RecyclerView.Adapter<LiveBidAdapter.VH> {

    private List<ProductEntity> items;
    public LiveBidAdapter(List<ProductEntity> items) { this.items = items; }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_bid, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() { return items.size(); }

    public void setItems(List<ProductEntity> list) { this.items = list; notifyDataSetChanged(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView title, countdown;
        Button bidButton;
        CountDownTimer timer;
        VH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            countdown = itemView.findViewById(R.id.tv_countdown);
            bidButton = itemView.findViewById(R.id.btn_bid);
        }

        void bind(ProductEntity e) {
            title.setText(e.title);
            long remaining = Math.max(0, e.bidEndTimeEpochMs - System.currentTimeMillis());
            if (timer != null) timer.cancel();
            timer = new CountDownTimer(remaining, 1000) {
                @Override public void onTick(long millisUntilFinished) {
                    long s = millisUntilFinished / 1000;
                    long h = s / 3600; s %= 3600; long m = s / 60; s = s % 60;
                    countdown.setText(String.format("%02d:%02d:%02d", h, m, s));
                }
                @Override public void onFinish() { countdown.setText("Ended"); }
            };
            timer.start();
            bidButton.setEnabled(false); // disabled per requirements
            com.example.buyandsell.models.Customer cu = com.example.buyandsell.utils.UserManager.getInstance().getCurrentUser();
            if (cu != null && cu.getCustomerId() != null && cu.getCustomerId().equals(e.sellerId)) {
                bidButton.setText("Bid (own product)");
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    android.content.Context ctx = v.getContext();
                    android.content.Intent i = new android.content.Intent(ctx, com.example.buyandsell.activity.ProductDetailsActivity.class);
                    i.putExtra(com.example.buyandsell.activity.ProductDetailsActivity.EXTRA_PRODUCT_ID, e.productId);
                    ctx.startActivity(i);
                }
            });
        }
    }
}



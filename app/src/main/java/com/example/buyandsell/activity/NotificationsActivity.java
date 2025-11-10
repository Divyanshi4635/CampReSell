package com.example.buyandsell.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.buyandsell.R;
import com.example.buyandsell.data.AppDatabase;
import com.example.buyandsell.data.NotificationEntity;
import com.example.buyandsell.utils.UserManager;
import com.example.buyandsell.models.Customer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        recyclerView = findViewById(R.id.rv_notifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        load();
    }

    private void load() {
        AsyncTask.execute(new Runnable() {
            @Override public void run() {
                Customer current = UserManager.getInstance().getCurrentUser();
                String recipientId = current != null ? current.getCustomerId() : "";
                final List<NotificationEntity> list = AppDatabase.getInstance(NotificationsActivity.this)
                        .notificationDao().getByRecipient(recipientId);
                runOnUiThread(new Runnable() {
                    @Override public void run() { adapter.setItems(list); }
                });
            }
        });
    }

    private static class Adapter extends RecyclerView.Adapter<VH> {
        private List<NotificationEntity> items;
        Adapter(List<NotificationEntity> items) { this.items = items; }
        void setItems(List<NotificationEntity> items) { this.items = items; notifyDataSetChanged(); }
        @Override public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
            return new VH(v);
        }
        @Override public void onBindViewHolder(VH holder, int position) { holder.bind(items.get(position)); }
        @Override public int getItemCount() { return items == null ? 0 : items.size(); }
    }

    private static class VH extends RecyclerView.ViewHolder {
        TextView tvTitle, tvMessage, tvTime;
        VH(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvMessage = itemView.findViewById(R.id.tv_message);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
        void bind(NotificationEntity n) {
            tvTitle.setText(n.title);
            tvMessage.setText(n.message);
            String t = new SimpleDateFormat("MMM d, h:mm a", Locale.getDefault()).format(new Date(n.timestamp));
            tvTime.setText(t);
        }
    }
}



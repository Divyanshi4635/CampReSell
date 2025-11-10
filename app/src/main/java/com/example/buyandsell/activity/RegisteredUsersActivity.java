package com.example.buyandsell.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.buyandsell.R;
import com.example.buyandsell.data.AppDatabase;
import com.example.buyandsell.data.CustomerEntity;
import com.example.buyandsell.utils.UserManager;

import java.util.ArrayList;
import java.util.List;

public class RegisteredUsersActivity extends AppCompatActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, RegisteredUsersActivity.class);
    }

    private RecyclerView recyclerView;
    private TextView emptyView;
    private UsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_users);

        if (!UserManager.getInstance().isAdmin()) {
            finish();
            return;
        }

        recyclerView = findViewById(R.id.rv_users);
        emptyView = findViewById(R.id.tv_empty);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UsersAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        loadUsers();
    }

    private void loadUsers() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List<CustomerEntity> list = AppDatabase.getInstance(RegisteredUsersActivity.this)
                        .customerDao().getAll();
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

    private static class UsersAdapter extends RecyclerView.Adapter<UserVH> {
        private List<CustomerEntity> items;
        UsersAdapter(List<CustomerEntity> items) { this.items = items; }
        void setItems(List<CustomerEntity> newItems) { this.items = newItems; notifyDataSetChanged(); }
        @Override public UserVH onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_user_row, null);
            return new UserVH(v);
        }
        @Override public void onBindViewHolder(UserVH holder, int position) { holder.bind(items.get(position)); }
        @Override public int getItemCount() { return items.size(); }
    }

    private static class UserVH extends RecyclerView.ViewHolder {
        TextView tvName, tvEmail, tvId, tvMobile, tvRole;
        UserVH(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvEmail = itemView.findViewById(R.id.tv_email);
            tvId = itemView.findViewById(R.id.tv_id);
            tvMobile = itemView.findViewById(R.id.tv_mobile);
            tvRole = itemView.findViewById(R.id.tv_role);
        }
        void bind(CustomerEntity e) {
            tvName.setText(e.name);
            tvEmail.setText(e.email);
            tvId.setText("ID: " + e.id);
            tvMobile.setText("Mobile: " + e.mobileNumber);
            tvRole.setText(e.isAdmin ? "Admin" : "User");
        }
    }
}



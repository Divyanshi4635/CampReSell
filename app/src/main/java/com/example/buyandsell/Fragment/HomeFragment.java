package com.example.buyandsell.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.buyandsell.R;
import com.example.buyandsell.activity.SettingsActivity;
import com.example.buyandsell.adapters.ProductAdapter;
import com.example.buyandsell.models.Product;
import com.example.buyandsell.utils.ProductDataManager;

import java.util.List;

public class HomeFragment extends Fragment {

    private EditText searchEditText;
    private ImageButton searchButton;
    private ImageButton settingsButton;
    private ImageButton notificationsButton;
    private RecyclerView ongoingBidsRecyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initializeViews(view);
        setupClickListeners();
        setupOngoingBids();

        return view;
    }

    private void initializeViews(View view) {
        searchEditText = view.findViewById(R.id.et_search);
        searchButton = view.findViewById(R.id.btn_search);
        settingsButton = view.findViewById(R.id.btn_settings_header);
        notificationsButton = view.findViewById(R.id.btn_notifications_header);
        ongoingBidsRecyclerView = view.findViewById(R.id.recycler_view_ongoing_bids);
    }

    private void setupClickListeners() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof com.example.buyandsell.activity.MainActivity) {
                    com.example.buyandsell.activity.MainActivity mainActivity = 
                        (com.example.buyandsell.activity.MainActivity) getActivity();
                    if (mainActivity != null) {
                        mainActivity.openSettingsDrawer();
                    }
                }
            }
        });

        notificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    startActivity(new android.content.Intent(getActivity(), com.example.buyandsell.activity.NotificationsActivity.class));
                }
            }
        });
    }

    private void setupOngoingBids() {
        androidx.recyclerview.widget.GridLayoutManager gridLayoutManager = new androidx.recyclerview.widget.GridLayoutManager(getActivity(), 2);
        ongoingBidsRecyclerView.setLayoutManager(gridLayoutManager);

        new android.os.AsyncTask<Void, Void, java.util.List<com.example.buyandsell.data.ProductEntity>>() {
            @Override
            protected java.util.List<com.example.buyandsell.data.ProductEntity> doInBackground(Void... voids) {
                com.example.buyandsell.data.ProductDao dao = com.example.buyandsell.data.AppDatabase
                        .getInstance(requireContext()).productDao();
                java.util.List<com.example.buyandsell.data.ProductEntity> allLive = dao.getByStatus("live");
                java.util.List<com.example.buyandsell.data.ProductEntity> filtered = new java.util.ArrayList<>();
                long now = System.currentTimeMillis();
                for (com.example.buyandsell.data.ProductEntity e : allLive) {
                    if (e.bidEndTimeEpochMs > now) filtered.add(e);
                }
                return filtered;
            }

            @Override
            protected void onPostExecute(java.util.List<com.example.buyandsell.data.ProductEntity> productEntities) {
                com.example.buyandsell.adapters.LiveBidAdapter bidsAdapter = new com.example.buyandsell.adapters.LiveBidAdapter(productEntities);
                ongoingBidsRecyclerView.setAdapter(bidsAdapter);
            }
        }.execute();
    }

    private void performSearch() {
        String searchQuery = searchEditText.getText().toString().trim();

        if (!searchQuery.isEmpty()) {
            if (getActivity() instanceof com.example.buyandsell.activity.MainActivity) {
                com.example.buyandsell.activity.MainActivity mainActivity = 
                    (com.example.buyandsell.activity.MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.searchItem(searchQuery);
                }
            }
        } else {
            Toast.makeText(getActivity(), "Please enter search term", Toast.LENGTH_SHORT).show();
        }
    }
}
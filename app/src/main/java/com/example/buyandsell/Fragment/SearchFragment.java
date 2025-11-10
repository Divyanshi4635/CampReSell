package com.example.buyandsell.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.buyandsell.R;
import com.example.buyandsell.adapters.ProductAdapter;
import com.example.buyandsell.models.Product;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private static final String ARG_SEARCH_QUERY = "search_query";

    private RecyclerView recyclerView;
    private TextView noResultsText;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    private String searchQuery;

    public static SearchFragment newInstance(String searchQuery) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SEARCH_QUERY, searchQuery);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            searchQuery = getArguments().getString(ARG_SEARCH_QUERY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        initializeViews(view);
        setupRecyclerView();
        performSearchOperation();

        return view;
    }

    private void initializeViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_search);
        noResultsText = view.findViewById(R.id.tv_no_results);
    }

    private void setupRecyclerView() {
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(getActivity(), productList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(productAdapter);
    }

    private void performSearchOperation() {
        // Simulate search operation
        List<Product> searchResults = searchItem(searchQuery);

        if (searchResults.isEmpty()) {
            showNoResults();
        } else {
            showSearchResults(searchResults);
        }
    }

    private List<Product> searchItem(String query) {
        // Use ProductDataManager for search
        List<Product> results = new ArrayList<>();
        
        if (query != null && !query.trim().isEmpty()) {
            // Search by name first
            results.addAll(com.example.buyandsell.utils.ProductDataManager.searchByName(query));
            
            // Search by category if no results found
            if (results.isEmpty()) {
                results.addAll(com.example.buyandsell.utils.ProductDataManager.searchByCategory(query));
            }
        }
        
        return results;
    }

    private void showNoResults() {
        recyclerView.setVisibility(View.GONE);
        noResultsText.setVisibility(View.VISIBLE);
        noResultsText.setText("NOT FOUND");
    }

    private void showSearchResults(List<Product> results) {
        recyclerView.setVisibility(View.VISIBLE);
        noResultsText.setVisibility(View.GONE);

        productList.clear();
        productList.addAll(results);
        productAdapter.notifyDataSetChanged();
    }
}
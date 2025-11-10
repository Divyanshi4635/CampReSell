package com.example.buyandsell.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyandsell.R;

import java.util.List;

public class ImagePagerAdapter extends RecyclerView.Adapter<ImagePagerAdapter.VH> {
    private final List<String> imageUris;
    public ImagePagerAdapter(List<String> imageUris) { this.imageUris = imageUris; }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_pager, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        String uriStr = imageUris.get(position);
        if (uriStr == null || uriStr.trim().isEmpty()) {
            holder.imageView.setImageResource(android.R.drawable.ic_menu_report_image);
            return;
        }
        try {
            holder.imageView.setImageURI(Uri.parse(uriStr));
        } catch (Exception e) {
            holder.imageView.setImageResource(android.R.drawable.ic_menu_report_image);
        }
    }

    @Override
    public int getItemCount() {
        return imageUris == null ? 0 : imageUris.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView imageView;
        VH(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_image);
        }
    }
}



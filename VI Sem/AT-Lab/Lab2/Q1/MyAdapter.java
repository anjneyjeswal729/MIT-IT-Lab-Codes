package com.ssc.q1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<String> items;
    private OnItemClickListener listener;

    // Constructor for MyAdapter
    public MyAdapter(List<String> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the item layout for RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // Set the item data
        String item = items.get(position);
        holder.textView.setText(item);

        // Set OnClickListener for the item view
        holder.itemView.setOnClickListener(v -> {
            // Notify the listener (MainActivity) when an item is clicked
            listener.onItemClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // ViewHolder class
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1); // Using a simple TextView layout
        }
    }

    // Interface for handling item click
    public interface OnItemClickListener {
        void onItemClick(String item);
    }
}

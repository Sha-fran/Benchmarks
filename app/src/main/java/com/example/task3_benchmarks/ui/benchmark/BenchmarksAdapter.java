package com.example.task3_benchmarks.ui.benchmark;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task3_benchmarks.databinding.ItemBenchmarkBinding;
import com.example.task3_benchmarks.models.DataBox;

import java.util.ArrayList;
import java.util.List;

public class BenchmarksAdapter extends RecyclerView.Adapter<BenchmarksAdapter.BenchmarksViewHolder> {

    private final List<DataBox> items = new ArrayList<>();

    public List<DataBox> getItems() {
        return items;
    }

    public void setItems(List<DataBox> items) {
        if (this.items.isEmpty()) {
            this.items.addAll(items);
        } else {
            for (int i = 0; i < this.items.size(); i++) {
                this.items.set(i, items.get(i)) ;
            }
        }
    }

    @NonNull
    @Override
    public BenchmarksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ItemBenchmarkBinding binding = ItemBenchmarkBinding.inflate(inflater, parent, false);
        return new BenchmarksViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BenchmarksViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    static class BenchmarksViewHolder extends RecyclerView.ViewHolder {
        private final ItemBenchmarkBinding binding;
        private final ProgressBar progressBar;

        private BenchmarksViewHolder(ItemBenchmarkBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            progressBar = binding.progressCircularBar;
        }
        void bind(DataBox item) {
            if (item.isProgressVisible()) {
                progressBar.setVisibility(View.VISIBLE);
            }

            if (item.text == 0) {
                binding.dataBoxView.setText(String.valueOf(item.time));
                progressBar.setVisibility(View.INVISIBLE);
            } else {
                binding.dataBoxView.setText(item.text);
            }
        }
    }
}

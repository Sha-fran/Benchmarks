package com.example.task3_benchmarks.ui.benchmark;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task3_benchmarks.databinding.ItemBenchmarkBinding;
import com.example.task3_benchmarks.models.DataBox;

import java.util.ArrayList;
import java.util.List;

public class BenchmarksAdapter extends ListAdapter<DataBox, BenchmarksAdapter.BenchmarksViewHolder> {

    private final List<DataBox> items = new ArrayList<>();

    public BenchmarksAdapter() {
        super(new MyDiffutilCallback());
    }

    public List<DataBox> getItems() {
        return items;
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

        private BenchmarksViewHolder(ItemBenchmarkBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(DataBox item) {
            if (item.progressVisible) {
                binding.progressCircularBar.setVisibility(View.VISIBLE);
            }

            if (item.text == 0) {
                binding.dataBoxView.setText(String.valueOf(item.time));
                binding.progressCircularBar.setVisibility(View.INVISIBLE);
            } else {
                binding.dataBoxView.setText(item.text);
            }
        }
    }

    public static class MyDiffutilCallback extends DiffUtil.ItemCallback<DataBox> {

        @Override
        public boolean areItemsTheSame(@NonNull DataBox oldItem, @NonNull DataBox newItem) {
            return oldItem.text == newItem.text;
        }

        @Override
        public boolean areContentsTheSame(@NonNull DataBox oldItem, @NonNull DataBox newItem) {
            return oldItem.equals(newItem);
        }
    }
}

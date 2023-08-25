package com.example.task3_benchmarks.ui.benchmark;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task3_benchmarks.R;
import com.example.task3_benchmarks.databinding.ItemBenchmarkBinding;
import com.example.task3_benchmarks.models.DataBox;

public class BenchmarksAdapter extends ListAdapter<DataBox, BenchmarksAdapter.BenchmarksViewHolder> {

    public BenchmarksAdapter() {
        super(new DiffUtil.ItemCallback<DataBox>() {
            @Override
            public boolean areItemsTheSame(@NonNull DataBox oldItem, @NonNull DataBox newItem) {
                return oldItem.text == newItem.text;
            }

            @Override
            public boolean areContentsTheSame(@NonNull DataBox oldItem, @NonNull DataBox newItem) {
                return oldItem.equals(newItem);
            }
        });
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
        holder.bind(getItem(position));
    }


    static class BenchmarksViewHolder extends RecyclerView.ViewHolder {
        private final ItemBenchmarkBinding binding;

        private BenchmarksViewHolder(ItemBenchmarkBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(DataBox item) {
            binding.dataBoxView.setText(item.text);

            if (item.progressVisible) {
                binding.progressCircularBar.animate().alpha(1).setDuration(500);
                binding.progressCircularBar.setVisibility(View.VISIBLE);

            } else if (item.time >= 0) {
                binding.progressCircularBar.animate().alpha(0).setDuration(500);
                binding.dataBoxView.setText(String.valueOf(item.time));
                binding.progressCircularBar.setVisibility(View.INVISIBLE);
            }
        }
    }
}

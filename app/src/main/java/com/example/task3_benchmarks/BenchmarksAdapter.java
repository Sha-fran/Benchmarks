package com.example.task3_benchmarks;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task3_benchmarks.databinding.DataBoxItemBinding;

import java.util.ArrayList;
import java.util.List;

public class BenchmarksAdapter extends RecyclerView.Adapter<BenchmarksAdapter.BenchmarksViewHolder> {

    private List<DataBox> items = new ArrayList<>();

    public void setItems(List <DataBox> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BenchmarksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        DataBoxItemBinding binding = DataBoxItemBinding.inflate(inflater, parent, false);
        return new BenchmarksViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BenchmarksViewHolder holder, int position) {
        DataBox dataBox = items.get(position);
        holder.bind(dataBox);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class BenchmarksViewHolder extends RecyclerView.ViewHolder {
        private final DataBoxItemBinding binding;

        private BenchmarksViewHolder(DataBoxItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(DataBox item) {
            if (item.text == 0) {
                binding.dataBoxView.setText(String.valueOf(item.time));
            } else {
                binding.dataBoxView.setText(item.text);
            }
        }
    }
}

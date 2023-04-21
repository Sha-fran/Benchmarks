package com.example.task3_benchmarks;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task3_benchmarks.databinding.DataBoxItemBinding;

import java.util.ArrayList;

public class BenchmarksAdapter extends RecyclerView.Adapter<BenchmarksAdapter.BenchmarksViewHolder> {

    private ArrayList<DataBox> textForDataBoxes = new ArrayList<>();

    public void setTextForDataBoxes(ArrayList<DataBox> textForDataBoxes) {
        this.textForDataBoxes = textForDataBoxes;
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
        DataBox dataBox = textForDataBoxes.get(position);
        holder.binding.dataBoxView.setText(dataBox.getText());
    }

    @Override
    public int getItemCount() {
        return textForDataBoxes.size();
    }

    static class BenchmarksViewHolder extends RecyclerView.ViewHolder {

        private final DataBoxItemBinding binding;

        private BenchmarksViewHolder(DataBoxItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

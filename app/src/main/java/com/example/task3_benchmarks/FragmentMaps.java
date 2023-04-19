package com.example.task3_benchmarks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.task3_benchmarks.databinding.FragmentMapsBinding;

import java.util.ArrayList;
import java.util.List;

public class FragmentMaps extends Fragment implements View.OnClickListener {
    private FragmentMapsBinding binding;
    private final BenchmarksAdapter adapter = new BenchmarksAdapter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMapsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonStartFragmentsMaps.setOnClickListener(this);
        binding.rvFrMaps.setAdapter(adapter);
        binding.rvFrMaps.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
        adapter.setTextForDataBoxes((ArrayList<DataBox>) createBenchmarksListMaps());
    }

    private List<DataBox> createBenchmarksListMaps() {
        final List<DataBox> list = new ArrayList<>();

        final String[] textArrayMaps = {
                requireActivity().getString(R.string.adding_new_in_treemap),
                requireActivity().getString(R.string.search_by_key_in_treemap),
                requireActivity().getString(R.string.removing_from_treemap),
                requireActivity().getString(R.string.adding_new_in_hashmap),
                requireActivity().getString(R.string.search_by_key_in_hashmap),
                requireActivity().getString(R.string.removing_from_hashmap)
        };

        for (int i = 0; i < textArrayMaps.length; i++) {
            DataBox dataBox = new DataBox(i, textArrayMaps[i]);
            list.add(dataBox);
        }
        return list;
    }

    @Override
    public void onClick(View view) {
        EditDataDialogFragment.newInstance().show(getChildFragmentManager(), EditDataDialogFragment.TAG);
    }
}

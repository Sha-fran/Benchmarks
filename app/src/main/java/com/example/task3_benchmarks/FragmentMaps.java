package com.example.task3_benchmarks;

import static com.example.task3_benchmarks.EditDataDialogFragment.ENTER_AMOUNT_OF_OPERATIONS;
import static com.example.task3_benchmarks.EditDataDialogFragment.RESULT_OF_AMOUNT_OF_OPERATIONS;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.task3_benchmarks.databinding.FragmentCollectionsBinding;

import java.util.ArrayList;

public class FragmentMaps extends Fragment implements View.OnClickListener {
    private FragmentCollectionsBinding binding;
    private final BenchmarksAdapter adapter = new BenchmarksAdapter();
    private int amountOfOperations;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getChildFragmentManager().setFragmentResultListener(ENTER_AMOUNT_OF_OPERATIONS, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                amountOfOperations = result.getInt(RESULT_OF_AMOUNT_OF_OPERATIONS);
                binding.textInputLayoutCollections.setText(Integer.toString(amountOfOperations));
            }
        });
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCollectionsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonStartFragmentsCollections.setOnClickListener(this);
        binding.rvFrCollections.setAdapter(adapter);
        binding.rvFrCollections.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        adapter.setItems(createBenchmarksListMaps());
    }

    private ArrayList<DataBox> createBenchmarksListMaps() {
        final ArrayList<DataBox> list = new ArrayList<>();

        final int[] textArrayMaps = {
                R.string.adding_new_in_treemap,
                R.string.search_by_key_in_treemap,
                R.string.removing_from_treemap,
                R.string.adding_new_in_hashmap,
                R.string.search_by_key_in_hashmap,
                R.string.removing_from_hashmap
        };

        for (int i = 0; i < textArrayMaps.length; i++) {
            DataBox dataBox = new DataBox((int) textArrayMaps[i], 0);
            list.add(dataBox);
        }
        return list;
    }

    @Override
    public void onClick(View view) {
        EditDataDialogFragment.newInstance().show(getChildFragmentManager(), EditDataDialogFragment.TAG);
    }
}

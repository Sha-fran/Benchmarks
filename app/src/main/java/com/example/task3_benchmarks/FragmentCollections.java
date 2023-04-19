package com.example.task3_benchmarks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.task3_benchmarks.databinding.FragmentCollectionsBinding;

import java.util.ArrayList;
import java.util.List;

public class FragmentCollections extends Fragment implements View.OnClickListener {
    private FragmentCollectionsBinding binding;
    private final BenchmarksAdapter adapter = new BenchmarksAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCollectionsBinding.inflate(inflater, container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonStartFragmentsCollections.setOnClickListener(this);
        binding.rvFrCollections.setAdapter(adapter);
        binding.rvFrCollections.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
        adapter.setTextForDataBoxes( createBenchmarksListCollections());
    }

    private ArrayList<DataBox> createBenchmarksListCollections() {
        final ArrayList<DataBox> list = new ArrayList<>();
        final String[] textArrayCollections = {
            getString(R.string.adding_in_the_beginning_of_arrayList),
            getString(R.string.adding_in_the_middle_of_arrayList),
            getString(R.string.adding_in_the_end_of_arrayList),
            getString(R.string.search_by_value_from_arrayList),
            getString(R.string.removing_in_the_beginning_of_arrayList),
            getString(R.string.removing_in_the_middle_of_arrayList),
            getString(R.string.removing_in_the_end_of_ArrayList),
            getString(R.string.adding_in_the_beginning_of_linkedList),
            getString(R.string.adding_in_the_middle_of_linkedList),
            getString(R.string.adding_in_the_end_of_linkedList),
            getString(R.string.search_by_value_from_linkedList),
            getString(R.string.removing_in_the_beginning_of_linked_list),
            getString(R.string.removing_in_the_middle_of_linked_list),
            getString(R.string.removing_in_the_end_of_linked_list),
            getString(R.string.adding_in_the_beginning_of_copyrightableList),
            getString(R.string.adding_in_the_middle_of_copyrightableList),
            getString(R.string.adding_in_the_end_of_copyrightableList),
            getString(R.string.search_by_value_from_copyrightableList),
            getString(R.string.removing_in_the_beginning_of_copyrightableList),
            getString(R.string.removing_in_the_middle_of_copyrightableList),
            getString(R.string.removing_in_the_end_of_copyrightableList)
        };

        for (int i = 0; i < textArrayCollections.length; i++) {
            DataBox dataBox = new DataBox(i, textArrayCollections[i]);
            list.add(dataBox);
        }
        return list;
    }

    @Override
    public void onClick(View view) {
        EditDataDialogFragment.newInstance().show(getChildFragmentManager(), EditDataDialogFragment.TAG);
    }
}

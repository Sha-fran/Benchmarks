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

public class FragmentCollections extends Fragment implements View.OnClickListener {
    private FragmentCollectionsBinding binding;
    private final BenchmarksAdapter adapter = new BenchmarksAdapter();
    private final ArrayList<DataBox> textForDataBoxes = new ArrayList<>();

    private final String[] textArray = {
        "adding in the beginning of ArrayList",
        "adding in the middle of ArrayList",
        "adding in the end of ArrayList",
        "search by value from ArrayList",
        "removing in the beginning of ArrayList",
        "removing in the middle of ArrayList",
        "removing in the end of ArrayList",
        "adding in the beginning of LinkedList",
        "adding in the middle of LinkedList",
        "adding in the end of LinkedList",
        "search by value from LinkedList",
        "removing in the beginning of LinkedList",
        "removing in the middle of LinkedList",
        "removing in the end of LinkedList",
        "adding in the beginning of CopyOnWriteArrayList",
        "adding in the middle of CopyOnWriteArrayList",
        "adding in the end of CopyOnWriteArrayList",
        "search by value from CopyOnWriteArrayList",
        "removing in the beginning of CopyOnWriteArrayList",
        "removing in the middle of CopyOnWriteArrayList",
        "removing in the end of CopyOnWriteArrayList"
    };

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

        binding.buttonStartFragments.setOnClickListener((View.OnClickListener) this);
        binding.rvFrCollections.setAdapter(adapter);
        binding.rvFrCollections.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
        textBoxesCreation();
        adapter.setTextForDataBoxes(textForDataBoxes);
    }

    public void textBoxesCreation() {
        for (int i = 0; i < 21; i++) {
            DataBox dataBox = new DataBox(i, textArray[i]);
            textForDataBoxes.add(dataBox);
        }
    }

    @Override
    public void onClick(View view) {
        EditDataDialogFragment.newInstance().show(getChildFragmentManager(), EditDataDialogFragment.TAG);
    }
}

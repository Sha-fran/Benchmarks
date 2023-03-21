package com.example.task3_benchmarks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.task3_benchmarks.databinding.FragmentCollectionsBinding;

public class FragmentCollections extends Fragment {
    private FragmentCollectionsBinding binding;
    private Button buttonStart;

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

        buttonStart = binding.buttonStart;

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditDataDialogFragment.newInstance().show(getChildFragmentManager(), EditDataDialogFragment.TAG);
            }
        });
    }
}

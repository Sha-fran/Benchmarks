package com.example.task3_benchmarks.ui.benchmark;

import static com.example.task3_benchmarks.ui.input.EditDataDialogFragment.ENTER_AMOUNT_OF_OPERATIONS;
import static com.example.task3_benchmarks.ui.input.EditDataDialogFragment.RESULT_OF_AMOUNT_OF_OPERATIONS;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.task3_benchmarks.R;
import com.example.task3_benchmarks.databinding.FragmentCollectionsBinding;
import com.example.task3_benchmarks.models.DataBox;
import com.example.task3_benchmarks.ui.input.EditDataDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentCollections extends Fragment implements View.OnClickListener {

    private final BenchmarksAdapter adapter = new BenchmarksAdapter();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private FragmentCollectionsBinding binding;

    private int amountOfOperations;
    private long startTime;
    private char charToAction = 'a';

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getChildFragmentManager().setFragmentResultListener(ENTER_AMOUNT_OF_OPERATIONS,
                this, (requestKey, result) -> handleFragmentResult(requestKey, result)
        );
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
        binding.rvFrCollections.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
        adapter.setItems(createBenchmarkItems());
    }

    private List<DataBox> createBenchmarkItems() {
        final List<DataBox> list = new ArrayList<>();
        final int[] textArrayCollections = {
                R.string.adding_in_the_beginning_of_arrayList,
                R.string.adding_in_the_middle_of_arrayList,
                R.string.adding_in_the_end_of_arrayList,
                R.string.search_by_value_from_arrayList,
                R.string.removing_in_the_beginning_of_arrayList,
                R.string.removing_in_the_middle_of_arrayList,
                R.string.removing_in_the_end_of_ArrayList,
                R.string.adding_in_the_beginning_of_linkedList,
                R.string.adding_in_the_middle_of_linkedList,
                R.string.adding_in_the_end_of_linkedList,
                R.string.search_by_value_from_linkedList,
                R.string.removing_in_the_beginning_of_linked_list,
                R.string.removing_in_the_middle_of_linked_list,
                R.string.removing_in_the_end_of_linked_list,
                R.string.adding_in_the_beginning_of_copyrightableList,
                R.string.adding_in_the_middle_of_copyrightableList,
                R.string.adding_in_the_end_of_copyrightableList,
                R.string.search_by_value_from_copyrightableList,
                R.string.removing_in_the_beginning_of_copyrightableList,
                R.string.removing_in_the_middle_of_copyrightableList,
                R.string.removing_in_the_end_of_copyrightableList
        };

        for (int textArrayCollection : textArrayCollections) {
            DataBox dataBox = new DataBox(textArrayCollection, 0);
            list.add(dataBox);
        }
        return list;
    }

    @Override
    public void onClick(View view) {
        EditDataDialogFragment.newInstance().show(getChildFragmentManager(), EditDataDialogFragment.TAG);
    }

    public void handleFragmentResult(String requestKey, Bundle result) {
        amountOfOperations = result.getInt(RESULT_OF_AMOUNT_OF_OPERATIONS);
        binding.textInputLayoutCollections.setText(Integer.toString(amountOfOperations));
        startTime = System.currentTimeMillis();

        Thread threadAddingInTheBeginningOfArrayList = new Thread(new Runnable() {
            final List<Character> arrayList = new ArrayList<>();

            @Override
            public void run() {
                startTime = System.currentTimeMillis();
                for (int i = 0; i < amountOfOperations; i++) {
                    arrayList.add(i, charToAction);
                }
                long resultTime = System.currentTimeMillis() - startTime;
                handler.post(() -> actionsWithCollections(0, resultTime));
            }
        });
        threadAddingInTheBeginningOfArrayList.start();

        Thread threadAddingInTheMiddleOfArrayList = new Thread(new Runnable() {
            final List<Character> arrayList1 = new ArrayList<>(Arrays.asList('a', 'a'));

            @Override
            public void run() {
                for (int i = 0; i < amountOfOperations; i++) {
                    arrayList1.add(arrayList1.size() / 2, charToAction);
                }
                long resultTime1 = System.currentTimeMillis() - startTime;
                handler.post((() -> actionsWithCollections(1, resultTime1)));
            }
        });
        threadAddingInTheMiddleOfArrayList.start();

        Thread threadAddingInTheEndOfArrayList = new Thread(new Runnable() {
            private final List<Character> arrayList2 = new ArrayList<>(Arrays.asList('a', 'a'));

            @Override
            public void run() {
                for (int i = 0; i < amountOfOperations; i++) {
                    arrayList2.add(arrayList2.size() - 1, charToAction);
                }
                long resultTime2 = System.currentTimeMillis() - startTime;

                handler.post(() -> actionsWithCollections(2, resultTime2));
            }
        });
        threadAddingInTheEndOfArrayList.start();
    }

    public void actionsWithCollections(int index, long resultTime) {
/*
        DataBox dataBox = new DataBox(0, ((int) resultTime));
        listOfDataBoxes.set(index, dataBox);
        adapter.setItems(listOfDataBoxes);

 */
    }
}

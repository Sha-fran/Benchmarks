package com.example.task3_benchmarks;

import static com.example.task3_benchmarks.EditDataDialogFragment.ENTER_AMOUNT_OF_OPERATIONS;
import static com.example.task3_benchmarks.EditDataDialogFragment.RESULT_OF_AMOUNT_OF_OPERATIONS;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.task3_benchmarks.databinding.FragmentCollectionsBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentCollections extends Fragment implements View.OnClickListener {
    private FragmentCollectionsBinding binding;
    private final BenchmarksAdapter adapter = new BenchmarksAdapter();
    private int amountOfOperations;
    private long startTime;
    private char charToAction = 'a';
    private List<DataBox> listOfDataBoxes = createBenchmarksListCollections();
    private Handler handler = new Handler(Looper.getMainLooper());


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getChildFragmentManager().setFragmentResultListener(ENTER_AMOUNT_OF_OPERATIONS, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                handleFragmentResult(requestKey, result);
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
        binding.rvFrCollections.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
        adapter.setItems(listOfDataBoxes);
    }

    private List<DataBox> createBenchmarksListCollections() {
        List<DataBox> list = new ArrayList<>();
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

        for (int i = 0; i < textArrayCollections.length; i++) {
            DataBox dataBox = new DataBox(textArrayCollections[i], 0);
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
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        actionsWithCollections(0, resultTime);
                    }
                });
            }
        });
        threadAddingInTheBeginningOfArrayList.start();

        Thread threadAddingInTheMiddleOfArrayList = new Thread(new Runnable() {
            final List<Character> arrayList1 = new ArrayList<>(Arrays.asList('a', 'a'));

            @Override
            public void run() {
                for (int i = 0; i < amountOfOperations; i++) {
//                    System.out.println(arrayList1.size()/2);
                    arrayList1.add(arrayList1.size()/2, charToAction);
                }
                long resultTime1 = System.currentTimeMillis() - startTime;
                handler.post((new Runnable() {
                    @Override
                    public void run() {
                        actionsWithCollections(1, resultTime1);
                    }
                }));
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

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        actionsWithCollections(2, resultTime2);
                    }
                });
            }
        });
        threadAddingInTheEndOfArrayList.start();
    }

    public void actionsWithCollections(int index, long resultTime) {

        DataBox dataBox = new DataBox(0 ,((int) resultTime) );
        listOfDataBoxes.set(index, dataBox);
        adapter.setItems(listOfDataBoxes);
    }
}

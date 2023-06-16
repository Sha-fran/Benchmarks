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
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FragmentCollections extends Fragment implements View.OnClickListener {

    private final BenchmarksAdapter adapter = new BenchmarksAdapter();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private FragmentCollectionsBinding binding;

    private int amountOfOperations;
    private long startTime;
    final char charToAction = 'a', charToSearch = 'b';
    final List <DataBox> benchmarkItems = createBenchmarkItems();
    final List<Runnable> operations = new ArrayList<>(createOperationsList());
    final List<List> arraysToWork = new ArrayList<>(createArraysToWorkList());
    private int index = 0;

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
        adapter.setItems(benchmarkItems);
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

        for (int i = 0; i < textArrayCollections.length; i++) {
            DataBox dataBox = new DataBox(textArrayCollections[i], (int) System.currentTimeMillis());
            list.add(dataBox);
        }

        return list;
    }

    private List<Runnable> createOperationsList() {
        final ArrayList<Runnable> opList = new ArrayList<>();
        opList.add(() -> arraysToWork.get(0).add(0, charToAction));
        opList.add(() -> arraysToWork.get(1).add(arraysToWork.size() / 2, charToAction));
        opList.add(() -> arraysToWork.get(2).add(arraysToWork.get(0).size() - 1, charToAction));
        opList.add(() -> {
            for ( int i = 0; i < amountOfOperations; i++) {
                if ((char)arraysToWork.get(3).get(i) == charToSearch) {
                    break;
                }
            }
        });
        opList.add(() -> arraysToWork.get(4).remove(0));
        opList.add(() -> arraysToWork.get(5).remove(arraysToWork.get(5).size() / 2));
        opList.add(() -> arraysToWork.get(6).remove(arraysToWork.get(6).size() - 1));

        opList.add(() -> arraysToWork.get(7).add(0, charToAction));
        opList.add(() -> arraysToWork.get(8).add(arraysToWork.get(8).size() / 2, charToAction));
        opList.add(() -> arraysToWork.get(9).add(arraysToWork.get(9).size() - 1, charToAction));
        opList.add(() -> {
            for ( int i = 0; i < amountOfOperations; i++) {
                if ((char)arraysToWork.get(10).get(i) == charToSearch) {
                    break;
                }
            }
        });
        opList.add(() -> arraysToWork.get(11).remove(0));
        opList.add(() -> arraysToWork.get(12).remove(arraysToWork.get(12).size() / 2));
        opList.add(() -> arraysToWork.get(13).remove(arraysToWork.get(13).size() - 1));

        opList.add(() -> arraysToWork.get(14).add(0, charToAction));
        opList.add(() -> arraysToWork.get(15).add(arraysToWork.get(15).size() / 2, charToAction));
        opList.add(() -> arraysToWork.get(16).add(arraysToWork.get(16).size() - 1, charToAction));
        opList.add(() -> {
            for ( int i = 0; i < amountOfOperations; i++) {
                if ((char)arraysToWork.get(17).get(i) == charToSearch) {
                    break;
                }
            }
        });
        opList.add(() -> arraysToWork.get(18).remove(0));
        opList.add(() -> arraysToWork.get(19).remove(arraysToWork.get(19).size() / 2));
        opList.add(() -> arraysToWork.get(20).remove(arraysToWork.get(20).size() - 1));

        return opList;
    }


    public List<List> createArraysToWorkList() {
        final List<List> arrayLists = new ArrayList<>();

        arrayLists.add(new ArrayList<>());
        arrayLists.add(new ArrayList<>(Arrays.asList('a', 'a')));
        arrayLists.add(new ArrayList<>(Arrays.asList('a', 'a')));
        arrayLists.add(new ArrayList<>(arrayListForSearch(amountOfOperations)));
        arrayLists.add(new ArrayList<>(arrayListForSearch(amountOfOperations)));
        arrayLists.add(new ArrayList<>(arrayListForSearch(amountOfOperations)));
        arrayLists.add(new ArrayList<>(arrayListForSearch(amountOfOperations)));

        arrayLists.add(new LinkedList<>());
        arrayLists.add(new LinkedList<>(Arrays.asList('a', 'a')));
        arrayLists.add(new LinkedList<>(Arrays.asList('a', 'a')));
        arrayLists.add(new LinkedList<>(linkedListForSearch(amountOfOperations)));
        arrayLists.add(new LinkedList<>(linkedListForSearch(amountOfOperations)));
        arrayLists.add(new LinkedList<>(linkedListForSearch(amountOfOperations)));
        arrayLists.add(new LinkedList<>(linkedListForSearch(amountOfOperations)));

        arrayLists.add(new CopyOnWriteArrayList<>());
        arrayLists.add(new CopyOnWriteArrayList<>(Arrays.asList('a', 'a')));
        arrayLists.add(new CopyOnWriteArrayList<>(Arrays.asList('a', 'a')));
        arrayLists.add(new CopyOnWriteArrayList<>(new CopyOnWriteArrayList<>(copyOnWriteArrayListForSearch(amountOfOperations))));
        arrayLists.add(new CopyOnWriteArrayList<>(new CopyOnWriteArrayList<>(copyOnWriteArrayListForSearch(amountOfOperations))));
        arrayLists.add(new CopyOnWriteArrayList<>(new CopyOnWriteArrayList<>(copyOnWriteArrayListForSearch(amountOfOperations))));
        arrayLists.add(new CopyOnWriteArrayList<>(new CopyOnWriteArrayList<>(copyOnWriteArrayListForSearch(amountOfOperations))));

        return arrayLists;
    }

    public void onClick(View view) {
        EditDataDialogFragment.newInstance().show(getChildFragmentManager(), EditDataDialogFragment.TAG);
    }

    public void handleFragmentResult(String requestKey, Bundle result) {
        amountOfOperations = result.getInt(RESULT_OF_AMOUNT_OF_OPERATIONS);

        binding.textInputLayoutCollections.setText(Integer.toString(amountOfOperations));

        ExecutorService pool = Executors.newFixedThreadPool(benchmarkItems.size());

        startTime = System.currentTimeMillis();

        for( DataBox iem : benchmarkItems) {
            pool.submit(()->{
                for (int i = 0; i < amountOfOperations; i++) {
                    operations.get(index).run();
                }
                long resultTime = System.currentTimeMillis() - startTime;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        DataBox dataBox = new DataBox(0, ((int) resultTime));
                        createBenchmarkItems().set(index, dataBox);
                        adapter.setItems(benchmarkItems);
                    }
                });
            });
            index++;
        }
        pool.shutdown();
    }
//
//    public void actionsWithCollections(DataBox iem, long resultTime) {
//
//        DataBox dataBox = new DataBox(0, ((int) resultTime));
//        createBenchmarkItems().set(index, dataBox);
//        adapter.setItems(benchmarkItems);
//
//    }

    public List<Character> arrayListForSearch(int amountOfOperations) {
        List <Character> aLFS = new ArrayList<>();
//        Random random = new Random();
//        int indexForSearch = random.nextInt(amountOfOperations - 1);

        for (int i = 0; i < amountOfOperations; i++) {
                aLFS.add(i, charToAction);
        }
//        aLFS.remove(indexForSearch);
//        aLFS.add(indexForSearch, charToSearch);

        return aLFS;
    }

    public List<Character> linkedListForSearch(int amountOfOperations) {
        List <Character> lLFS = new LinkedList<>();

        for (int i = 0; i < amountOfOperations; i++) {
                lLFS.add(i, charToSearch);
        }

        return lLFS;
    }

    public List<Character> copyOnWriteArrayListForSearch(int amountOfOperations) {
        List <Character> cWALFS = new CopyOnWriteArrayList<>();

        for (int i = 0; i < amountOfOperations; i++) {
                cWALFS.add(i, charToAction);
            }

        return cWALFS;
    }
}

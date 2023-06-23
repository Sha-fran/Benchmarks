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
import java.util.Objects;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getChildFragmentManager().setFragmentResultListener(ENTER_AMOUNT_OF_OPERATIONS,
                this, (requestKey, result) -> amountOfOperations = result.getInt(RESULT_OF_AMOUNT_OF_OPERATIONS)
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

        binding.textInputLayoutCollections.setOnClickListener(this);
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
                R.string.removing_in_the_end_of_arrayList,
                R.string.adding_in_the_beginning_of_linkedList,
                R.string.adding_in_the_middle_of_linkedList,
                R.string.adding_in_the_end_of_linkedList,
                R.string.search_by_value_from_linkedList,
                R.string.removing_in_the_beginning_of_linkedlist,
                R.string.removing_in_the_middle_of_linkedlist,
                R.string.removing_in_the_end_of_linkedlist,
                R.string.adding_in_the_beginning_of_copyrightableList,
                R.string.adding_in_the_middle_of_copyrightableList,
                R.string.adding_in_the_end_of_copyrightableList,
                R.string.search_by_value_from_copyrightableList,
                R.string.removing_in_the_beginning_of_copyrightableList,
                R.string.removing_in_the_middle_of_copyrightableList,
                R.string.removing_in_the_end_of_copyrightableList
        };

        for (int i = 0; i < textArrayCollections.length; i++) {
            DataBox dataBox = new DataBox(textArrayCollections[i] , (int) System.currentTimeMillis());
            list.add(dataBox);
        }

        return list;
    }

    public void onClick(View view) {
        if (view == binding.textInputLayoutCollections) {
            EditDataDialogFragment.newInstance().show(getChildFragmentManager(), EditDataDialogFragment.TAG);
            binding.textInputLayoutCollections.setText(Integer.toString(amountOfOperations));
        } else if (view == binding.buttonStartFragmentsCollections) {
            calculationsStart();
            adapter.setItems(benchmarkItems);
            adapter.notifyDataSetChanged();
        }
    }

    public void calculationsStart() {
        ExecutorService pool = Executors.newFixedThreadPool(benchmarkItems.size());
        startTime = System.currentTimeMillis();

        for (DataBox item : benchmarkItems) {
            pool.submit(() -> {
                if (item.text == R.string.adding_in_the_beginning_of_arrayList) {
                    addingInTheBeginningOfArrayList();
                } else if (item.text == R.string.adding_in_the_middle_of_arrayList) {
                    addingInTheMiddleOfArrayList();
                } else if (item.text == R.string.adding_in_the_end_of_arrayList) {
                    addingInTheEndOfArrayList();
                } else if (item.text == R.string.search_by_value_from_arrayList) {
                    searchByValueFromArrayList();
                } else if (item.text == R.string.removing_in_the_beginning_of_arrayList) {
                    removingInTheBeginningOfArrayList();
                } else if (item.text == R.string.removing_in_the_middle_of_arrayList) {
                    removingInTheMiddleOfArrayList();
                } else if (item.text == R.string.removing_in_the_end_of_arrayList) {
                    removingInTheEndOfArrayList();
                } else if (item.text == R.string.adding_in_the_beginning_of_linkedList) {
                    addingInTheBeginningOfLinkedList();
                } else if (item.text == R.string.adding_in_the_middle_of_linkedList) {
                    addingInTheMiddleOfLinkedList();
                } else if (item.text == R.string.adding_in_the_end_of_linkedList) {
                    addingInTheEndOfLinkedList();
                } else if (item.text == R.string.search_by_value_from_linkedList) {
                    searchByValueFromLinkedList();
                } else if (item.text == R.string.removing_in_the_beginning_of_linkedlist) {
                    removingInTheBeginningOfLinkedList();
                } else if (item.text == R.string.removing_in_the_middle_of_linkedlist) {
                    removingInTheMiddleOfLinkedList();
                } else if (item.text == R.string.removing_in_the_end_of_linkedlist) {
                    removingInTheEndOfLinkedList();
                } else if (item.text == R.string.adding_in_the_beginning_of_copyrightableList) {
                    addingInTheBeginningOfCopyrightableList();
                } else if (item.text == R.string.adding_in_the_middle_of_copyrightableList) {
                    addingInTheMiddleOfCopyrightableList();
                } else if (item.text == R.string.adding_in_the_end_of_copyrightableList) {
                    addingInTheEndOfCopyrightableList();
                } else if (item.text == R.string.search_by_value_from_copyrightableList) {
                    searchByValueFromCopyrightableList();
                } else if (item.text == R.string.removing_in_the_beginning_of_copyrightableList) {
                    removingInTheBeginningOfCopyrightableList();
                } else if (item.text == R.string.removing_in_the_middle_of_copyrightableList) {
                    removingInTheMiddleOfCopyrightableList();
                } else {
                    removingInTheEndOfCopyrightableList();
                }
            });
        }
        pool.shutdown();
    }

    public void addingInTheBeginningOfArrayList() {
        List<Character> arrayList = new ArrayList<>();
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            arrayList.add(charToAction);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime);
        benchmarkItems.set(0, dataBox);
        adapter.setItems(benchmarkItems);
    }

    public void addingInTheMiddleOfArrayList() {
        List<Character> arrayList = new ArrayList<>(Arrays.asList('a', 'a'));
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            arrayList.add(arrayList.size()/2, charToAction);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime);
        benchmarkItems.set(1, dataBox);
    }

    public void addingInTheEndOfArrayList() {
        List<Character> arrayList = new ArrayList<>(Arrays.asList('a', 'a'));
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            arrayList.add(arrayList.size() - 1, charToAction);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime);
        benchmarkItems.set(2, dataBox);
    }

    public void searchByValueFromArrayList() {
        List<Character> arrayList = new ArrayList<>(arrayListForSearch(amountOfOperations));
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            if (arrayList.get(i) == charToSearch) {
                return;
            }
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime);
        benchmarkItems.set(3, dataBox);
    }

    public void removingInTheBeginningOfArrayList() {
        List<Character> arrayList = new ArrayList<>(arrayListForSearch(amountOfOperations));
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            arrayList.remove(0);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime);
        benchmarkItems.set(4, dataBox);
    }

    public void removingInTheMiddleOfArrayList() {
        List<Character> arrayList = new ArrayList<>(arrayListForSearch(amountOfOperations));
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            arrayList.remove(arrayList.size()/2);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime);
        benchmarkItems.set(5, dataBox);
    }

    public void removingInTheEndOfArrayList() {
        List<Character> arrayList = new ArrayList<>(arrayListForSearch(amountOfOperations));
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            arrayList.remove(arrayList.size() - 1);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime);
        benchmarkItems.set(6, dataBox);
    }

    public void addingInTheBeginningOfLinkedList() {
        List<Character> linkedList = new LinkedList<>();
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            linkedList.add(charToAction);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime);
        benchmarkItems.set(7, dataBox);
    }

    public void addingInTheMiddleOfLinkedList() {
        List<Character> linkedList = new LinkedList<>(Arrays.asList('a', 'a'));
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            linkedList.add(linkedList.size()/2, charToAction);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime);
        benchmarkItems.set(8, dataBox);
    }

    public void addingInTheEndOfLinkedList() {
        List<Character> linkedList = new LinkedList<>(Arrays.asList('a', 'a'));
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            linkedList.add(linkedList.size() - 1, charToAction);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime);
        benchmarkItems.set(9, dataBox);
    }

    public void searchByValueFromLinkedList() {
        List<Character> linkedList = new LinkedList<>(linkedListForSearch(amountOfOperations));
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            if (linkedList.get(i) == charToSearch) {
                return;
            }
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime);
        benchmarkItems.set(10, dataBox);
    }

    public void removingInTheBeginningOfLinkedList() {
        List<Character> linkedList = new LinkedList<>(linkedListForSearch(amountOfOperations));
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            linkedList.remove(0);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime);
        benchmarkItems.set(11, dataBox);
    }

    public void removingInTheMiddleOfLinkedList() {
        List<Character> linkedList = new LinkedList<>(linkedListForSearch(amountOfOperations));
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            linkedList.remove(linkedList.size()/2);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime);
        benchmarkItems.set(12, dataBox);
    }

    public void removingInTheEndOfLinkedList() {
        List<Character> linkedList = new LinkedList<>(linkedListForSearch(amountOfOperations));
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            linkedList.remove(linkedList.size() - 1);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime);
        benchmarkItems.set(13, dataBox);
    }

    public void addingInTheBeginningOfCopyrightableList() {
        List<Character> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            copyOnWriteArrayList.add(charToAction);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime);
        benchmarkItems.set(14, dataBox);
    }

    public void addingInTheMiddleOfCopyrightableList() {
        List<Character> copyOnWriteArrayList = new CopyOnWriteArrayList<>(Arrays.asList('a', 'a'));
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            copyOnWriteArrayList.add(copyOnWriteArrayList.size()/2, charToAction);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime);
        benchmarkItems.set(15, dataBox);
    }

    public void addingInTheEndOfCopyrightableList() {
        List<Character> copyOnWriteArrayList = new CopyOnWriteArrayList<>(Arrays.asList('a', 'a'));
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            copyOnWriteArrayList.add(copyOnWriteArrayList.size() - 1, charToAction);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime);
        benchmarkItems.set(16, dataBox);
    }

    public void searchByValueFromCopyrightableList() {
        List<Character> copyOnWriteArrayList = new CopyOnWriteArrayList<>(copyOnWriteArrayListForSearch(amountOfOperations));
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            if (copyOnWriteArrayList.get(i) == charToSearch) {
                return;
            }
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime);
        benchmarkItems.set(17, dataBox);
    }

    public void removingInTheBeginningOfCopyrightableList() {
        List<Character> copyOnWriteArrayList = new CopyOnWriteArrayList<>(copyOnWriteArrayListForSearch(amountOfOperations));
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            copyOnWriteArrayList.remove(0);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime);
        benchmarkItems.set(18, dataBox);
    }

    public void removingInTheMiddleOfCopyrightableList() {
        List<Character> copyOnWriteArrayList = new CopyOnWriteArrayList<>(copyOnWriteArrayListForSearch(amountOfOperations));
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            copyOnWriteArrayList.remove(copyOnWriteArrayList.size()/2);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime);
        benchmarkItems.set(19, dataBox);
    }

    public void removingInTheEndOfCopyrightableList() {
        List<Character> copyOnWriteArrayList = new CopyOnWriteArrayList<>(copyOnWriteArrayListForSearch(amountOfOperations));
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            copyOnWriteArrayList.remove(copyOnWriteArrayList.size() - 1);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime);
        benchmarkItems.set(20, dataBox);
    }

    public List<Character> arrayListForSearch(int amountOfOperations) {
        List <Character> aLFS = new ArrayList<>();
//        Random random = new Random();
//        int indexForSearch = random.nextInt(amountOfOperations - 1);

        for (int i = 0; i < amountOfOperations; i++) {
            aLFS.add(i, charToAction);
        }
//        aLFS.set(indexForSearch, charToSearch);

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

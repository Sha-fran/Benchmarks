package com.example.task3_benchmarks.ui.benchmark;

import static com.example.task3_benchmarks.ui.input.EditDataDialogFragment.ENTER_AMOUNT_OF_OPERATIONS;
import static com.example.task3_benchmarks.ui.input.EditDataDialogFragment.RESULT_OF_AMOUNT_OF_OPERATIONS;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
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
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FragmentCollections extends Fragment implements View.OnClickListener {

    private final BenchmarksAdapter adapter = new BenchmarksAdapter();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private FragmentCollectionsBinding binding;
    private final char charToAction = 'a', charToSearch = 'b';
    private ExecutorService pool;
    private static final int NUMBER_OF_CORES = Math.max(1, Runtime.getRuntime().availableProcessors() - 1);


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getChildFragmentManager().setFragmentResultListener(ENTER_AMOUNT_OF_OPERATIONS,
                this,
                (requestKey, result) -> binding.textInputLayoutCollections.setText(
                        Integer.toString(result.getInt(RESULT_OF_AMOUNT_OF_OPERATIONS))
                )
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

        binding.buttonStartStopFragmentsCollections.setText(R.string.start);
        binding.textInputLayoutCollections.setOnClickListener(this);
        binding.buttonStartStopFragmentsCollections.setOnClickListener(this);
        binding.rvFrCollections.setAdapter(adapter);
        binding.rvFrCollections.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
        adapter.submitList(createBenchmarkItems(false));
    }

    private List<DataBox> createBenchmarkItems(boolean showProgress) {
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

        for (int textArrayCollection : textArrayCollections) {
            DataBox dataBox = new DataBox(getString(textArrayCollection), -1, showProgress);
            list.add(dataBox);
        }

        return list;
    }

    public void onClick(View view) {
        if (view == binding.textInputLayoutCollections) {
            EditDataDialogFragment.newInstance().show(getChildFragmentManager(), EditDataDialogFragment.TAG);
        } else if (view == binding.buttonStartStopFragmentsCollections) {
            if (binding.buttonStartStopFragmentsCollections.getText().toString().equals(getString(R.string.ON))) {
                adapter.submitList(createBenchmarkItems(true));
                calculations(Integer.parseInt(binding.textInputLayoutCollections.getText().toString()));
                binding.buttonStartStopFragmentsCollections.setText(R.string.stop);
            } else {
                pool.shutdownNow();
                pool = null;
                binding.buttonStartStopFragmentsCollections.setText(R.string.start);
                adapter.submitList(createBenchmarkItems(false));
            }
        }
    }

    public void calculations(int amountOfCalculation) {
        final List<DataBox> benchmarkItems = createBenchmarkItems(true);
        int index = 0;

        pool = Executors.newFixedThreadPool(NUMBER_OF_CORES);

        for (DataBox item : benchmarkItems) {
            final int currentIndex = index;
            pool.submit(() -> {
                DataBox dataBox = item.copyWithTime((int) measure(item.text, amountOfCalculation));
                benchmarkItems.set(currentIndex, dataBox);
                handler.post(() -> {
                    Log.d("LOGG", "Item update " + currentIndex);
                    adapter.submitList(new ArrayList<>(benchmarkItems));
                });
            });
            index++;
        }
        pool.shutdown();
    }

    public long measure(String itemText, int amountOfCalculation) {
        if (itemText.equals(getString(R.string.adding_in_the_beginning_of_arrayList))) {
            return addingInTheBeginningOfArrayList(amountOfCalculation, new ArrayList<>());
        } else if (itemText.equals(getString(R.string.adding_in_the_middle_of_arrayList))) {
            return addingInTheMiddleOfArrayList(amountOfCalculation, new ArrayList<>());
        } else if (itemText.equals(getString(R.string.adding_in_the_end_of_arrayList))) {
            return addingInTheEndOfArrayList(amountOfCalculation, new ArrayList<>());
        } else if (itemText.equals(getString(R.string.search_by_value_from_arrayList))) {
            return searchByValueFromArrayList(amountOfCalculation, arrayListForSearch(amountOfCalculation));
        } else if (itemText.equals(getString(R.string.removing_in_the_beginning_of_arrayList))) {
            return removingInTheBeginningOfArrayList(amountOfCalculation, arrayListForSearch(amountOfCalculation));
        } else if (itemText.equals(getString(R.string.removing_in_the_middle_of_arrayList)) ) {
            return removingInTheMiddleOfArrayList(amountOfCalculation, arrayListForSearch(amountOfCalculation));
        } else if (itemText.equals(getString(R.string.removing_in_the_end_of_arrayList))) {
            return removingInTheEndOfArrayList(amountOfCalculation, arrayListForSearch(amountOfCalculation));
        } else if (itemText.equals(getString(R.string.adding_in_the_beginning_of_linkedList))) {
            return addingInTheBeginningOfLinkedList(amountOfCalculation, new LinkedList<>());
        } else if (itemText.equals(getString(R.string.adding_in_the_middle_of_linkedList))) {
            return addingInTheMiddleOfLinkedList(amountOfCalculation, new LinkedList<>());
        } else if (itemText.equals(getString(R.string.adding_in_the_end_of_linkedList))) {
            return addingInTheEndOfLinkedList(amountOfCalculation, new LinkedList<>());
        } else if (itemText.equals(getString(R.string.search_by_value_from_linkedList))) {
            searchByValueFromLinkedList(amountOfCalculation, linkedListForSearch(amountOfCalculation));
        } else if (itemText.equals(getString(R.string.removing_in_the_beginning_of_linkedlist))) {
            removingInTheBeginningOfLinkedList(amountOfCalculation, linkedListForSearch(amountOfCalculation));
        } else if (itemText.equals(getString(R.string.removing_in_the_middle_of_linkedlist))) {
            return removingInTheMiddleOfLinkedList(amountOfCalculation, linkedListForSearch(amountOfCalculation));
        } else if (itemText.equals(getString(R.string.removing_in_the_end_of_linkedlist))) {
            return removingInTheEndOfLinkedList(amountOfCalculation, linkedListForSearch(amountOfCalculation));
        } else if (itemText.equals(getString(R.string.adding_in_the_beginning_of_copyrightableList))) {
            return addingInTheBeginningOfCopyrightableList(amountOfCalculation, new CopyOnWriteArrayList<>());
        } else if (itemText.equals(getString(R.string.adding_in_the_middle_of_copyrightableList))) {
            return addingInTheMiddleOfCopyrightableList(amountOfCalculation, new CopyOnWriteArrayList<>());
        } else if (itemText.equals(getString(R.string.adding_in_the_end_of_copyrightableList))) {
            return addingInTheEndOfCopyrightableList(amountOfCalculation, new CopyOnWriteArrayList<>());
        } else if (itemText.equals(getString(R.string.search_by_value_from_copyrightableList))) {
            return searchByValueFromCopyrightableList(amountOfCalculation, copyOnWriteArrayListForSearch(amountOfCalculation));
        } else if (itemText.equals(getString(R.string.removing_in_the_beginning_of_copyrightableList))) {
            return removingInTheBeginningOfCopyrightableList(amountOfCalculation, copyOnWriteArrayListForSearch(amountOfCalculation));
        } else if (itemText.equals(getString(R.string.removing_in_the_middle_of_copyrightableList))) {
            return removingInTheMiddleOfCopyrightableList(amountOfCalculation, copyOnWriteArrayListForSearch(amountOfCalculation));
        }
        return removingInTheEndOfCopyrightableList(amountOfCalculation, copyOnWriteArrayListForSearch(amountOfCalculation));
    }

    public long addingInTheBeginningOfArrayList(int amountOfOperations, List<Character> arrayList) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            arrayList.add(charToAction);
        }
        return System.currentTimeMillis() - startTime;
    }

    public long addingInTheMiddleOfArrayList(int amountOfOperations, List<Character> arrayList) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            arrayList.add(arrayList.size() / 2, charToAction);
        }

        return System.currentTimeMillis() - startTime;
    }

    public long addingInTheEndOfArrayList(int amountOfOperations, List<Character> arrayList) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            arrayList.add(arrayList.size() - 1, charToAction);
        }

        return  System.currentTimeMillis() - startTime;
    }

    public long searchByValueFromArrayList(int amountOfOperations, List<Character> arrayList) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            if (arrayList.get(i) == charToSearch) {
                return System.currentTimeMillis() - startTime;
            }
        }

        return System.currentTimeMillis() - startTime;
    }

    public long removingInTheBeginningOfArrayList(int amountOfOperations, List<Character> arrayList) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            arrayList.remove(0);
        }

        return System.currentTimeMillis() - startTime;
    }

    public long removingInTheMiddleOfArrayList(int amountOfOperations, List<Character> arrayList) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            arrayList.remove(arrayList.size() / 2);
        }

        return System.currentTimeMillis() - startTime;
    }

    public long removingInTheEndOfArrayList(int amountOfOperations, List<Character> arrayList) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            arrayList.remove(arrayList.size() - 1);
        }

        return System.currentTimeMillis() - startTime;
    }

    public long addingInTheBeginningOfLinkedList(int amountOfOperations, List<Character> linkedList) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            linkedList.add(charToAction);
        }

        return System.currentTimeMillis() - startTime;
    }

    public long addingInTheMiddleOfLinkedList(int amountOfOperations, List<Character> linkedList) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            linkedList.add(linkedList.size() / 2, charToAction);
        }

        return System.currentTimeMillis() - startTime;
    }

    public long addingInTheEndOfLinkedList(int amountOfOperations, List<Character> linkedList) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            linkedList.add(linkedList.size() - 1, charToAction);
        }

        return System.currentTimeMillis() - startTime;
    }

    public long searchByValueFromLinkedList(int amountOfOperations, List<Character> linkedList) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            if (linkedList.get(i) == charToSearch) {
                return System.currentTimeMillis() - startTime;
            }
        }

        return System.currentTimeMillis() - startTime;
    }

    public long removingInTheBeginningOfLinkedList(int amountOfOperations, List<Character> linkedList) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            linkedList.remove(0);
        }

        return System.currentTimeMillis() - startTime;
    }

    public long removingInTheMiddleOfLinkedList(int amountOfOperations, List<Character> linkedList) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            linkedList.remove(linkedList.size() / 2);
        }

        return System.currentTimeMillis() - startTime;
    }

    public long removingInTheEndOfLinkedList(int amountOfOperations, List<Character> linkedList) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            linkedList.remove(linkedList.size() - 1);
        }

        return System.currentTimeMillis() - startTime;
    }

    public long addingInTheBeginningOfCopyrightableList(int amountOfOperations, List<Character> copyOnWriteArrayList) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            copyOnWriteArrayList.add(charToAction);
        }

        return System.currentTimeMillis() - startTime;
    }

    public long addingInTheMiddleOfCopyrightableList(int amountOfOperations, List<Character> copyOnWriteArrayList) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            copyOnWriteArrayList.add(copyOnWriteArrayList.size() / 2, charToAction);
        }

        return System.currentTimeMillis() - startTime;
    }

    public long addingInTheEndOfCopyrightableList(int amountOfOperations, List<Character> copyOnWriteArrayList) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            copyOnWriteArrayList.add(copyOnWriteArrayList.size() - 1, charToAction);
        }

        return System.currentTimeMillis() - startTime;
    }

    public long searchByValueFromCopyrightableList(int amountOfOperations, List<Character> copyOnWriteArrayList) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            if (copyOnWriteArrayList.get(i) == charToSearch) {
                return System.currentTimeMillis() - startTime;
            }
        }

        return System.currentTimeMillis() - startTime;
    }

    public long removingInTheBeginningOfCopyrightableList(int amountOfOperations, List<Character> copyOnWriteArrayList) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            copyOnWriteArrayList.remove(0);
        }

        return System.currentTimeMillis() - startTime;
    }

    public long removingInTheMiddleOfCopyrightableList(int amountOfOperations, List<Character> copyOnWriteArrayList) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            copyOnWriteArrayList.remove(copyOnWriteArrayList.size() / 2);
        }

        return System.currentTimeMillis() - startTime;
    }

    public long removingInTheEndOfCopyrightableList(int amountOfOperations, List<Character> copyOnWriteArrayList) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            copyOnWriteArrayList.remove(copyOnWriteArrayList.size() - 1);
        }

        return System.currentTimeMillis() - startTime;
    }

    public List<Character> arrayListForSearch(int amountOfOperations) {
        List<Character> aLFS = new ArrayList<>();

        for (int i = 0; i < amountOfOperations; i++) {
            aLFS.add(i, charToAction);
        }

        return aLFS;
    }

    public List<Character> linkedListForSearch(int amountOfOperations) {
        List<Character> lLFS = new LinkedList<>();

        for (int i = 0; i < amountOfOperations; i++) {
            lLFS.add(i, charToAction);
        }

        return lLFS;
    }

    public List<Character> copyOnWriteArrayListForSearch(int amountOfOperations) {
        List<Character> cWALFS = new CopyOnWriteArrayList<>();

        for (int i = 0; i < amountOfOperations; i++) {
            cWALFS.add(i, charToAction);
        }

        return cWALFS;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getParentFragmentManager().clearFragmentResultListener(RESULT_OF_AMOUNT_OF_OPERATIONS);
        binding = null;
    }
}

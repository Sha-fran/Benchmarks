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

public class FragmentCollections extends Fragment implements View.OnClickListener, OperationsCollections {

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
            return addingInTheBeginning(amountOfCalculation, new ArrayList<>());
        } else if (itemText.equals(getString(R.string.adding_in_the_middle_of_arrayList))) {
            return addingInTheMiddle(amountOfCalculation, new ArrayList<>());
        } else if (itemText.equals(getString(R.string.adding_in_the_end_of_arrayList))) {
            return addingInTheEnd(amountOfCalculation, new ArrayList<>());
        } else if (itemText.equals(getString(R.string.search_by_value_from_arrayList))) {
            return searchByValue(amountOfCalculation, arrayListForSearch(amountOfCalculation));
        } else if (itemText.equals(getString(R.string.removing_in_the_beginning_of_arrayList))) {
            return removingInTheBeginning(amountOfCalculation, arrayListForSearch(amountOfCalculation));
        } else if (itemText.equals(getString(R.string.removing_in_the_middle_of_arrayList)) ) {
            return removingInTheMiddle(amountOfCalculation, arrayListForSearch(amountOfCalculation));
        } else if (itemText.equals(getString(R.string.removing_in_the_end_of_arrayList))) {
            return removingInTheEnd(amountOfCalculation, arrayListForSearch(amountOfCalculation));
        } else if (itemText.equals(getString(R.string.adding_in_the_beginning_of_linkedList))) {
            return addingInTheBeginning(amountOfCalculation, new LinkedList<>());
        } else if (itemText.equals(getString(R.string.adding_in_the_middle_of_linkedList))) {
            return addingInTheMiddle(amountOfCalculation, new LinkedList<>());
        } else if (itemText.equals(getString(R.string.adding_in_the_end_of_linkedList))) {
            return addingInTheEnd(amountOfCalculation, new LinkedList<>());
        } else if (itemText.equals(getString(R.string.search_by_value_from_linkedList))) {
            searchByValue(amountOfCalculation, linkedListForSearch(amountOfCalculation));
        } else if (itemText.equals(getString(R.string.removing_in_the_beginning_of_linkedlist))) {
            removingInTheBeginning(amountOfCalculation, linkedListForSearch(amountOfCalculation));
        } else if (itemText.equals(getString(R.string.removing_in_the_middle_of_linkedlist))) {
            return removingInTheMiddle(amountOfCalculation, linkedListForSearch(amountOfCalculation));
        } else if (itemText.equals(getString(R.string.removing_in_the_end_of_linkedlist))) {
            return removingInTheEnd(amountOfCalculation, linkedListForSearch(amountOfCalculation));
        } else if (itemText.equals(getString(R.string.adding_in_the_beginning_of_copyrightableList))) {
            return addingInTheBeginning(amountOfCalculation, new CopyOnWriteArrayList<>());
        } else if (itemText.equals(getString(R.string.adding_in_the_middle_of_copyrightableList))) {
            return addingInTheMiddle(amountOfCalculation, new CopyOnWriteArrayList<>());
        } else if (itemText.equals(getString(R.string.adding_in_the_end_of_copyrightableList))) {
            return addingInTheEnd(amountOfCalculation, new CopyOnWriteArrayList<>());
        } else if (itemText.equals(getString(R.string.search_by_value_from_copyrightableList))) {
            return searchByValue(amountOfCalculation, copyOnWriteArrayListForSearch(amountOfCalculation));
        } else if (itemText.equals(getString(R.string.removing_in_the_beginning_of_copyrightableList))) {
            return removingInTheBeginning(amountOfCalculation, copyOnWriteArrayListForSearch(amountOfCalculation));
        } else if (itemText.equals(getString(R.string.removing_in_the_middle_of_copyrightableList))) {
            return removingInTheMiddle(amountOfCalculation, copyOnWriteArrayListForSearch(amountOfCalculation));
        }
        return removingInTheEnd(amountOfCalculation, copyOnWriteArrayListForSearch(amountOfCalculation));
    }

    @Override
    public long addingInTheBeginning(int amountOfOperations, List<Character> list) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            list.add(charToAction);
        }

        return System.currentTimeMillis() - startTime;
    }

    @Override
    public long addingInTheMiddle(int amountOfOperations, List<Character> list) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            list.add(list.size() / 2, charToAction);
        }

        return System.currentTimeMillis() - startTime;
    }

    @Override
    public long addingInTheEnd(int amountOfOperations, List<Character> list) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            list.add(list.size() - 1, charToAction);
        }

        return System.currentTimeMillis() - startTime;
    }

    @Override
    public long searchByValue(int amountOfOperations, List<Character> list) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            if (list.get(i) == charToSearch) {
                return System.currentTimeMillis() - startTime;
            }
        }

        return System.currentTimeMillis() - startTime;
    }

    @Override
    public long removingInTheBeginning(int amountOfOperations, List<Character> list) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            list.remove(0);
        }

        return System.currentTimeMillis() - startTime;
    }

    @Override
    public long removingInTheMiddle(int amountOfOperations, List<Character> list) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            list.remove(list.size() / 2);
        }

        return System.currentTimeMillis() - startTime;
    }

    @Override
    public long removingInTheEnd(int amountOfOperations, List<Character> list) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            list.remove(list.size() - 1);
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

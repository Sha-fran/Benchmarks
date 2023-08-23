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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FragmentCollections extends Fragment implements View.OnClickListener, OperationsCollections {

    private final BenchmarksAdapter adapter = new BenchmarksAdapter();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private FragmentCollectionsBinding binding;
    private final char charToAction = 'a';
    private ExecutorService pool;
    private static final int NUMBER_OF_CORES = Math.max(1, Runtime.getRuntime().availableProcessors() - 1);
    private long initialStartTime = -1;


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
        adapter.submitList(createBenchmarkItems(initialStartTime, false));
    }

    private List<DataBox> createBenchmarkItems(long time, boolean showProgress) {
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
            DataBox dataBox = new DataBox(textArrayCollection, (int) time, showProgress);
            list.add(dataBox);
        }

        return list;
    }

    public void onClick(View view) {
        if (view == binding.textInputLayoutCollections) {
            EditDataDialogFragment.newInstance().show(getChildFragmentManager(), EditDataDialogFragment.TAG);
        } else if (view == binding.buttonStartStopFragmentsCollections) {
            if (binding.buttonStartStopFragmentsCollections.getText().toString().equals(getString(R.string.ON))) {
                initialStartTime = System.currentTimeMillis();
                calculations(Integer.parseInt(binding.textInputLayoutCollections.getText().toString()), initialStartTime);
                binding.buttonStartStopFragmentsCollections.setText(R.string.stop);
            } else {
                long stoppedTime = System.currentTimeMillis() - initialStartTime;
                List<DataBox> items = new ArrayList<>(adapter.getCurrentList());

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).progressVisible) {
                        items.set(i, items.get(i).copyWithTime((int) stoppedTime));
                    }
                }

                pool.shutdownNow();
                pool = null;
                binding.buttonStartStopFragmentsCollections.setText(R.string.start);
                adapter.submitList(new ArrayList<>(items));
            }
        }
    }

    public void calculations(int amountOfCalculation, long startTime) {
        final List<DataBox> benchmarkItems = createBenchmarkItems(startTime, true);
        adapter.submitList(new ArrayList<>(benchmarkItems));
        pool = Executors.newFixedThreadPool(NUMBER_OF_CORES);

        for (int i = 0; i < benchmarkItems.size(); i++) {
            final int index = i;
            final DataBox item = benchmarkItems.get(index);
            pool.submit(() -> {
                DataBox dataBox = item.copyWithTime((int) measure(item.text, amountOfCalculation, startTime));
                benchmarkItems.set(index, dataBox);
                handler.post(() -> {
                    Log.d("LOGG", "Item update " + index);
                    adapter.submitList(new ArrayList<>(benchmarkItems));
                });
            });
        }
        pool.shutdown();
    }

    public long measure(int itemText, int amountOfCalculation, long startTime) {
        if (itemText == R.string.adding_in_the_beginning_of_arrayList) {
            return addingInTheBeginning(amountOfCalculation, arrayListCreation(amountOfCalculation), startTime);
        } else if (itemText == R.string.adding_in_the_middle_of_arrayList) {
            return addingInTheMiddle(amountOfCalculation, arrayListCreation(amountOfCalculation), startTime);
        } else if (itemText == R.string.adding_in_the_end_of_arrayList) {
            return addingInTheEnd(amountOfCalculation, arrayListCreation(amountOfCalculation), startTime);
        } else if (itemText == R.string.search_by_value_from_arrayList) {
            return searchByValue(amountOfCalculation, arrayListCreation(amountOfCalculation), startTime);
        } else if (itemText == R.string.removing_in_the_beginning_of_arrayList) {
            return removingInTheBeginning(amountOfCalculation, arrayListCreation(amountOfCalculation), startTime);
        } else if (itemText == R.string.removing_in_the_middle_of_arrayList) {
            return removingInTheMiddle(amountOfCalculation, arrayListCreation(amountOfCalculation), startTime);
        } else if (itemText == R.string.removing_in_the_end_of_arrayList) {
            return removingInTheEnd(amountOfCalculation, arrayListCreation(amountOfCalculation) , startTime);
        } else if (itemText == R.string.adding_in_the_beginning_of_linkedList) {
            return addingInTheBeginning(amountOfCalculation, linkedListCreation(amountOfCalculation), startTime);
        } else if (itemText == R.string.adding_in_the_middle_of_linkedList) {
            return addingInTheMiddle(amountOfCalculation, linkedListCreation(amountOfCalculation), startTime);
        } else if (itemText == R.string.adding_in_the_end_of_linkedList) {
            return addingInTheEnd(amountOfCalculation, linkedListCreation(amountOfCalculation), startTime);
        } else if (itemText == R.string.search_by_value_from_linkedList) {
            searchByValue(amountOfCalculation, linkedListCreation(amountOfCalculation), startTime);
        } else if (itemText == R.string.removing_in_the_beginning_of_linkedlist) {
            removingInTheBeginning(amountOfCalculation, linkedListCreation(amountOfCalculation), startTime);
        } else if (itemText == R.string.removing_in_the_middle_of_linkedlist) {
            return removingInTheMiddle(amountOfCalculation, linkedListCreation(amountOfCalculation), startTime);
        } else if (itemText == R.string.removing_in_the_end_of_linkedlist) {
            return removingInTheEnd(amountOfCalculation, linkedListCreation(amountOfCalculation), startTime);
        } else if (itemText == R.string.adding_in_the_beginning_of_copyrightableList) {
            return addingInTheBeginning(amountOfCalculation, copyOnWriteArrayListCreation(amountOfCalculation), startTime);
        } else if (itemText == R.string.adding_in_the_middle_of_copyrightableList) {
            return addingInTheMiddle(amountOfCalculation, copyOnWriteArrayListCreation(amountOfCalculation), startTime);
        } else if (itemText == R.string.adding_in_the_end_of_copyrightableList) {
            return addingInTheEnd(amountOfCalculation, copyOnWriteArrayListCreation(amountOfCalculation), startTime);
        } else if (itemText == R.string.search_by_value_from_copyrightableList) {
            return searchByValue(amountOfCalculation, copyOnWriteArrayListCreation(amountOfCalculation), startTime);
        } else if (itemText == R.string.removing_in_the_beginning_of_copyrightableList) {
            return removingInTheBeginning(amountOfCalculation, copyOnWriteArrayListCreation(amountOfCalculation), startTime);
        } else if (itemText == R.string.removing_in_the_middle_of_copyrightableList) {
            return removingInTheMiddle(amountOfCalculation, copyOnWriteArrayListCreation(amountOfCalculation), startTime);
        } else if (itemText == R.string.removing_in_the_end_of_copyrightableList) {
            return removingInTheEnd(amountOfCalculation, copyOnWriteArrayListCreation(amountOfCalculation), startTime);
        }

        return 0;
    }

    @Override
    public long addingInTheBeginning(int amountOfOperations, List<Character> list, long startTime) {
        for (int i = 0; i < amountOfOperations; i++) {
            list.add(charToAction);
        }

        return System.currentTimeMillis() - startTime;
    }

    @Override
    public long addingInTheMiddle(int amountOfOperations, List<Character> list, long startTime) {
        for (int i = 0; i < amountOfOperations; i++) {
            list.add(list.size() / 2, charToAction);
        }

        return System.currentTimeMillis() - startTime;
    }

    @Override
    public long addingInTheEnd(int amountOfOperations, List<Character> list, long startTime) {
        for (int i = 0; i < amountOfOperations; i++) {
            list.add(list.size() - 1, charToAction);
        }

        return System.currentTimeMillis() - startTime;
    }

    @Override
    public long searchByValue(int amountOfOperations, List<Character> list, long startTime) {
        for (int i = 0; i < amountOfOperations; i++) {
            char charToSearch = 'b';
            if (list.get(i) == charToSearch) {
                return System.currentTimeMillis() - startTime;
            }
        }

        return System.currentTimeMillis() - startTime;
    }

    @Override
    public long removingInTheBeginning(int amountOfOperations, List<Character> list, long startTime) {
        for (int i = 0; i < amountOfOperations; i++) {
            list.remove(0);
        }

        return System.currentTimeMillis() - startTime;
    }

    @Override
    public long removingInTheMiddle(int amountOfOperations, List<Character> list, long startTime) {
        for (int i = 0; i < amountOfOperations; i++) {
            list.remove(list.size() / 2);
        }

        return System.currentTimeMillis() - startTime;
    }

    @Override
    public long removingInTheEnd(int amountOfOperations, List<Character> list, long startTime) {
        for (int i = 0; i < amountOfOperations; i++) {
            list.remove(list.size() - 1);
        }

        return System.currentTimeMillis() - startTime;
    }

    private List<Character> arrayListCreation(int amountOfOperations) {
        return new ArrayList<>(Collections.nCopies(amountOfOperations, charToAction));
    }

    private List<Character> linkedListCreation(int amountOfOperations) {
        return new LinkedList<>(Collections.nCopies(amountOfOperations, charToAction));
    }

    private List<Character> copyOnWriteArrayListCreation(int amountOfOperations) {
        return new CopyOnWriteArrayList<>(Collections.nCopies(amountOfOperations, charToAction));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getParentFragmentManager().clearFragmentResultListener(RESULT_OF_AMOUNT_OF_OPERATIONS);
    }
}

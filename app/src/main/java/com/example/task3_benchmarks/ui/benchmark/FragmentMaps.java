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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FragmentMaps extends Fragment implements View.OnClickListener, OperationsMaps {
    private final BenchmarksAdapter adapter = new BenchmarksAdapter();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private FragmentCollectionsBinding binding;
    private ExecutorService pool;
    private static final int NUMBER_OF_CORES = Math.max(1, Runtime.getRuntime().availableProcessors() - 1);
    private long initialStartTime = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getChildFragmentManager().setFragmentResultListener(ENTER_AMOUNT_OF_OPERATIONS,
                this, (requestKey, result) -> {
                    binding.textInputLayoutCollections.setText(Integer.toString(result.getInt(RESULT_OF_AMOUNT_OF_OPERATIONS)));
                }
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
        binding.buttonStartStopFragmentsCollections.setOnClickListener(this);
        binding.textInputLayoutCollections.setOnClickListener(this);
        binding.rvFrCollections.setAdapter(adapter);
        binding.rvFrCollections.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        adapter.submitList(createBenchmarkItems(initialStartTime, false));
    }

    private List<DataBox> createBenchmarkItems(long time, boolean showProgress) {
        final List<DataBox> list = new ArrayList<>();

        final int[] textArrayMaps = {
                R.string.adding_new_in_treemap,
                R.string.search_by_key_in_treemap,
                R.string.removing_from_treemap,
                R.string.adding_new_in_hashmap,
                R.string.search_by_key_in_hashmap,
                R.string.removing_from_hashmap
        };

        for (int textArrayMap : textArrayMaps) {
            DataBox dataBox = new DataBox(textArrayMap, -1, showProgress);
            list.add(dataBox);
        }
        return list;
    }

    @Override
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
        if (itemText == R.string.adding_new_in_treemap) {
            return addingNew(amountOfCalculation, treeMapCreation(amountOfCalculation), startTime);
        } else if (itemText == R.string.search_by_key_in_treemap) {
            return searchByKey(amountOfCalculation, treeMapCreation(amountOfCalculation), startTime);
        } else if (itemText == R.string.removing_from_treemap) {
            return removing(amountOfCalculation, treeMapCreation(amountOfCalculation), startTime);
        } else if (itemText == R.string.adding_new_in_hashmap) {
            return addingNew(amountOfCalculation, hashMapCreation(amountOfCalculation), startTime);
        } else if (itemText == R.string.search_by_key_in_hashmap) {
            return searchByKey(amountOfCalculation, hashMapCreation(amountOfCalculation), startTime);
        } else if (itemText == R.string.removing_from_hashmap) {
            return removing(amountOfCalculation, hashMapCreation(amountOfCalculation), startTime);
        }

        return 0;
    }

    @Override
    public long addingNew(int amountOfOperations, Map<Integer, Character> map, long startTime) {
        for (int i = 0; i < amountOfOperations; i++) {
            map.put(i, 'a');
        }
        return System.currentTimeMillis() - startTime;
    }

    @Override
    public long searchByKey(int amountOfOperations, Map<Integer, Character> map, long startTime) {
        map.get(amountOfOperations - 1);

        return System.currentTimeMillis() - startTime;
    }

    @Override
    public long removing(int amountOfOperations, Map<Integer, Character> map, long startTime) {
        for (int i = 0; i < amountOfOperations; i++) {
            map.remove(i);
        }

        return System.currentTimeMillis() - startTime;
    }

    private TreeMap<Integer, Character> treeMapCreation(int amountOfOperations) {
        TreeMap<Integer, Character> treeMap = new TreeMap<>();

        for (int i = 0; i < amountOfOperations; i++) {
            treeMap.put(i, 'a');
        }

        return treeMap;
    }

    private HashMap<Integer, Character> hashMapCreation(int amountOfOperations) {
        HashMap<Integer, Character> hashMap = new HashMap<>();

        for (int i = 0; i < amountOfOperations; i++) {
            hashMap.put(i, 'a');
        }

        return hashMap;
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

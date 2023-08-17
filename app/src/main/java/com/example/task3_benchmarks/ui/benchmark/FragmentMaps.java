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
        adapter.submitList(createBenchmarkItems(false));
    }

    private List<DataBox> createBenchmarkItems(boolean showProgress) {
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
            DataBox dataBox = new DataBox(getString(textArrayMap), -1, showProgress);
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
            int currentIndex = index;

            pool.submit(() -> {
                DataBox dataBox = item.copyWithTime((int) measure(item.text, amountOfCalculation));
                benchmarkItems.set(currentIndex, dataBox);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.submitList(new ArrayList<>(benchmarkItems));
                    }
                });
            });
            index++;
        }
        pool.shutdown();
    }

    public long measure(String itemText, int amountOfCalculation) {
        if (itemText.equals(getString(R.string.adding_new_in_treemap))) {
            return addingNew(amountOfCalculation, new TreeMap<>());
        } else if (itemText.equals(getString(R.string.search_by_key_in_treemap))) {
            return searchByKey(amountOfCalculation, treeMapForSearch(amountOfCalculation, new TreeMap<>()));
        } else if (itemText.equals(getString(R.string.removing_from_treemap))) {
            return removing(amountOfCalculation, treeMapForSearch(amountOfCalculation, new TreeMap<>()));
        } else if (itemText.equals(getString(R.string.adding_new_in_hashmap))) {
            return addingNew(amountOfCalculation, new HashMap<>());
        } else if (itemText.equals(getString(R.string.search_by_key_in_hashmap))) {
            return searchByKey(amountOfCalculation, hashMapForSearch(amountOfCalculation, new HashMap<>()));
        }
        return removing(amountOfCalculation, hashMapForSearch(amountOfCalculation, new HashMap<>()));
    }

    @Override
    public long addingNew(int amountOfOperations, Map<Integer, Character> map) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            map.put(i, 'a');
        }
        return System.currentTimeMillis() - startTime;
    }

    @Override
    public long searchByKey(int amountOfOperations, Map<Integer, Character> map) {
        long startTime = System.currentTimeMillis();

        map.get(amountOfOperations - 1);

        return System.currentTimeMillis() - startTime;
    }

    @Override
    public long removing(int amountOfOperations, Map<Integer, Character> map) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < amountOfOperations; i++) {
            map.remove(i);
        }

        return System.currentTimeMillis() - startTime;
    }


    public HashMap<Integer, Character> hashMapForSearch(int amountOfOperations, HashMap<Integer, Character> hashMap) {
        for (int i = 0; i < amountOfOperations; i++) {
            hashMap.put(i, 'a');
        }

        return hashMap;
    }

    public TreeMap<Integer, Character> treeMapForSearch(int amountOfOperations, TreeMap<Integer, Character> treeMap) {
        for (int i = 0; i < amountOfOperations; i++) {
            treeMap.put(i, 'a');
        }

        return treeMap;
    }
}

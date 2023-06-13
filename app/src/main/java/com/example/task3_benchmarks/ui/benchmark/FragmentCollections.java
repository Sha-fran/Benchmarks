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
    final List<Runnable> operationsList = new ArrayList<>();
    List<Character> arrayList = new ArrayList<>();
    List<Character> arrayList1 = new ArrayList<>(Arrays.asList('a', 'a'));
    List<Character> arrayList2 = new ArrayList<>(Arrays.asList('a', 'a'));
    List<Character> arrayList3, arrayList4, arrayList5, arrayList6;

    List<Character> linkedList = new LinkedList<>();
    List<Character> linkedList1 = new LinkedList<>(Arrays.asList('a', 'a'));
    List<Character> linkedList2 = new LinkedList<>(Arrays.asList('a', 'a'));
    List<Character> linkedList3, linkedList4, linkedList5, linkedList6;

    List<Character> copyOnWrightArrayList = new CopyOnWriteArrayList<>();
    List<Character> copyOnWrightArrayList1 = new CopyOnWriteArrayList<>(Arrays.asList('a', 'a'));
    List<Character> copyOnWrightArrayList2 = new CopyOnWriteArrayList<>(Arrays.asList('a', 'a'));
    List<Character> copyOnWrightArrayList3, copyOnWrightArrayList4, copyOnWrightArrayList5, copyOnWrightArrayList6;
    int index = 0;

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

    private List<Runnable> createOperationsList() {
        final ArrayList<Runnable> opList = new ArrayList<>();
        opList.add(() -> arrayList.add(0, charToAction));
        opList.add(() -> arrayList1.add(arrayList1.size() / 2, charToAction));
        opList.add(() -> arrayList2.add(arrayList2.size() - 1, charToAction));
        opList.add(() -> {
            for ( int i = 0; i < amountOfOperations; i++) {
                if (arrayList3.get(i) == charToSearch) {
                    break;
                }
            }
        });
        opList.add(() -> arrayList4.remove(0));
        opList.add(() -> arrayList5.remove(arrayList5.size() / 2));
        opList.add(() -> arrayList6.remove(arrayList6.size() - 1));

        opList.add(() -> linkedList.add(0, charToAction));
        opList.add(() -> linkedList1.add(linkedList1.size() / 2, charToAction));
        opList.add(() -> linkedList2.add(linkedList2.size() - 1, charToAction));
        opList.add(() -> {
            for ( int i = 0; i < amountOfOperations; i++) {
                if (linkedList3.get(i) == charToSearch) {
                    break;
                }
            }
        });
        opList.add(() -> linkedList4.remove(0));
        opList.add(() -> linkedList5.remove(linkedList5.size() / 2));
        opList.add(() -> linkedList6.remove(linkedList6.size() - 1));

        opList.add(() -> copyOnWrightArrayList.add(0, charToAction));
        opList.add(() -> copyOnWrightArrayList1.add(copyOnWrightArrayList1.size() / 2, charToAction));
        opList.add(() -> copyOnWrightArrayList2.add(copyOnWrightArrayList2.size() - 1, charToAction));
        opList.add(() -> {
            for ( int i = 0; i < amountOfOperations; i++) {
                if (copyOnWrightArrayList3.get(i) == charToSearch) {
                    break;
                }
            }
        });
        opList.add(() -> copyOnWrightArrayList4.remove(0));
        opList.add(() -> copyOnWrightArrayList5.remove(copyOnWrightArrayList5.size() / 2));
        opList.add(() -> copyOnWrightArrayList6.remove(copyOnWrightArrayList6.size() - 1));

        return opList;
    }

    private List<DataBox> createBenchmarkItems() {
        final List<DataBox> list = new ArrayList<>();

        final List<Runnable> operations = new ArrayList<>(createOperationsList());

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
            DataBox dataBox = new DataBox(textArrayCollections[i], (int) System.currentTimeMillis(), operations.get(i));
            list.add(dataBox);
        }

        return list;
    }

    public void onClick(View view) {
        EditDataDialogFragment.newInstance().show(getChildFragmentManager(), EditDataDialogFragment.TAG);
    }

    public void handleFragmentResult(String requestKey, Bundle result) {
        amountOfOperations = result.getInt(RESULT_OF_AMOUNT_OF_OPERATIONS);
        arrayList3 = new ArrayList<>(arrayListForSearch(amountOfOperations));
        arrayList4 = new ArrayList<>(arrayListForSearch(amountOfOperations));
        arrayList5 = new ArrayList<>(arrayListForSearch(amountOfOperations));
        arrayList6 = new ArrayList<>(arrayListForSearch(amountOfOperations));

        linkedList3 = new LinkedList<>(linkedListForSearch(amountOfOperations));
        linkedList4 = new LinkedList<>(linkedListForSearch(amountOfOperations));
        linkedList5 = new LinkedList<>(linkedListForSearch(amountOfOperations));
        linkedList6 = new LinkedList<>(linkedListForSearch(amountOfOperations));

        copyOnWrightArrayList3 = new CopyOnWriteArrayList<>(copyOnWriteArrayListForSearch(amountOfOperations));
        copyOnWrightArrayList4 = new CopyOnWriteArrayList<>(copyOnWriteArrayListForSearch(amountOfOperations));
        copyOnWrightArrayList5 = new CopyOnWriteArrayList<>(copyOnWriteArrayListForSearch(amountOfOperations));
        copyOnWrightArrayList6 = new CopyOnWriteArrayList<>(copyOnWriteArrayListForSearch(amountOfOperations));

        binding.textInputLayoutCollections.setText(Integer.toString(amountOfOperations));

        ExecutorService pool = Executors.newFixedThreadPool(createBenchmarkItems().size());

        startTime = System.currentTimeMillis();

        for( DataBox iem : benchmarkItems) {
            pool.submit(()->{
                for (int i = 0; i < amountOfOperations; i++) {
                    Runnable operation = iem.operation;
                    operation.run();
                }
                long resultTime = System.currentTimeMillis() - startTime;
                handler.post(() -> actionsWithCollections(0, resultTime));
            });
        }
        pool.shutdown();

//        Thread threadAddingInTheBeginningOfArrayList = new Thread(new Runnable() {
//            final List<Character> arrayList = new ArrayList<>();
//
//            @Override
//            public void run() {
//                startTime = System.currentTimeMillis();
//                for (int i = 0; i < amountOfOperations; i++) {
//                    arrayList.add(i, charToAction);
//                }
//                long resultTime = System.currentTimeMillis() - startTime;
//                handler.post(() -> actionsWithCollections(0, resultTime));
//            }
//        });
//        threadAddingInTheBeginningOfArrayList.start();
//
//        Thread threadAddingInTheMiddleOfArrayList = new Thread(new Runnable() {
//            final List<Character> arrayList1 = new ArrayList<>(Arrays.asList('a', 'a'));
//
//            @Override
//            public void run() {
//                for (int i = 0; i < amountOfOperations; i++) {
//                    arrayList1.add(arrayList1.size() / 2, charToAction);
//                }
//                long resultTime1 = System.currentTimeMillis() - startTime;
//                handler.post((() -> actionsWithCollections(1, resultTime1)));
//            }
//        });
//        threadAddingInTheMiddleOfArrayList.start();
//
//        Thread threadAddingInTheEndOfArrayList = new Thread(new Runnable() {
//            private final List<Character> arrayList2 = new ArrayList<>(Arrays.asList('a', 'a'));
//
//            @Override
//            public void run() {
//                for (int i = 0; i < amountOfOperations; i++) {
//                    arrayList2.add(arrayList2.size() - 1, charToAction);
//                }
//                long resultTime2 = System.currentTimeMillis() - startTime;
//
//                handler.post(() -> actionsWithCollections(2, resultTime2));
//            }
//        });
//        threadAddingInTheEndOfArrayList.start();
    }

    public void actionsWithCollections(int index, long resultTime) {

        DataBox dataBox = new DataBox(0, ((int) resultTime), operationsList.get(index));
        createBenchmarkItems().set(index, dataBox);
        adapter.setItems(benchmarkItems);

    }

    public List<Character> arrayListForSearch(int amountOfOperations) {
        List <Character> aLFS = new ArrayList<>();
        Random random = new Random();
        int indexForSearch = random.nextInt(amountOfOperations - 1);

        for (int i = 0; i < amountOfOperations; i++) {
            if (i == indexForSearch) {
                aLFS.add(i, charToSearch);
            } else {
                aLFS.add(i, charToAction);
            }
        }

        return aLFS;
    }

    public List<Character> linkedListForSearch(int amountOfOperations) {
        List <Character> lLFS = new LinkedList<>();
        Random random = new Random();
        int indexForSearch = random.nextInt(amountOfOperations - 1);

        for (int i = 0; i < amountOfOperations; i++) {
            if (i == indexForSearch) {
                lLFS.add(i, charToSearch);
            } else {
                lLFS.add(i, charToAction);
            }
        }

        return lLFS;
    }

    public List<Character> copyOnWriteArrayListForSearch(int amountOfOperations) {
        List <Character> cWALFS = new CopyOnWriteArrayList<>();
        Random random = new Random();
        int indexForSearch = random.nextInt(amountOfOperations - 1);

        for (int i = 0; i < amountOfOperations; i++) {
            if (i == indexForSearch) {
                cWALFS.add(i, charToSearch);
            } else {
                cWALFS.add(i, charToAction);
            }
        }

        return cWALFS;
    }
}

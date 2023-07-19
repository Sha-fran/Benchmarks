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
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.task3_benchmarks.R;
import com.example.task3_benchmarks.databinding.FragmentCollectionsBinding;
import com.example.task3_benchmarks.models.DataBox;
import com.example.task3_benchmarks.ui.input.EditDataDialogFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class FragmentCollections extends Fragment implements View.OnClickListener {

    private final BenchmarksAdapter adapter = new BenchmarksAdapter();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private FragmentCollectionsBinding binding;
    final char charToAction = 'a', charToSearch = 'b';
    private boolean showProgress = false;
    final List<DataBox> benchmarkItems = createBenchmarkItems(showProgress);
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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

        binding.textInputLayoutCollections.setOnClickListener(this);
        binding.buttonStartFragmentsCollections.setOnClickListener(this);
        binding.rvFrCollections.setAdapter(adapter);
        binding.rvFrCollections.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
        adapter.setItems(benchmarkItems);
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

        for (int i = 0; i < textArrayCollections.length; i++) {
            DataBox dataBox = new DataBox(textArrayCollections[i], (int) System.currentTimeMillis(), showProgress);
            list.add(dataBox);
        }

        return list;
    }

    public void onClick(View view) {

        if (view == binding.textInputLayoutCollections) {
            EditDataDialogFragment.newInstance().show(getChildFragmentManager(), EditDataDialogFragment.TAG);
        } else if (view == binding.buttonStartFragmentsCollections) {
            calculations(Integer.parseInt(binding.textInputLayoutCollections.getText().toString()));
            binding.buttonStartFragmentsCollections.setVisibility(View.INVISIBLE);
            binding.buttonStopFragmentsCollections.setVisibility(View.VISIBLE);
        }
    }

    public void progressInAllCells() {
        for (int i = 0; i < benchmarkItems.size(); i++) {
            showProgress = true;
            benchmarkItems.get(i).setProgressVisible(showProgress);
            adapter.notifyItemChanged(i);
        }
        adapter.setItems(benchmarkItems);
    }

    public void stopProgressInAllCells() {
        for (int i = 0; i < benchmarkItems.size(); i++) {
            showProgress = false;
            benchmarkItems.get(i).setProgressVisible(showProgress);
            adapter.notifyItemChanged(i);
        }
        adapter.setItems(benchmarkItems);
    }

    public void calculations(int amountOfCalculation) {
        ExecutorService pool = Executors.newFixedThreadPool(NUMBER_OF_CORES - 1);

        progressInAllCells();

        for (DataBox item : benchmarkItems) {
            pool.submit(() -> {
                if (item.text == R.string.adding_in_the_beginning_of_arrayList) {
                    Runnable task = new Runnable() {
                        @Override
                        public void run() {
                            addingInTheBeginningOfArrayList(amountOfCalculation);
                        }
                    };
                    task.run();
                } else if (item.text == R.string.adding_in_the_middle_of_arrayList) {
                        Runnable task = new Runnable() {
                            @Override
                            public void run() {
                                addingInTheMiddleOfArrayList(amountOfCalculation);
                            }
                        };
                    task.run();
                } else if (item.text == R.string.adding_in_the_end_of_arrayList) {
                    Runnable task = new Runnable() {
                        @Override
                        public void run() {
                            addingInTheEndOfArrayList(amountOfCalculation);
                        }
                    };
                    task.run();
                } else if (item.text == R.string.search_by_value_from_arrayList) {
                    Runnable task = new Runnable() {
                        @Override
                        public void run() {
                            searchByValueFromArrayList(amountOfCalculation);
                        }
                    };
                    task.run();
                } else if (item.text == R.string.removing_in_the_beginning_of_arrayList) {
                    Runnable task = new Runnable() {
                        @Override
                        public void run() {
                            removingInTheBeginningOfArrayList(amountOfCalculation);
                        }
                    };
                    task.run();
                } else if (item.text == R.string.removing_in_the_middle_of_arrayList) {
                    Runnable task = new Runnable() {
                        @Override
                        public void run() {
                            removingInTheMiddleOfArrayList(amountOfCalculation);
                        }
                    };
                    task.run();
                } else if (item.text == R.string.removing_in_the_end_of_arrayList) {
                    Runnable task = new Runnable() {
                        @Override
                        public void run() {
                            removingInTheEndOfArrayList(amountOfCalculation);
                        }
                    };
                    task.run();
                } else if (item.text == R.string.adding_in_the_beginning_of_linkedList) {
                    Runnable task = new Runnable() {
                        @Override
                        public void run() {
                            addingInTheBeginningOfLinkedList(amountOfCalculation);
                        }
                    };
                    task.run();
                } else if (item.text == R.string.adding_in_the_middle_of_linkedList) {
                    Runnable task = new Runnable() {
                        @Override
                        public void run() {
                            addingInTheMiddleOfLinkedList(amountOfCalculation);
                        }
                    };
                    task.run();
                } else if (item.text == R.string.adding_in_the_end_of_linkedList) {
                    Runnable task = new Runnable() {
                        @Override
                        public void run() {
                            addingInTheEndOfLinkedList(amountOfCalculation);
                        }
                    };
                    task.run();
                } else if (item.text == R.string.search_by_value_from_linkedList) {
                    Runnable task = new Runnable() {
                        @Override
                        public void run() {
                            searchByValueFromLinkedList(amountOfCalculation);
                        }
                    };
                    task.run();
                } else if (item.text == R.string.removing_in_the_beginning_of_linkedlist) {
                    Runnable task = new Runnable() {
                        @Override
                        public void run() {
                            removingInTheBeginningOfLinkedList(amountOfCalculation);
                        }
                    };
                    task.run();
                } else if (item.text == R.string.removing_in_the_middle_of_linkedlist) {
                    Runnable task = new Runnable() {
                        @Override
                        public void run() {
                            removingInTheMiddleOfLinkedList(amountOfCalculation);
                        }
                    };
                    task.run();
                } else if (item.text == R.string.removing_in_the_end_of_linkedlist) {
                    Runnable task = new Runnable() {
                        @Override
                        public void run() {
                            removingInTheEndOfLinkedList(amountOfCalculation);
                        }
                    };
                    task.run();
                } else if (item.text == R.string.adding_in_the_beginning_of_copyrightableList) {
                    Runnable task = new Runnable() {
                        @Override
                        public void run() {
                            addingInTheBeginningOfCopyrightableList(amountOfCalculation);
                        }
                    };
                    task.run();
                } else if (item.text == R.string.adding_in_the_middle_of_copyrightableList) {
                    Runnable task = new Runnable() {
                        @Override
                        public void run() {
                            addingInTheMiddleOfCopyrightableList(amountOfCalculation);
                        }
                    };
                    task.run();
                } else if (item.text == R.string.adding_in_the_end_of_copyrightableList) {
                    Runnable task = new Runnable() {
                        @Override
                        public void run() {
                            addingInTheEndOfCopyrightableList(amountOfCalculation);
                        }
                    };
                    task.run();
                } else if (item.text == R.string.search_by_value_from_copyrightableList) {
                    Runnable task = new Runnable() {
                        @Override
                        public void run() {
                            searchByValueFromCopyrightableList(amountOfCalculation);
                        }
                    };
                    task.run();
                } else if (item.text == R.string.removing_in_the_beginning_of_copyrightableList) {
                    Runnable task = new Runnable() {
                        @Override
                        public void run() {
                            removingInTheBeginningOfCopyrightableList(amountOfCalculation);
                        }
                    };
                    task.run();
                } else if (item.text == R.string.removing_in_the_middle_of_copyrightableList) {
                    Runnable task = new Runnable() {
                        @Override
                        public void run() {
                            removingInTheMiddleOfCopyrightableList(amountOfCalculation);;
                        }
                    };
                    task.run();
                } else {
                    Runnable task = new Runnable() {
                        @Override
                        public void run() {
                            removingInTheEndOfCopyrightableList(amountOfCalculation);
                        }
                    };
                    task.run();
                }
            });
        }
        binding.buttonStopFragmentsCollections.setOnClickListener(v -> {
            pool.shutdownNow();
            stopProgressInAllCells();
            binding.buttonStartFragmentsCollections.setVisibility(View.VISIBLE);
            binding.buttonStopFragmentsCollections.setVisibility(View.INVISIBLE);
        });

        pool.shutdown();
        binding.buttonStartFragmentsCollections.setVisibility(View.INVISIBLE);
        binding.buttonStopFragmentsCollections.setVisibility(View.VISIBLE);
    }

    public void addingInTheBeginningOfArrayList(int amountOfOperations) {
        List<Character> arrayList = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            arrayList.add(charToAction);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime, false);
        adapter.getItems().set(0, dataBox);
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemChanged(0);
            }
        });
    }

    public void addingInTheMiddleOfArrayList(int amountOfOperations) {
        List<Character> arrayList = new ArrayList<>(Arrays.asList('a', 'a'));
        long startTime = System.currentTimeMillis();
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            arrayList.add(arrayList.size() / 2, charToAction);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime, false);
        adapter.getItems().set(1, dataBox);
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemChanged(1);
            }
        });
    }

    public void addingInTheEndOfArrayList(int amountOfOperations) {
        List<Character> arrayList = new ArrayList<>(Arrays.asList('a', 'a'));
        long startTime = System.currentTimeMillis();
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            arrayList.add(arrayList.size() - 1, charToAction);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime, false);
        adapter.getItems().set(2, dataBox);
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemChanged(2);
            }
        });
    }

    public void searchByValueFromArrayList(int amountOfOperations) {
        List<Character> arrayList = new ArrayList<>(arrayListForSearch(amountOfOperations));
        long startTime = System.currentTimeMillis();
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            if (arrayList.get(i) == charToSearch) {
                return;
            }
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime, false);
        adapter.getItems().set(3, dataBox);
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemChanged(3);
            }
        });
    }

    public void removingInTheBeginningOfArrayList(int amountOfOperations) {
        List<Character> arrayList = new ArrayList<>(arrayListForSearch(amountOfOperations));
        long startTime = System.currentTimeMillis();
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            arrayList.remove(0);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime, false);
        adapter.getItems().set(4, dataBox);
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemChanged(4);
            }
        });
    }

    public void removingInTheMiddleOfArrayList(int amountOfOperations) {
        List<Character> arrayList = new ArrayList<>(arrayListForSearch(amountOfOperations));
        long startTime = System.currentTimeMillis();
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            arrayList.remove(arrayList.size() / 2);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime, false);
        adapter.getItems().set(5, dataBox);
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemChanged(5);
            }
        });
    }

    public void removingInTheEndOfArrayList(int amountOfOperations) {
        List<Character> arrayList = new ArrayList<>(arrayListForSearch(amountOfOperations));
        long startTime = System.currentTimeMillis();
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            arrayList.remove(arrayList.size() - 1);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime, false);
        adapter.getItems().set(6, dataBox);
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemChanged(6);
            }
        });
    }

    public void addingInTheBeginningOfLinkedList(int amountOfOperations) {
        List<Character> linkedList = new LinkedList<>();
        long startTime = System.currentTimeMillis();
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            linkedList.add(charToAction);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime, false);
        adapter.getItems().set(7, dataBox);
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemChanged(7);
            }
        });
    }

    public void addingInTheMiddleOfLinkedList(int amountOfOperations) {
        List<Character> linkedList = new LinkedList<>(Arrays.asList('a', 'a'));
        long startTime = System.currentTimeMillis();
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            linkedList.add(linkedList.size() / 2, charToAction);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime, false);
        adapter.getItems().set(8, dataBox);
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemChanged(8);
            }
        });
    }

    public void addingInTheEndOfLinkedList(int amountOfOperations) {
        List<Character> linkedList = new LinkedList<>(Arrays.asList('a', 'a'));
        long startTime = System.currentTimeMillis();
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            linkedList.add(linkedList.size() - 1, charToAction);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime, false);
        adapter.getItems().set(9, dataBox);
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemChanged(9);
            }
        });
    }

    public void searchByValueFromLinkedList(int amountOfOperations) {
        List<Character> linkedList = new LinkedList<>(linkedListForSearch(amountOfOperations));
        long startTime = System.currentTimeMillis();
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            if (linkedList.get(i) == charToSearch) {
                return;
            }
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime, false);
        adapter.getItems().set(10, dataBox);
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemChanged(10);
            }
        });
    }

    public void removingInTheBeginningOfLinkedList(int amountOfOperations) {
        List<Character> linkedList = new LinkedList<>(linkedListForSearch(amountOfOperations));
        long startTime = System.currentTimeMillis();
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            linkedList.remove(0);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime, false);
        adapter.getItems().set(11, dataBox);
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemChanged(11);
            }
        });
    }

    public void removingInTheMiddleOfLinkedList(int amountOfOperations) {
        List<Character> linkedList = new LinkedList<>(linkedListForSearch(amountOfOperations));
        long startTime = System.currentTimeMillis();
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            linkedList.remove(linkedList.size() / 2);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime, false);
        adapter.getItems().set(12, dataBox);
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemChanged(12);
            }
        });
    }

    public void removingInTheEndOfLinkedList(int amountOfOperations) {
        List<Character> linkedList = new LinkedList<>(linkedListForSearch(amountOfOperations));
        long startTime = System.currentTimeMillis();
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            linkedList.remove(linkedList.size() - 1);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime, false);
        adapter.getItems().set(13, dataBox);
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemChanged(13);
            }
        });
    }

    public void addingInTheBeginningOfCopyrightableList(int amountOfOperations) {
        List<Character> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        long startTime = System.currentTimeMillis();
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            copyOnWriteArrayList.add(charToAction);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime, false);
        adapter.getItems().set(14, dataBox);
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemChanged(14);
            }
        });
    }

    public void addingInTheMiddleOfCopyrightableList(int amountOfOperations) {
        List<Character> copyOnWriteArrayList = new CopyOnWriteArrayList<>(Arrays.asList('a', 'a'));
        long startTime = System.currentTimeMillis();
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            copyOnWriteArrayList.add(copyOnWriteArrayList.size() / 2, charToAction);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime, false);
        adapter.getItems().set(15, dataBox);
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemChanged(15);
            }
        });
    }

    public void addingInTheEndOfCopyrightableList(int amountOfOperations) {
        List<Character> copyOnWriteArrayList = new CopyOnWriteArrayList<>(Arrays.asList('a', 'a'));
        long startTime = System.currentTimeMillis();
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            copyOnWriteArrayList.add(copyOnWriteArrayList.size() - 1, charToAction);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime, false);
        adapter.getItems().set(16, dataBox);
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemChanged(16);
            }
        });
    }

    public void searchByValueFromCopyrightableList(int amountOfOperations) {
        List<Character> copyOnWriteArrayList = new CopyOnWriteArrayList<>(copyOnWriteArrayListForSearch(amountOfOperations));
        long startTime = System.currentTimeMillis();
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            if (copyOnWriteArrayList.get(i) == charToSearch) {
                return;
            }
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime, false);
        adapter.getItems().set(17, dataBox);
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemChanged(17);
            }
        });
    }

    public void removingInTheBeginningOfCopyrightableList(int amountOfOperations) {
        List<Character> copyOnWriteArrayList = new CopyOnWriteArrayList<>(copyOnWriteArrayListForSearch(amountOfOperations));
        long startTime = System.currentTimeMillis();
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            copyOnWriteArrayList.remove(0);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime, false);
        adapter.getItems().set(18, dataBox);
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemChanged(18);
            }
        });
    }

    public void removingInTheMiddleOfCopyrightableList(int amountOfOperations) {
        List<Character> copyOnWriteArrayList = new CopyOnWriteArrayList<>(copyOnWriteArrayListForSearch(amountOfOperations));
        long startTime = System.currentTimeMillis();
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            copyOnWriteArrayList.remove(copyOnWriteArrayList.size() / 2);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime, false);
        adapter.getItems().set(19, dataBox);
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemChanged(19);
            }
        });
    }

    public void removingInTheEndOfCopyrightableList(int amountOfOperations) {
        List<Character> copyOnWriteArrayList = new CopyOnWriteArrayList<>(copyOnWriteArrayListForSearch(amountOfOperations));
        long startTime = System.currentTimeMillis();
        long resulTime;

        for (int i = 0; i < amountOfOperations; i++) {
            binding.buttonStopFragmentsCollections.callOnClick();
            copyOnWriteArrayList.remove(copyOnWriteArrayList.size() - 1);
        }

        resulTime = System.currentTimeMillis() - startTime;

        DataBox dataBox = new DataBox(0, (int) resulTime, false);
        adapter.getItems().set(20, dataBox);
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemChanged(20);
            }
        });
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
}

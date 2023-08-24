package com.example.task3_benchmarks.ui.benchmark;

import java.util.List;

public interface OperationsCollections {
    long addingInTheBeginning(int amountOfOperations, List<Character> list);
    long addingInTheMiddle(int amountOfOperations, List<Character> list);
    long addingInTheEnd(int amountOfOperations, List<Character> list);
    long searchByValue(int amountOfOperations, List<Character> list);
    long removingInTheBeginning(int amountOfOperations, List<Character> list);
    long removingInTheMiddle(int amountOfOperations, List<Character> list);
    long removingInTheEnd(int amountOfOperations, List<Character> list);
}

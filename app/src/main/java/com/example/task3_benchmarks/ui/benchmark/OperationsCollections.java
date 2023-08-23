package com.example.task3_benchmarks.ui.benchmark;

import java.util.List;

public interface OperationsCollections {
    long addingInTheBeginning(int amountOfOperations, List<Character> list, long startTime);
    long addingInTheMiddle(int amountOfOperations, List<Character> list, long startTime);
    long addingInTheEnd(int amountOfOperations, List<Character> list, long startTime);
    long searchByValue(int amountOfOperations, List<Character> list, long startTime);
    long removingInTheBeginning(int amountOfOperations, List<Character> list, long startTime);
    long removingInTheMiddle(int amountOfOperations, List<Character> list, long startTime);
    long removingInTheEnd(int amountOfOperations, List<Character> list, long startTime);
}

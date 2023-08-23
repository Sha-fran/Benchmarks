package com.example.task3_benchmarks.ui.benchmark;
import java.util.Map;

public interface OperationsMaps {
    long addingNew(int amountOfOperations, Map<Integer, Character> treeMap, long startTime);
    long searchByKey(int amountOfOperations, Map<Integer, Character> treeMap, long startTime);
    long removing(int amountOfOperations, Map<Integer, Character> treeMap, long startTime);
}

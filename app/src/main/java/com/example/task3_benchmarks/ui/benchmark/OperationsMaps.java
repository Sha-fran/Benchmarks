package com.example.task3_benchmarks.ui.benchmark;
import java.util.Map;

public interface OperationsMaps {
    long addingNew(int amountOfOperations, Map<Integer, Character> treeMap);
    long searchByKey(int amountOfOperations, Map<Integer, Character> treeMap);
    long removing(int amountOfOperations, Map<Integer, Character> treeMap);
}

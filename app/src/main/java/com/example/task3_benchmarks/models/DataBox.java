package com.example.task3_benchmarks.models;

import java.util.List;

public class DataBox {

    public final int text;
    public final int time;
    public final Runnable operation;
    public final List list;

    public DataBox(int text, int time, Runnable operation, List list) {
        this.text = text;
        this.time = time;
        this.operation = operation;
        this.list = list;
    }
}

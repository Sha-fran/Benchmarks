package com.example.task3_benchmarks.models;

public class DataBox {

    public final int text;
    public final int time;
    public final Runnable operation;

    public DataBox(int text, int time, Runnable operation) {
        this.text = text;
        this.time = time;
        this.operation = operation;
    }
}

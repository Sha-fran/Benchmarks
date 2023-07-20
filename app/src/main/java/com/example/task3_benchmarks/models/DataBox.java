package com.example.task3_benchmarks.models;

public class DataBox {

    public final int text;
    public final int time;
    public final boolean progressVisible;

    public boolean isProgressVisible() {
        return progressVisible;
    }

    public DataBox(int text, int time, boolean progressVisible) {
        this.text = text;
        this.time = time;
        this.progressVisible = progressVisible;
    }
}

package com.example.task3_benchmarks.models;

public class DataBox {

    public final int text;
    public final int time;

    private boolean progressVisible;

    public boolean isProgressVisible() {
        return progressVisible;
    }

    public DataBox(int text, int time, boolean progressVisible) {
        this.text = text;
        this.time = time;
        this.progressVisible = progressVisible;
    }

    public void setProgressVisible(boolean progressVisible) {
        this.progressVisible = progressVisible;
    }
}

package com.example.task3_benchmarks.models;

public class DataBox {

    public final int text;
    public final int time;

    public boolean isProgressVisible() {
        return progressVisible;
    }

    private boolean progressVisible;

    public DataBox(int text, int time, boolean progressVisible) {
        this.text = text;
        this.time = time;
        this.progressVisible = false;
    }
    public void setProgressVisible(boolean progressVisible) {
        this.progressVisible = progressVisible;
    }
}

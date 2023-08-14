package com.example.task3_benchmarks.models;

public class DataBox {

    public final int text;
    public final int time;
    public final boolean progressVisible;

    public DataBox(int text, int time, boolean progressVisible) {
        this.text = text;
        this.time = time;
        this.progressVisible = progressVisible;
    }

    public DataBox copyWithTime(int newTime) {
        return new DataBox(text, newTime, progressVisible);
    }

    public boolean equals(DataBox dataBox) {
        if (dataBox == null) {
            return false;
        }

        return this.text == dataBox.text && this.time == dataBox.time && this.progressVisible == dataBox.progressVisible;
    }
}

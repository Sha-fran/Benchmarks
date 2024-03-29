package com.example.task3_benchmarks.models;

import java.util.Objects;

public class DataBox {

    public final int text;
    public final long time;
    public final boolean progressVisible;

    public DataBox(int text, long time, boolean progressVisible) {
        this.text = text;
        this.time = time;
        this.progressVisible = progressVisible;
    }

    public DataBox copyWithTime(long newTime) {
        return new DataBox(text, newTime, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataBox)) return false;
        DataBox dataBox = (DataBox) o;
        return Objects.equals(text, dataBox.text) && time == dataBox.time && progressVisible == dataBox.progressVisible;
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, time, progressVisible);
    }
}

package com.example.task3_benchmarks.models;

public class DataBox {

    public int text;
    public int time;

    public DataBox(int text, int time) {
        this.text = text;
        this.time = time;

    }

    public void setText(int text) {
        this.text = text;
    }

    public void setTime(int time) {
        this.time = time;
    }
}

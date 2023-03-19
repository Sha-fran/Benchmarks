package com.example.task3_benchmarks;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EditDataDialogFragment extends DialogFragment {

    public static final String TAG = "Edit Data";

    public static EditDataDialogFragment newInstance() {
        return new EditDataDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_data_dialog, container, false);
    }
}

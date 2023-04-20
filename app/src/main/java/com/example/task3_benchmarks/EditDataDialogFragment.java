package com.example.task3_benchmarks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.task3_benchmarks.databinding.FragmentEditDataDialogBinding;
import com.google.android.material.textfield.TextInputEditText;

public class EditDataDialogFragment extends DialogFragment implements TextWatcher {
    public static final String ENTER_AMOUNT_OF_OPERATIONS = "Enter amount of operations";
    public static final String RESULT_OF_AMOUNT_OF_OPERATIONS = "Result of amount of operations";
    public static final String TAG = "Edit Data";
    private FragmentEditDataDialogBinding binding;
    private int amountOfOperations;
    private TextInputEditText textAmountOfOperations;

    public static EditDataDialogFragment newInstance() {
        return new EditDataDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_data_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle result = new Bundle();
                result.putInt(RESULT_OF_AMOUNT_OF_OPERATIONS, amountOfOperations);
                getParentFragmentManager().setFragmentResult(ENTER_AMOUNT_OF_OPERATIONS, result);
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        amountOfOperations = Integer.parseInt(textAmountOfOperations.getText().toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}

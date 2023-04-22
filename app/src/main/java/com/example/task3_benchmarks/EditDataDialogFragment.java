package com.example.task3_benchmarks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.task3_benchmarks.databinding.FragmentEditDataDialogBinding;
import com.google.android.material.textfield.TextInputEditText;

public class EditDataDialogFragment extends DialogFragment {
    public static final String ENTER_AMOUNT_OF_OPERATIONS = "Enter amount of operations";
    public static final String RESULT_OF_AMOUNT_OF_OPERATIONS = "Result of amount of operations";
    public static final String TAG = "Edit Data";
    private FragmentEditDataDialogBinding binding;
    private int amountOfOperations;

    public static EditDataDialogFragment newInstance() {
        return new EditDataDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditDataDialogBinding.inflate(inflater, container, false);
        return inflater.inflate(R.layout.fragment_edit_data_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isDigitsOnly(binding.enterANumber.getText().toString())) {
                    amountOfOperations = Integer.parseInt(binding.enterANumber.getText().toString());
                } else {
                    binding.enterANumber.setError(getString(R.string.error_you_need_enter_elements_count));
                }
                Bundle result = new Bundle();
                result.putInt(RESULT_OF_AMOUNT_OF_OPERATIONS, amountOfOperations);
                getParentFragmentManager().setFragmentResult(ENTER_AMOUNT_OF_OPERATIONS, result);
                dismiss();
            }
        });
    }
}

package com.example.task3_benchmarks.ui.input;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.task3_benchmarks.R;
import com.example.task3_benchmarks.databinding.FragmentInputBinding;

public class EditDataDialogFragment extends DialogFragment implements View.OnClickListener {
    public static final String ENTER_AMOUNT_OF_OPERATIONS = "Enter amount of operations";
    public static final String RESULT_OF_AMOUNT_OF_OPERATIONS = "Result of amount of operations";
    public static final String TAG = "Edit Data";

    private FragmentInputBinding binding;

    public static EditDataDialogFragment newInstance() {
        return new EditDataDialogFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInputBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonCalculate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String enteredAmountOfOperations = binding.enterANumber.getText().toString();

        if (enteredAmountOfOperations.isEmpty()) {
            binding.enterANumber.setError(getString(R.string.imput_is_empty_please));
        } else if (hasNonDigits(enteredAmountOfOperations)) {
            binding.enterANumber.setError(getString(R.string.you_need_enter_digit_symbols_only));
        } else {
            int amountOfOperations = Integer.parseInt(enteredAmountOfOperations);
            if (amountOfOperations <= 0) {
                binding.enterANumber.setError(getString(R.string.please_enter_amount_of_operations_0));
            } else {
                final Bundle result = new Bundle();
                result.putInt(RESULT_OF_AMOUNT_OF_OPERATIONS, amountOfOperations);
                getParentFragmentManager().setFragmentResult(ENTER_AMOUNT_OF_OPERATIONS, result);
                dismiss();
            }
        }
    }

    public boolean hasNonDigits(String amountOfOperations) {
        for (int i = 0; i < amountOfOperations.length(); i++) {
            if (Character.isDigit(amountOfOperations.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}

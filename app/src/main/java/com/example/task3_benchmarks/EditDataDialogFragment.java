package com.example.task3_benchmarks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.task3_benchmarks.databinding.FragmentEditDataDialogBinding;

public class EditDataDialogFragment extends DialogFragment implements View.OnClickListener {
    public static final String ENTER_AMOUNT_OF_OPERATIONS = "Enter amount of operations";
    public static final String RESULT_OF_AMOUNT_OF_OPERATIONS = "Result of amount of operations";
    public static final String TAG = "Edit Data";
    private FragmentEditDataDialogBinding binding;
    private int amountOfOperations;

    public static EditDataDialogFragment newInstance() {
        return new EditDataDialogFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditDataDialogBinding.inflate(inflater, container, false);
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

        if(enteredAmountOfOperations.isEmpty()) {
            binding.enterANumber.setError(getString(R.string.imput_is_empty_please));
        } else if (checkDigitSymbols(enteredAmountOfOperations)) {
            binding.enterANumber.setError(getString(R.string.you_need_enter_digit_symbols_only));
        } else if (Integer.parseInt(enteredAmountOfOperations) == 0) {
            binding.enterANumber.setError(getString(R.string.please_enter_amount_of_operations_0));
        } else if (Integer.parseInt(enteredAmountOfOperations) < 0) {
            binding.enterANumber.setError(getString(R.string.please_enter_amount_0));
        } else {
            amountOfOperations = Integer.parseInt(checkFirsZeroes(enteredAmountOfOperations));
            Bundle result = new Bundle();
            result.putInt(RESULT_OF_AMOUNT_OF_OPERATIONS, amountOfOperations);
            getParentFragmentManager().setFragmentResult(ENTER_AMOUNT_OF_OPERATIONS, result);
            dismiss();
        }
    }

    public boolean checkDigitSymbols(String amountOfOperations) {
        for ( int i = 0; i < amountOfOperations.length(); i++ ) {
            if (!Character.isLetter(amountOfOperations.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public String checkFirsZeroes(String numberOfOperations) {
        for(int i = 0; i < numberOfOperations.length(); i++) {
            if(numberOfOperations.charAt(i) == '0') {
                numberOfOperations = numberOfOperations.substring(i+1);
                i -= 1;
            } else {
                return numberOfOperations;
            }
        }
        return numberOfOperations;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }


}

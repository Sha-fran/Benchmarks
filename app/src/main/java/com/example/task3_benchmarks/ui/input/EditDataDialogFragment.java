package com.example.task3_benchmarks.ui.input;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.task3_benchmarks.R;
import com.example.task3_benchmarks.databinding.FragmentInputBinding;

import java.util.Objects;

public class EditDataDialogFragment extends DialogFragment implements View.OnClickListener {
    public static final String ENTER_AMOUNT_OF_OPERATIONS = "Enter amount of operations";
    public static final String RESULT_OF_AMOUNT_OF_OPERATIONS = "Result of amount of operations";
    public static final String TAG = "Edit Data";

    private FragmentInputBinding binding;

    public static EditDataDialogFragment newInstance() {
        return new EditDataDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle saveInstanceState) {
       Dialog dialog = new Dialog(getContext());
       binding = FragmentInputBinding.inflate(getLayoutInflater(),null, false);

       dialog.setContentView(binding.getRoot());
       dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
       binding.buttonCalculate.setOnClickListener(this);
       setCancelable(false);

       return dialog;
    }

    @Override
    public void onClick(View view) {
        try {
            String enteredAmountOfOperations = Objects.requireNonNull(binding.enterANumber.getText()).toString();

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
        } catch (NumberFormatException e) {
            Log.d("InputData", "Invalid Amount of operation");
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

package com.example.prm392_proj.util;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.time.format.DateTimeFormatter;

public class InputValidation {
    TextInputLayout inputLayout;
    TextInputEditText inputText;
    String label;
    boolean isRequired = false;
    boolean isNumber = false;
    boolean isInteger = false;
    boolean isEmail = false;
    boolean isPositiveNumber = false;
    boolean isNegativeNumber = false;
    boolean isNotPositiveNumber = false;
    boolean isNotNegativeNumber = false;
    boolean isGreaterThanOne = false;
    boolean isDate = false;
    String datePattern = "dd/MM/yyyy";

    public InputValidation(TextInputLayout inputLayout, TextInputEditText inputText, String label) {
        this.inputLayout = inputLayout;
        this.inputText = inputText;
        this.label = label;

        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validate();
            }
        });
    }

    public boolean validate() {
        String text = inputText.getText().toString().trim();
        if (isRequired && TextUtils.isEmpty(text)) {
            inputLayout.setError(label + " is required");
            return false;
        }

        if (isNumber) {
            try {
                Double.parseDouble(text);
            } catch (NumberFormatException e) {
                inputLayout.setError(label + " must be a number");
                return false;
            }
        }

        if (isInteger) {
            try {
                Integer.parseInt(text);
            } catch (NumberFormatException e) {
                inputLayout.setError(label + " must be an integer");
                return false;
            }
        }

        if (isEmail) {
            if (!text.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                inputLayout.setError(label + " must be a valid email address");
                return false;
            }
        }

        if (isPositiveNumber) {
            if (Double.parseDouble(text) <= 0) {
                inputLayout.setError(label + " must be a positive number");
                return false;
            }
        }

        if (isNegativeNumber) {
            if (Double.parseDouble(text) >= 0) {
                inputLayout.setError(label + " must be a negative number");
                return false;
            }
        }

        if (isNotPositiveNumber) {
            if (Double.parseDouble(text) > 0) {
                inputLayout.setError(label + " must not be a positive number");
                return false;
            }
        }

        if (isNotNegativeNumber) {
            if (Double.parseDouble(text) < 0) {
                inputLayout.setError(label + " must not be a negative number");
                return false;
            }
        }

        if (isDate) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
            try {
                formatter.parse(text);
            } catch (Exception e) {
                inputLayout.setError(label + " must be a valid date");
                return false;
            }
        }

        if (isGreaterThanOne) {
            if (Double.parseDouble(text) <= 1) {
                inputLayout.setError(label + " must be greater than 1");
                return false;
            }
        }

        inputLayout.setError(null);
        return true;
    }
}

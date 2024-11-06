package com.example.prm392_proj.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_proj.R;
import com.example.prm392_proj.util.InputValidation;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class InputDialog extends AppCompatActivity {
    Dialog dialog;
    CancelListener cancelListener;
    EnterListener enterListener;
    InputValidation inputValidation = null;
    TextInputLayout inputLayout;
    TextInputEditText inputText;
    String label = "Text";

    public InputDialog(Context context) {
        cancelListener = () -> {

        };
        enterListener = text -> {

        };

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_input);

        inputLayout = dialog.findViewById(R.id.inputLayout);
        inputText = dialog.findViewById(R.id.inputText);
        inputLayout.setHint("Enter text");

        Window window = dialog.getWindow();

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        window.setAttributes(windowAttributes);

        Button cancelButton = dialog.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v1 -> {
            dialog.dismiss();
            cancelListener.onCancel();
        });

        Button enterButton = dialog.findViewById(R.id.enterButton);
        enterButton.setOnClickListener(v1 -> {
            EditText dialogEditText = dialog.findViewById(R.id.inputText);
            String text = "";
            boolean isOk = false;
            if (inputValidation == null) {
                text = dialogEditText.getText().toString();
                isOk = true;
            } else {
                if (inputValidation.validate()) {
                    text = dialogEditText.getText().toString();
                    isOk = true;
                }
            }

            if (isOk) {
                enterListener.onEnter(text);
                dialog.dismiss();
            }
        });
    }

    public void setValidation(InputValidation inputValidation) {
        this.inputValidation = inputValidation;
        this.setLabel(inputValidation.getLabel());
        TextView title = dialog.findViewById(R.id.title);
        title.setText("Enter " + label);
        inputLayout.setHint("Enter " + label);
        inputValidation.setInputLayout(inputLayout, inputText);
    }

    public void setContentView(@LayoutRes int layoutResID) {
        dialog.setContentView(layoutResID);
    }

    public void show() {
        dialog.show();
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setOnCancelListener(CancelListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    public void setOnEnterListener(EnterListener enterListener) {
        this.enterListener = enterListener;
    }

    public interface CancelListener {
        void onCancel();

    }

    public interface EnterListener {

        void onEnter(String text);

    }
}


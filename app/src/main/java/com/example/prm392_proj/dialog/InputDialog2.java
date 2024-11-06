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

public class InputDialog2 extends AppCompatActivity {
    Dialog dialog;
    CancelListener cancelListener;
    EnterListener enterListener;
    InputValidation inputValidation = null;
    InputValidation inputValidation2 = null;
    TextInputLayout inputLayout, inputLayout2;
    TextInputEditText inputText, inputText2;
    String label = "Text";
    String label2 = "Text";
    String labelTitle = "Text";

    public InputDialog2(Context context) {
        init(context);
    }

    public InputDialog2(Context context, String defaultText, String defaultText2, String labelTitle) {
        init(context);
        inputText.setText(defaultText);
        inputText2.setText(defaultText2);
        this.labelTitle = labelTitle;
        TextView title = dialog.findViewById(R.id.title);
        title.setText("Enter " +labelTitle);
    }

    private void init(Context context) {
        cancelListener = () -> {

        };
        enterListener = (text, text2) -> {

        };

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_2_input);

        inputLayout = dialog.findViewById(R.id.inputLayout);
        inputText = dialog.findViewById(R.id.inputText);
        inputLayout.setHint("Enter text");

        inputLayout2 = dialog.findViewById(R.id.inputLayout2);
        inputText2 = dialog.findViewById(R.id.inputText2);
        inputLayout2.setHint("Enter text");

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

            EditText dialogEditText2 = dialog.findViewById(R.id.inputText2);
            String text2 = "";

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

            boolean isOk2 = false;
            if (inputValidation2 == null) {
                text2 = dialogEditText2.getText().toString();
                isOk2 = true;
            } else {
                if (inputValidation2.validate()) {
                    text2 = dialogEditText2.getText().toString();
                    isOk2 = true;
                }
            }

            if (isOk && isOk2) {
                enterListener.onEnter(text, text2);
                dialog.dismiss();
            }
        });
    }

    public void setValidation(InputValidation inputValidation, InputValidation inputValidation2) {
        this.inputValidation = inputValidation;
        this.setLabel(inputValidation.getLabel());
        inputLayout.setHint("Enter " + label);
        inputValidation.setInputLayout(inputLayout, inputText);

        this.inputValidation2 = inputValidation2;
        this.setLabel2(inputValidation2.getLabel());
        inputLayout2.setHint("Enter " + label2);
        inputValidation2.setInputLayout(inputLayout2, inputText2);
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

    public void setLabel2(String label) {
        this.label2 = label;
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

        void onEnter(String text, String text2);

    }
}


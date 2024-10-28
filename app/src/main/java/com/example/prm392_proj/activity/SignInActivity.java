package com.example.prm392_proj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prm392_proj.R;

import com.example.prm392_proj.database.DatabaseHelper;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText username = findViewById(R.id.username_prompt_input);
        EditText password = findViewById(R.id.confirm_password_prompt_input);

        var signInBtn = findViewById(R.id.signin_button);

        signInBtn.setOnClickListener(v ->
                login(username.getText().toString(),
                        password.getText().toString()));

        var switchSignUpBtn = findViewById(R.id.switch_signup);
        var signUpIntent = new Intent(this, SignUpActivity.class);

        switchSignUpBtn.setOnClickListener(v -> startActivity(signUpIntent));
    }

    void login(String username, String password) {
        var db = DatabaseHelper.getInstance(this);

        if (db.userDAO().accountExist(username, password)) {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        }

    }
}
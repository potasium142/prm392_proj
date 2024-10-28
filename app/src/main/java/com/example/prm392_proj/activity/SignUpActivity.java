package com.example.prm392_proj.activity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prm392_proj.R;

import com.example.prm392_proj.database.DatabaseHelper;
import com.example.prm392_proj.model.User;

public class SignUpActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        var signUpBtn = findViewById(R.id.signup_button);
        signUpBtn.setOnClickListener(v -> {
            CheckBox tosCheckBox = findViewById(R.id.agree_tos_checkbox);
            boolean tosChecked = !tosCheckBox.isChecked();
            if (tosChecked) {
                Toast.makeText(this,
                        "Please accept TOS",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            String password = ((EditText) findViewById(R.id.password_prompt_input))
                    .getText().toString();
            String confirmPassword = ((EditText) findViewById(R.id.confirm_password_prompt_input))
                    .getText().toString();

            boolean samePasswordCheck = !(password.equals(confirmPassword));
            if (samePasswordCheck) {
                Toast.makeText(this,
                        "Passwords do not match",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            EditText email = findViewById(R.id.email_prompt_input);
            EditText username = findViewById(R.id.username_prompt_input);

            User user = User.builder()
                    .email(email.getText().toString())
                    .username(username.getText().toString())
                    .profileName(username.getText().toString())
                    .password(password)
                    .build();

            addUser(user);


        });
    }

    private void addUser(User user) {
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        if (db.userDAO().usernameExist(user.getUsername())) {
            Toast.makeText(this, "Username exist", Toast.LENGTH_SHORT).show();
            return;
        }
        var insertStatus = db.userDAO().insert(user);

        if (insertStatus == -1) {
            Toast.makeText(this,
                    "Failed to create account",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Account created, please login",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
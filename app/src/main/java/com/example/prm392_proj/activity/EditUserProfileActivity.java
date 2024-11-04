package com.example.prm392_proj.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_proj.R;
import com.example.prm392_proj.database.DatabaseHelper;
import com.example.prm392_proj.model.User;

public class EditUserProfileActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText emailInput;
    private EditText profileNameInput;
    private Button saveButton;
    private String username; // Thay đổi từ userId sang username
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        // Nhận dữ liệu từ Intent
        username = getIntent().getStringExtra("USERNAME");
        String profileName = getIntent().getStringExtra("PROFILE_NAME");

        // Khởi tạo các trường nhập liệu
        usernameInput = findViewById(R.id.username_input);
        emailInput = findViewById(R.id.email_input);
        profileNameInput = findViewById(R.id.profile_name_input);
        saveButton = findViewById(R.id.save_button);
        backButton = findViewById(R.id.back_button);

        loadUserData(username);

        saveButton.setOnClickListener(v -> saveUserProfile());
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditUserProfileActivity.this, UserProfileActivity.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });
    }

    private void loadUserData(String username) {
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        User user = db.userDAO().getUser(username); // Lấy thông tin người dùng từ cơ sở dữ liệu

        // Kiểm tra xem người dùng có tồn tại không
        if (user != null) {
            usernameInput.setText(user.getUsername());
            emailInput.setText(user.getEmail());
            profileNameInput.setText(user.getProfileName());
        } else {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void saveUserProfile() {
        String newUsername = usernameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String profileName = profileNameInput.getText().toString().trim();

        // Kiểm tra dữ liệu đầu vào
        if (newUsername.isEmpty() || email.isEmpty() || profileName.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy thông tin người dùng từ SharedPreferences
        var sharedPreferences = getSharedPreferences("vclclgtclgmcs", MODE_PRIVATE);
        String currentUsername = sharedPreferences.getString("USERNAME", null);

        // Khởi tạo DatabaseHelper
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        User user = db.userDAO().getUser(currentUsername); // Lấy thông tin người dùng hiện tại

        // Kiểm tra xem người dùng có tồn tại không
        if (user != null) {
            // Cập nhật thông tin người dùng
            user.setUsername(newUsername);
            user.setEmail(email);
            user.setProfileName(profileName);

            // Cập nhật thông tin người dùng trong cơ sở dữ liệu
            db.userDAO().updateUser(user);
            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();

            // Cập nhật lại SharedPreferences nếu cần
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("USERNAME", newUsername);
            editor.apply();

            Intent intent = new Intent(EditUserProfileActivity.this, UserProfileActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show();
        }
    }
}
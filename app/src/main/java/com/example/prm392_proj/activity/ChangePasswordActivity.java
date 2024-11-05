package com.example.prm392_proj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_proj.R;
import com.example.prm392_proj.database.DatabaseHelper;
import com.example.prm392_proj.model.User;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText currentPasswordInput;
    private EditText newPasswordInput;
    private EditText confirmPasswordInput;
    private Button changePasswordButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        // Khởi tạo các view
        currentPasswordInput = findViewById(R.id.current_password_input);
        newPasswordInput = findViewById(R.id.new_password_input);
        confirmPasswordInput = findViewById(R.id.confirm_password_input);
        changePasswordButton = findViewById(R.id.change_password_button);
        backButton = findViewById(R.id.back_button);

        // Nhận username từ Intent
        String username = getIntent().getStringExtra("USERNAME");

        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(ChangePasswordActivity.this, UserProfileActivity.class);
            intent.putExtra("USERNAME", username); // Truyền lại username
            startActivity(intent);
        });

        // Thiết lập sự kiện click cho nút thay đổi mật khẩu
        changePasswordButton.setOnClickListener(v -> {
            changePassword(username); // Truyền username vào phương thức changePassword
        });
    }

    private void changePassword(String username) {
        String currentPassword = currentPasswordInput.getText().toString().trim();
        String newPassword = newPasswordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        // Kiểm tra các trường nhập
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please enter all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra mật khẩu mới và xác nhận mật khẩu mới
        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "New password and confirm password do not match.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Khởi tạo DatabaseHelper
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        User user = db.userDAO().getUser(username); // Lấy thông tin người dùng từ cơ sở dữ liệu

        // Kiểm tra mật khẩu hiện tại
        if (user != null && user.getPassword().equals(currentPassword)) {
            // Cập nhật mật khẩu mới
            user.setPassword(newPassword);
            db.userDAO().updateUser(user);
            Toast.makeText(this, "Change password successfully.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(ChangePasswordActivity.this, UserProfileActivity.class);
            intent.putExtra("USERNAME", username); // Truyền lại username
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Current password is incorrect.", Toast.LENGTH_SHORT).show();
        }
    }
}
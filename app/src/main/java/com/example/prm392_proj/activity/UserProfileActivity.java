package com.example.prm392_proj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_proj.R;
import com.example.prm392_proj.database.DatabaseHelper;
import com.example.prm392_proj.dao.RecipeDao;
import com.example.prm392_proj.model.User;

public class UserProfileActivity extends AppCompatActivity {

    private TextView textViewProfileName;
    private TextView textViewRecipeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Nhận profileName và userId từ Intent
        String profileName = getIntent().getStringExtra("PROFILE_NAME");
        int userId = getIntent().getIntExtra("USER_ID", -1);

        // Hiển thị profileName
        textViewProfileName = findViewById(R.id.profile_name);
        textViewProfileName.setText(profileName);

        // Khởi tạo TextView để hiển thị số lượng công thức
        textViewRecipeCount = findViewById(R.id.recipe_count);

        // Lấy số lượng công thức từ Room
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        RecipeDao recipeDao = db.recipeDao();

        // Sử dụng một thread để truy vấn số lượng công thức
        new Thread(() -> {
            int recipeCount = recipeDao.countRecipesByUserId(userId);
            runOnUiThread(() -> textViewRecipeCount.setText(String.valueOf(recipeCount)));
        }).start();

        // Thiết lập menu
        ImageView menuIcon = findViewById(R.id.menu_icon);
        menuIcon.setOnClickListener(this::showPopupMenu);

        // Thiết lập nút quay lại
        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        var sharedPreferences = getSharedPreferences("vclclgtclgmcs", MODE_PRIVATE);
        String username = sharedPreferences.getString("USERNAME", null);

        // Nếu username là null, có thể lấy từ Intent
        if (username == null) {
            username = getIntent().getStringExtra("USERNAME");
        }

        DatabaseHelper db = DatabaseHelper.getInstance(this);
        User user = db.userDAO().getUser(username);

        if (user != null) {
            TextView profileName = findViewById(R.id.profile_name);
            profileName.setText(user.getProfileName());
        }
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.user_menu, popupMenu.getMenu());

        // Lấy username từ SharedPreferences thay vì Intent
        var sharedPreferences = getSharedPreferences("vclclgtclgmcs", MODE_PRIVATE);
        String username = sharedPreferences.getString("USERNAME", null);
        String email = sharedPreferences.getString("EMAIL", null);
        String profileName = getIntent().getStringExtra("PROFILE_NAME");

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.edit_profile) {
                // Chuyển đến EditUserProfileActivity và truyền username
                Intent editProfileIntent = new Intent(UserProfileActivity.this, EditUserProfileActivity.class);
                editProfileIntent.putExtra("USERNAME", username);           // Truyền username
                editProfileIntent.putExtra("EMAIL", email);                 // Truyền email
                editProfileIntent.putExtra("PROFILE_NAME", profileName);    // Truyền profileName
                Log.d("UserProfileActivity", "Username: " + username);
                Log.d("UserProfileActivity", "Email: " + email);
                startActivity(editProfileIntent);
                return true;
            } else if (item.getItemId() == R.id.change_password) {
                // Chuyển đến ChangePasswordActivity
                Intent changePasswordIntent = new Intent(UserProfileActivity.this, ChangePasswordActivity.class);
                changePasswordIntent.putExtra("USERNAME", username);
                startActivity(changePasswordIntent);
                return true;
            } else {
                return false;
            }
        });
        popupMenu.show();
    }
}
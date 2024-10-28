package com.example.prm392_proj.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prm392_proj.R;

public class TestScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        var mainScreenJumpBtn = findViewById(R.id.mainScreenJump);
        var mainScreenIntent = new Intent(this, MainActivity.class);
        mainScreenJumpBtn.setOnClickListener(v -> startActivity(mainScreenIntent));

        var signUpJumpBtn = findViewById(R.id.signUpJump);
        var signUpJumpIntent = new Intent(this, SignUpActivity.class);
        signUpJumpBtn.setOnClickListener(v -> startActivity(signUpJumpIntent));


        var signInJumpBtn = findViewById(R.id.signInJump);
        var signInJumpIntent = new Intent(this, SignInActivity.class);
        signInJumpBtn.setOnClickListener(v -> startActivity(signInJumpIntent));


        var userProfileJump = findViewById(R.id.user_profile_jump);
        var userProfileIntent = new Intent(this, UserProfileActivity.class);
        userProfileJump.setOnClickListener(v -> startActivity(userProfileIntent));

        var searchJumpBtn = findViewById(R.id.searchJump);
        var searchJumpIntent = new Intent(this, SearchActivity.class);
        searchJumpBtn.setOnClickListener(v -> startActivity(searchJumpIntent));
    }
}
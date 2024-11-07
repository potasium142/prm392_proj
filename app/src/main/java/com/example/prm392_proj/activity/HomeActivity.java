package com.example.prm392_proj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prm392_proj.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        var sr = getSharedPreferences("vclclgtclgmcs", MODE_PRIVATE);

        TextView usernameText = findViewById(R.id.hello_username);

        var username = sr.getString("USERNAME", "<user>");

        usernameText.setText("Hello " + username);

        var avt = findViewById(R.id.avt);

        var addButton = findViewById(R.id.floatingActionButton);

        addButton.setOnClickListener(v -> {
            var addIntent = new Intent(HomeActivity.this, RecipeAddNewActivity.class);
            startActivity(addIntent);
        });

        avt.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, UserProfileActivity.class);
            intent.putExtra("PROFILE_NAME", username);
            startActivity(intent);
        });
    }
}
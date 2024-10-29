package com.example.prm392_proj.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prm392_proj.R;
import com.example.prm392_proj.database.DatabaseHelper;

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
        var editor = sr.edit();

        TextView usernameText = findViewById(R.id.username_text);

        var username = sr.getString("USERNAME", "<user>");

        usernameText.setText(username);


        var db = DatabaseHelper.getInstance(this);

        var recipeDao = db.recipeDao();

        db.recipeDao()
                .getAllRecipes();
    }
}
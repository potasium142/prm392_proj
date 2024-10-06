package com.example.prm392_proj.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_proj.R;

import java.util.List;

import entities.models.RecipeEntity;

public class UserProfileActitvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile_actitvity);
        RecyclerView recyclerView = findViewById(R.id.recipe_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
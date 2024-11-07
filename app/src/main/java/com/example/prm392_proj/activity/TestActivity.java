package com.example.prm392_proj.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_proj.R;
import com.example.prm392_proj.adapter.Test;
import com.example.prm392_proj.model.Recipe;
import com.example.prm392_proj.repository.RecipeRepository;

import java.util.List;


public class TestActivity extends AppCompatActivity {
    private RecipeRepository recipeRepository;
    private Test adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);


//        this.deleteDatabase("PRM392_final_project");


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Khởi tạo repository và adapter
        recipeRepository = new RecipeRepository(this.getApplication());
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new Test(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        recipeRepository.getAllRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if (recipes != null && !recipes.isEmpty()) {
                    adapter.setRecipes(recipes);
                    Log.d("TestActivity", "Dữ liệu Recipe đã được cập nhật lên UI.");
                } else {
                    Log.e("TestActivity", "Recipe list is empty or null.");
                }
            }
        });
    }
}
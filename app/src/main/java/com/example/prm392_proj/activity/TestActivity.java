package com.example.prm392_proj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prm392_proj.R;
import com.example.prm392_proj.adapter.Test;
import com.example.prm392_proj.model.Recipe;
import com.example.prm392_proj.repository.RecipeRepository;


public class TestActivity extends AppCompatActivity {
    int EDIT_RECIPE_ACTIVITY_REQUEST_CODE = 1;
    RecipeRepository recipeRepository;


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
        Test adapter = new Test(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        recipeRepository.getAllRecipes().observe(this, recipes -> {
            if (recipes != null && !recipes.isEmpty()) {
                for (Recipe recipe : recipes) {
                    Log.d("Recipe Time", "Recipe time: " + recipe.getTime());
                }
                adapter.setRecipes(recipes);
            } else {
                Log.e("TestActivity", "Recipe list is empty or null.");
            }
        });

    }


    public void startEditRecipeActivity(Recipe recipe) {
        Intent intent = new Intent(this, RecipeIngredient.class);
        intent.putExtra("recipe", recipe);
        intent.putExtra("IMAGE_URL", recipe.getPicture());
        intent.putExtra("TIME", recipe.getTime());
        startActivityForResult(intent, EDIT_RECIPE_ACTIVITY_REQUEST_CODE);
    }
}

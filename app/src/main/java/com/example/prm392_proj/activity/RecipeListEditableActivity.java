package com.example.prm392_proj.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_proj.R;
import com.example.prm392_proj.adapter.RecipeListEditableAdapter;
import com.example.prm392_proj.model.Recipe;
import com.example.prm392_proj.repository.RecipeRepository;
import com.google.android.material.snackbar.Snackbar;

public class RecipeListEditableActivity extends AppCompatActivity {
    int EDIT_RECIPE_ACTIVITY_REQUEST_CODE = 1;
    RecipeRepository recipeRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe_list_editable);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        });

        recipeRepository = new RecipeRepository(this.getApplication());
        RecyclerView recyclerView = findViewById(R.id.recipe_recycler_view);
        RecipeListEditableAdapter adapter = new RecipeListEditableAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeRepository.getAllRecipes().observe(this, recipes -> {
            adapter.setRecipes(recipes);
        });
    }

    public void startEditRecipeActivity(Recipe recipe) {
        Intent intent = new Intent(this, RecipeEditableActivity.class);
        intent.putExtra("recipe", recipe);
        startActivityForResult(intent, EDIT_RECIPE_ACTIVITY_REQUEST_CODE);
    }

    public void deleteRecipe(Recipe recipe) {
        new AlertDialog.Builder(this).setTitle("Delete recipe")
                .setMessage("Are you sure you want to delete this recipe?")
                .setPositiveButton("OK", (dialog, which) -> {
                    recipeRepository.delete(recipe);
                    Snackbar.make(findViewById(android.R.id.content),
                            "Recipe deleted",
                            Snackbar.LENGTH_SHORT).setAction("Undo", v -> {
                        recipeRepository.insert(recipe);
                    }).setDuration(5000).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
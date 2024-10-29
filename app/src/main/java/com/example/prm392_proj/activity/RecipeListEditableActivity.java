package com.example.prm392_proj.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_proj.R;
import com.example.prm392_proj.adapter.RecipeListEditableAdapter;
import com.example.prm392_proj.dao.RecipeDao;
import com.example.prm392_proj.model.Recipe;
import com.example.prm392_proj.repository.RecipeRepository;

public class RecipeListEditableActivity extends AppCompatActivity {
    public RecipeDao recipeListEditableActivity;
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

        recipeRepository = new RecipeRepository(this.getApplication());
        RecyclerView recyclerView = findViewById(R.id.recipe_recycler_view);
        RecipeListEditableAdapter adapter = new RecipeListEditableAdapter(new RecipeListEditableAdapter.WordDiff(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeRepository.getAllRecipes().observe(this, products -> {
            adapter.submitList(products);
        });
    }

    public void startEditRecipeActivity(Recipe current) {
    }
}
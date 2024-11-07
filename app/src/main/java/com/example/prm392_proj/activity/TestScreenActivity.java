package com.example.prm392_proj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prm392_proj.R;
import com.example.prm392_proj.model.Comment;
import com.example.prm392_proj.model.Recipe;
import com.example.prm392_proj.repository.RecipeRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestScreenActivity extends AppCompatActivity {
    private RecipeRepository recipeRepository; // Declare RecipeRepository

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

        var userProfileJumpBtn = findViewById(R.id.userProfileJump);
        var userProfileJumpIntent = new Intent(this, UserProfileActivity.class);
        userProfileJumpBtn.setOnClickListener(v -> startActivity(userProfileJumpIntent));

        var searchJumpBtn = findViewById(R.id.searchJump);
        var searchJumpIntent = new Intent(this, SearchActivity.class);
        searchJumpBtn.setOnClickListener(v -> startActivity(searchJumpIntent));

        var allRecipesButton = findViewById(R.id.all_recipes_button);
        var allRecipesIntent = new Intent(this, RecipeListEditableActivity.class);
        allRecipesButton.setOnClickListener(v -> startActivity(allRecipesIntent));

        var commentButton = findViewById(R.id.testcomment);
        var commentIntent = new Intent(this, ReviewActivity.class);
        commentButton.setOnClickListener(v -> startActivity(commentIntent));


        // Initialize the RecipeRepository
        recipeRepository = new RecipeRepository(getApplication());

        // Observe the recipe data from the repository and log it
        recipeRepository.getAllRecipes().observe(this, recipes -> {
            if (recipes != null) {
                logAllRecipes(recipes); // Call method to log the list of recipes
            }
        });

    }

    // Method to log all recipes from the repository


    private void logAllRecipes(List<Recipe> recipes) {
        Log.d("TestScreenActivity", "Listing all recipes:");

        // Set the desired date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());

        for (Recipe recipe : recipes) {
            Date recipeDate = recipe.getCreationDate();

            if (recipeDate != null) {
                // Format the creation date if it's not null
                String formattedDate = dateFormat.format(recipeDate);
                Log.d("TestScreenActivity", "Recipe ID: " + recipe.getId() + ", Name: " + recipe.getDishName() + ", Date: " + formattedDate);
            } else {
                // Log a message if the creation date is null
                Log.d("TestScreenActivity", "Recipe ID: " + recipe.getId() + ", Name: " + recipe.getDishName() + ", Date: Not Available");
            }
        }
    }

}
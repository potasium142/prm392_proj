package com.example.prm392_proj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_proj.R;
import com.example.prm392_proj.adapter.Test;
import com.example.prm392_proj.model.Recipe;
import com.example.prm392_proj.repository.RecipeRepository;
import com.example.prm392_proj.repository.UserRepository;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private Test recipeAdapter;
    private RecyclerView recyclerView;
    private RecipeRepository recipeRepository;  // Direct reference to the repository
    private UserRepository userRepository;      // Add UserRepository for the adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.home_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the repositories
        recipeRepository = new RecipeRepository(getApplication());
        userRepository = new UserRepository(getApplication()); // Initialize UserRepository

        // Get the LiveData from the repository and observe it
        LiveData<List<Recipe>> allRecipes = recipeRepository.getAllRecipes();

        // Observe the LiveData to update the RecyclerView
        allRecipes.observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                // Initialize the adapter, passing the context and the necessary repositories
                recipeAdapter = new Test(HomeActivity.this, userRepository);
                recipeAdapter.setRecipes(recipes);  // Pass the updated recipe list to the adapter
                recyclerView.setAdapter(recipeAdapter);
            }
        });

        // Example: Setting up the username and other elements
        var sr = getSharedPreferences("vclclgtclgmcs", MODE_PRIVATE);
        TextView usernameText = findViewById(R.id.hello_username);
        var username = sr.getString("USERNAME", "<user>");
        usernameText.setText("Hello " + username);

        // Set up the Avatar click listener
        var avt = findViewById(R.id.avt);
        avt.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, UserProfileActivity.class);
            intent.putExtra("PROFILE_NAME", username);
            startActivity(intent);
        });

        // Set up the Add New Recipe button
        var addButton = findViewById(R.id.floatingActionButton);
        addButton.setOnClickListener(v -> {
            Intent addIntent = new Intent(HomeActivity.this, RecipeAddNewActivity.class);
            startActivity(addIntent);
        });
    }
}

package com.example.prm392_proj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SearchView;
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
    private RecipeRepository recipeRepository;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.home_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the repositories
        recipeRepository = new RecipeRepository(getApplication());
        userRepository = new UserRepository(getApplication());

        // Get the LiveData from the repository and observe it
        LiveData<List<Recipe>> allRecipes = recipeRepository.getAllRecipes();
        allRecipes.observe(this, recipes -> {
            // Initialize the adapter, passing the context and the necessary repositories
            recipeAdapter = new Test(HomeActivity.this, userRepository);
            recipeAdapter.setRecipes(recipes);
            recyclerView.setAdapter(recipeAdapter);
        });

        // Set up username display
        var sr = getSharedPreferences("vclclgtclgmcs", MODE_PRIVATE);
        TextView usernameText = findViewById(R.id.hello_username);
        var username = sr.getString("USERNAME", "<user>");
        usernameText.setText("Hello " + username);

        // Avatar click listener to open UserProfileActivity
        var avt = findViewById(R.id.avt);
        avt.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, UserProfileActivity.class);
            intent.putExtra("PROFILE_NAME", username);
            startActivity(intent);
        });

        // Add New Recipe button
        var addButton = findViewById(R.id.floatingActionButton);
        addButton.setOnClickListener(v -> {
            Intent addIntent = new Intent(HomeActivity.this, RecipeAddNewActivity.class);
            startActivity(addIntent);
        });

        // Set up SearchView to open SearchActivity on click
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnClickListener(v -> {
            Intent searchIntent = new Intent(HomeActivity.this, SearchActivity.class);
            startActivity(searchIntent);
        });
    }
}

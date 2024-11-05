package com.example.prm392_proj.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_proj.R;
import com.example.prm392_proj.adapter.SearchActivityAdapter;
import com.example.prm392_proj.model.Recipe;
import com.example.prm392_proj.repository.RecipeRepository;
import com.example.prm392_proj.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private SearchActivityAdapter adapter;
    private List<Recipe> recipeList; // List of recipes
    private RecipeRepository recipeRepository; // Recipe repository
    private TextView resultsCountTextView; // TextView for results count
    private EditText searchInput; // Search input EditText

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // Set grid layout with 2 columns

        // Initialize the RecipeRepository
        recipeRepository = new RecipeRepository(getApplication());

        // Initialize UserRepository
        UserRepository userRepository = new UserRepository(getApplication());

        // Initialize adapter with an empty list
        recipeList = new ArrayList<>();
        adapter = new SearchActivityAdapter(recipeList, userRepository, this);
        recyclerView.setAdapter(adapter);

        // Initialize the TextView for displaying results count
        resultsCountTextView = findViewById(R.id.signin_header4);

        // Initialize the EditText for search input
        searchInput = findViewById(R.id.search_input);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString();
                adapter.filter(query, "All"); // Only apply query-based filtering
                updateResultsCount();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish()); // Use finish() to go back

        // Load recipes from repository and update the list
        recipeRepository.getAllRecipes().observe(this, recipes -> {
            if (recipes != null) {
                updateRecipeList(recipes);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Method to update the recipe list and notify adapter
    private void updateRecipeList(List<Recipe> recipes) {
        recipeList.clear();
        recipeList.addAll(recipes);
        adapter.updateRecipes(recipes);
        updateResultsCount();
    }

    // Method to update results count display
    private void updateResultsCount() {
        int resultsCount = recipeList.size();
        resultsCountTextView.setText(resultsCount + " results");
    }
}

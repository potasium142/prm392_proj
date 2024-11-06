package com.example.prm392_proj.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
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
import com.example.prm392_proj.dialog.FilterBottomSheetDialog;
import com.example.prm392_proj.model.Recipe;
import com.example.prm392_proj.repository.RecipeRepository;
import com.example.prm392_proj.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements FilterBottomSheetDialog.FilterListener {
    private SearchActivityAdapter adapter;
    private List<Recipe> recipeList;
    private RecipeRepository recipeRepository;
    private TextView resultsCountTextView;
    private EditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        RecyclerView recyclerView = findViewById(R.id.instructionsRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        recipeRepository = new RecipeRepository(getApplication());
        UserRepository userRepository = new UserRepository(getApplication());

        recipeList = new ArrayList<>();
        adapter = new SearchActivityAdapter(recipeList, userRepository, this);
        recyclerView.setAdapter(adapter);

        resultsCountTextView = findViewById(R.id.signin_header4);
        searchInput = findViewById(R.id.search_input);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString();
                // Filter with default filter choice and star rating values
                adapter.filter(query, "All", "All");
                updateResultsCount();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        Button filterButton = findViewById(R.id.search_filter_button);
        filterButton.setOnClickListener(v -> {
            FilterBottomSheetDialog filterDialog = new FilterBottomSheetDialog();
            filterDialog.show(getSupportFragmentManager(), "FilterBottomSheetDialog");
        });

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

    @Override
    public void onFilterApplied(String filterChoice, String starRating) {
        Log.d("FilterApplied", "Selected filter: " + filterChoice + ", Star Rating: " + starRating);
        adapter.filter(searchInput.getText().toString(), filterChoice, starRating);
        updateResultsCount();
    }


    private void updateRecipeList(List<Recipe> recipes) {
        recipeList.clear();
        recipeList.addAll(recipes);
        adapter.updateRecipes(recipes);
        updateResultsCount();
    }

    private void updateResultsCount() {
        int resultsCount = recipeList.size();
        resultsCountTextView.setText(resultsCount + " results");
    }
}

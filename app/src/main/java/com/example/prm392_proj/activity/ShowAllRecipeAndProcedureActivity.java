package com.example.prm392_proj.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prm392_proj.R;
import com.example.prm392_proj.adapter.IngredientAdapter;
import com.example.prm392_proj.adapter.InstructionAdapter;
import com.example.prm392_proj.dao.IngredientDao;
import com.example.prm392_proj.dao.InstructionDao;
import com.example.prm392_proj.database.DatabaseHelper;
import com.example.prm392_proj.viewmodel.RecipeViewModel;

public class ShowAllRecipeAndProcedureActivity extends AppCompatActivity {
    private static final String TAG = "ShowAllRecipeAndProcedureActivity";
    private IngredientAdapter ingredientAdapter;
    private InstructionAdapter instructionAdapter;
    private RecipeViewModel recipeViewModel;
    private TextView dishTitle;
    private TextView countItemIngredient;
    private TextView countItemInstruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recipe_and_procedure);


        dishTitle = findViewById(R.id.dishTitle);
        countItemIngredient = findViewById(R.id.countItemIngredient);
        countItemInstruction = findViewById(R.id.countItemInstruction);


        String dishName = getIntent().getStringExtra("DISH_NAME");
        int recipeId = getIntent().getIntExtra("RECIPE_ID", -1);


        if (dishName != null) {
            dishTitle.setText(dishName);
        } else {
            Log.e(TAG, "Dish name not found in intent extras");
        }


        // Khởi tạo RecyclerView cho Ingredient
        RecyclerView ingredientRecyclerView = findViewById(R.id.recyclerView);
        ingredientAdapter = new IngredientAdapter();
        ingredientRecyclerView.setAdapter(ingredientAdapter);
        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Khởi tạo RecyclerView cho Instruction
        RecyclerView instructionRecyclerView = findViewById(R.id.recyclerViewProcudure);
        instructionAdapter = new InstructionAdapter();
        instructionRecyclerView.setAdapter(instructionAdapter);
        instructionRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Khởi tạo ViewModel và lấy dữ liệu
        recipeViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                IngredientDao ingredientDao = DatabaseHelper.getInstance(getApplicationContext()).ingredientDao();
                InstructionDao instructionDao = DatabaseHelper.getInstance(getApplicationContext()).instructionDao();
                return (T) new RecipeViewModel(recipeId, ingredientDao, instructionDao);
            }
        }).get(RecipeViewModel.class);


        // Quan sát dữ liệu Ingredient và cập nhật Adapter
        recipeViewModel.getIngredients().observe(this, ingredients -> {
            if (ingredients != null && !ingredients.isEmpty()) {
                ingredientAdapter.setIngredientList(ingredients);
                countItemIngredient.setText(ingredients.size() + " items");
            } else {
                Log.e(TAG, "No ingredients found for Recipe ID: " + recipeId);
            }
        });


        // Quan sát dữ liệu Instruction và cập nhật Adapter
        recipeViewModel.getInstructions().observe(this, instructions -> {
            if (instructions != null && !instructions.isEmpty()) {
                instructionAdapter.setInstructionList(instructions);
                countItemInstruction.setText(instructions.size() + " steps");
            } else {
                Log.e(TAG, "No instructions found for Recipe ID: " + recipeId);
            }
        });


        // Nút quay lại
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish());
    }
}
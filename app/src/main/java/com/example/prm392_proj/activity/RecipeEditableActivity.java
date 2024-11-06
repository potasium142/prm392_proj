package com.example.prm392_proj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.viewpager2.widget.ViewPager2;

import com.example.prm392_proj.R;
import com.example.prm392_proj.adapter.RecipeEditableViewPagerAdapter;
import com.example.prm392_proj.dialog.InputDialog;
import com.example.prm392_proj.model.Ingredient;
import com.example.prm392_proj.model.Instruction;
import com.example.prm392_proj.model.Recipe;
import com.example.prm392_proj.model.User;
import com.example.prm392_proj.repository.IngredientRepository;
import com.example.prm392_proj.repository.InstructionRepository;
import com.example.prm392_proj.repository.RecipeRepository;
import com.example.prm392_proj.repository.UserRepository;
import com.example.prm392_proj.util.InputValidation;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipeEditableActivity extends AppCompatActivity {
    UserRepository userRepository;
    Recipe recipe;
    TextView dishTittle;
    TextView totalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_editable);

        userRepository = new UserRepository(this.getApplication());

        Intent intent = getIntent();
        recipe = (Recipe) intent.getSerializableExtra("recipe");

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Set dish name
        dishTittle = findViewById(R.id.dishTitle);
        dishTittle.setText(recipe.getDishName());
        ImageView dishNameEditButton = findViewById(R.id.dishNameEditButton);
        dishNameEditButton.setOnClickListener(v -> onEditDishNameButton());

        totalTime = findViewById(R.id.totalTime);
        totalTime.setText(String.valueOf(recipe.getTotalTime()));
        ImageButton totalTimeEditButton = findViewById(R.id.totalTimeEditButton);
        totalTimeEditButton.setOnClickListener(v -> onEditTotalTimeButton());

        ImageView dishImageView = findViewById(R.id.foodImage);
        Picasso.get().load(recipe.getPicture()).into(dishImageView);

        User user = userRepository.getUserById(recipe.getUserCreatorId());
        TextView userChannel = findViewById(R.id.userChannel);
        userChannel.setText(user.getProfileName());

        IngredientRepository ingredientRepository = new IngredientRepository(this.getApplication());
        List<Ingredient> ingredients = ingredientRepository.getAllIngredientsByRecipeIdSync(recipe.getId());

        InstructionRepository instructionRepository = new InstructionRepository(this.getApplication());
        List<Instruction> instructions = instructionRepository.getAllInstructionsByRecipeIdSync(recipe.getId());

        var adapter = new RecipeEditableViewPagerAdapter(this, ingredients, instructions);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager2);
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Ingredients");
                    break;
                case 1:
                    tab.setText("Procedures");
                    break;
            }
        }).attach();
    }

    public void onEditDishNameButton() {
        InputDialog inputDialog = new InputDialog(this, recipe.getDishName());
        inputDialog.setOnEnterListener(input -> {
            recipe.setDishName(input);
            dishTittle.setText(input);
        });

        InputValidation inputValidation = new InputValidation("Dish Name");
        inputValidation.isRequired = true;
        inputDialog.setValidation(inputValidation);

        inputDialog.show();
    }

    public void onEditTotalTimeButton() {
        InputDialog inputDialog = new InputDialog(this, String.valueOf(recipe.getTotalTime()));
        inputDialog.setOnEnterListener(input -> {
            recipe.setTotalTime(Integer.parseInt(input));
            totalTime.setText(input);
        });

        InputValidation inputValidation = new InputValidation("Total time");
        inputValidation.isRequired = true;
        inputValidation.isInteger = true;
        inputValidation.isPositiveNumber = true;
        inputDialog.setValidation(inputValidation);

        inputDialog.show();
    }
}


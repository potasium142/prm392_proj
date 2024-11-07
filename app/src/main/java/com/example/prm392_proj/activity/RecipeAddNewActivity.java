package com.example.prm392_proj.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecipeAddNewActivity extends AppCompatActivity {
    public static final int REQUEST_CHANGE_IMAGE = 500;
    UserRepository userRepository;
    Recipe recipe;
    TextView dishTittle;
    TextView totalTime;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_add_new);

        userRepository = new UserRepository(this.getApplication());

        var sharedPreferencesUser = getSharedPreferences("vclclgtclgmcs", MODE_PRIVATE);
        String username = sharedPreferencesUser.getString("USERNAME", "null");
        username = "admin";
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            finish();
        }

        RecipeRepository recipeRepository = new RecipeRepository(this.getApplication());
        recipe = Recipe.builder()
                .userCreatorId(user.getId())
                .totalTime(10)
                .dishName("Click edit to change name")
                .picture("https://www.allrecipes.com/thmb/1blq_he4MHCz2acTU7arELCnGrI=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/8576313_Mediterranean-Baked-Cod-with-Lemon_Brenda-Venable_4x3-b34ff9cd504b4aca9ba74d5ca8ba0c4d.jpg")
                .description("A recipe")
                .creationDate(new Date())
                .build();

        sharedPreferences = getSharedPreferences("edit_recipe", MODE_PRIVATE);
        sharedPreferences.edit().putString("changed", "").apply();

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

        TextView userChannel = findViewById(R.id.userChannel);
        userChannel.setText(user.getProfileName());

        IngredientRepository ingredientRepository = new IngredientRepository(this.getApplication());
        List<Ingredient> ingredients = ingredientRepository.getAllIngredientsByRecipeIdSync(recipe.getId());

        InstructionRepository instructionRepository = new InstructionRepository(this.getApplication());
        List<Instruction> instructions = instructionRepository.getAllInstructionsByRecipeIdSync(
                recipe.getId());

        ImageButton changeImageButton = findViewById(R.id.changeImageButton);
        changeImageButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, EditImageUrlActivity.class);
            intent1.putExtra("imageUrl", recipe.getPicture());
            startActivityForResult(intent1, REQUEST_CHANGE_IMAGE);
        });

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

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            String changed = sharedPreferences.getString("changed", null);
            if (changed.equals("changed")) {
                new AlertDialog.Builder(this).setTitle("Discard changes?")
                        .setMessage(
                                "You have unsaved changes. Are you sure you want to discard them?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            finish();
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .show();
            } else {
                finish();
            }
        });

        ImageView saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> {
            Long recipeId = recipeRepository.insert(recipe);
            recipe.setId(Math.toIntExact(recipeId));
            sharedPreferences.edit().putString("changed", "").apply();


            ingredientRepository.deleteAllIngredientsByRecipeId(recipe.getId());
            ingredients.forEach(ingredient -> {
                ingredient.setRecipeId(recipe.getId());
                ingredientRepository.insert(ingredient);
            });

            instructionRepository.deleteAllInstructionsByRecipeId(recipe.getId());
            instructions.forEach(instruction -> {
                instruction.setRecipeId(recipe.getId());
                instructionRepository.insert(instruction);
            });

            Toast.makeText(this, "Recipe saved", Toast.LENGTH_SHORT).show();
        });
    }

    public void onEditDishNameButton() {
        InputDialog inputDialog = new InputDialog(this, recipe.getDishName());
        inputDialog.setOnEnterListener(input -> {
            recipe.setDishName(input);
            dishTittle.setText(input);

            sharedPreferences.edit().putString("changed", "changed").apply();
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

            sharedPreferences.edit().putString("changed", "changed").apply();
        });

        InputValidation inputValidation = new InputValidation("Total time");
        inputValidation.isRequired = true;
        inputValidation.isInteger = true;
        inputValidation.isPositiveNumber = true;
        inputDialog.setValidation(inputValidation);

        inputDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHANGE_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                String imageUrl = data.getStringExtra("imageUrl");
                recipe.setPicture(imageUrl);
                ImageView dishImageView = findViewById(R.id.foodImage);
                Picasso.get().load(imageUrl).into(dishImageView);

                sharedPreferences.edit().putString("changed", "changed").apply();
            }
        }
    }
}


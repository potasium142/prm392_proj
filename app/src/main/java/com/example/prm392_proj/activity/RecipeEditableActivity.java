package com.example.prm392_proj.activity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.MimeTypeMap;
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
import com.example.prm392_proj.s3.S3ClientProvider;
import com.example.prm392_proj.util.InputValidation;
import com.example.prm392_proj.util.RandomAlphaDigit;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class RecipeEditableActivity extends AppCompatActivity {
    public static final int REQUEST_IMAGE_PICK = 1001;
    UserRepository userRepository;
    Recipe recipe;
    TextView dishTittle;
    TextView totalTime;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_editable);

        Intent intent = getIntent();
        recipe = (Recipe) intent.getSerializableExtra("recipe");

        sharedPreferences = getSharedPreferences("edit_recipe", MODE_PRIVATE);
        sharedPreferences.edit().putString("changed", "").apply();

        userRepository = new UserRepository(this.getApplication());

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
        List<Instruction> instructions = instructionRepository.getAllInstructionsByRecipeIdSync(
                recipe.getId());

        ImageButton changeImageButton = findViewById(R.id.changeImageButton);
        changeImageButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(Intent.ACTION_PICK);
            intent1.setType("image/*");
            startActivityForResult(intent1, REQUEST_IMAGE_PICK);
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
            RecipeRepository recipeRepository = new RecipeRepository(this.getApplication());
            recipeRepository.update(recipe);
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
        Log.d("onActivityResult", "requestCode: " + requestCode + ", resultCode: " + resultCode);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            if (data != null) {
                Uri imageUri = data.getData();

                String extension;

                //Check uri format to avoid null
                if (imageUri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
                    //If scheme is a content
                    final MimeTypeMap mime = MimeTypeMap.getSingleton();
                    extension = mime.getExtensionFromMimeType(this.getContentResolver()
                            .getType(imageUri));
                } else {
                    //If scheme is a File
                    //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
                    extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(imageUri.getPath()))
                            .toString());

                }

                String key = RandomAlphaDigit.generateRandomAlphaDigit(10);
                key = key + "." + extension;
                S3ClientProvider.uploadImageToSpaceInBackground(this,
                        imageUri,
                        "",
                        key,
                        new S3ClientProvider.UploadCallback() {
                            @Override
                            public void onSuccess(String imageUrl) {
                                Handler handler = new Handler(Looper.getMainLooper());

// Inside your background thread
                                handler.post(() -> {
                                    recipe.setPicture(imageUrl);
                                    ImageView dishImageView = findViewById(R.id.foodImage);
                                    Picasso.get().load(imageUrl).into(dishImageView);

                                    sharedPreferences.edit().putString("changed", "changed").apply();
                                });

                            }

                            @Override
                            public void onFailure(Exception e) {
                                Handler handler = new Handler(Looper.getMainLooper());

// Inside your background thread
                                handler.post(() -> {
                                    Toast.makeText(RecipeEditableActivity.this,
                                            "Failed to upload image",
                                            Toast.LENGTH_SHORT).show();
                                });
                            }
                        });
            }
        }
    }

}


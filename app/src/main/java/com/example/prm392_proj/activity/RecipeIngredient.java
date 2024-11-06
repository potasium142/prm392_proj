package com.example.prm392_proj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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
import com.example.prm392_proj.dao.IngredientDao;
import com.example.prm392_proj.database.DatabaseHelper;
import com.example.prm392_proj.viewmodel.RecipeViewModel;
import com.example.prm392_proj.viewmodel.UserViewModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class RecipeIngredient extends AppCompatActivity {
    private static final String TAG = "RecipeIngredientActivity";
    private RecipeViewModel recipeViewModel;
    private IngredientAdapter ingredientAdapter;
    private TextView dishTitle;
    private ImageView dishImage;
    private TextView timeView;
    private TextView userName;
    private TextView countItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingredient);


        // Khởi tạo các view
        dishTitle = findViewById(R.id.dishTitle);
        dishImage = findViewById(R.id.foodImage);
        timeView = findViewById(R.id.time);
        userName = findViewById(R.id.userChannel);
        countItem = findViewById(R.id.countItem);


        // Lấy dữ liệu từ Intent
        String dishName = getIntent().getStringExtra("DISH_NAME");
        String imageUrl = getIntent().getStringExtra("IMAGE_URL");
        String time = getIntent().getStringExtra("TIME");
        int recipeId = getIntent().getIntExtra("RECIPE_ID", -1);
        int userId = getIntent().getIntExtra("USER_ID", -1);


        // Hiển thị tên món ăn
        if (dishName != null) {
            dishTitle.setText(dishName);
        } else {
            Log.e(TAG, "Dish name not found in intent extras");
        }


        // Hiển thị thời gian
        if (timeView != null && time != null) {
            timeView.setText(time);
        } else {
            Log.e(TAG, "Time is missing in intent extras");
        }


        // Hiển thị hình ảnh món ăn
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(dishImage, new Callback() {
                @Override
                public void onSuccess() {}

                @Override
                public void onError(Exception e) {
                    Log.e(TAG, "Error loading image", e);
                }
            });
        } else {
            Log.e(TAG, "Image URL is missing or empty");
        }


        // Kiểm tra Recipe ID hợp lệ
        if (recipeId == -1) {
            Log.e(TAG, "Invalid Recipe ID, finishing activity.");
            finish();
            return;
        }


        // Thiết lập RecyclerView cho danh sách nguyên liệu
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ingredientAdapter = new IngredientAdapter();
        recyclerView.setAdapter(ingredientAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Khởi tạo ViewModel cho Recipe và Ingredient
        recipeViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                IngredientDao ingredientDao = DatabaseHelper.getInstance(getApplicationContext()).ingredientDao();
                return (T) new RecipeViewModel(recipeId, ingredientDao);
            }
        }).get(RecipeViewModel.class);


        // Danh sách Ingredient từ RecipeViewModel
        recipeViewModel.getIngredients().observe(this, ingredients -> {
            if (ingredients != null && !ingredients.isEmpty()) {
                Log.d(TAG, "Ingredients found: " + ingredients.size());
                ingredientAdapter.setIngredientList(ingredients);
                countItem.setText("Ingredients: " + ingredients.size());
            } else {
                Log.e(TAG, "No ingredients found for Recipe ID: " + recipeId);
            }
        });


        // Lấy thông tin người dùng từ UserViewModel
        if (userId != -1) {
            UserViewModel userViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
                @NonNull
                @Override
                public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                    return (T) new UserViewModel(getApplication(), userId);
                }
            }).get(UserViewModel.class);

            userViewModel.getUser().observe(this, user -> {
                if (user != null) {
                    userName.setText(user.getProfileName());
                } else {
                    Log.e(TAG, "User not found for ID: " + userId);
                }
            });
        } else {
            Log.e(TAG, "User ID is missing in intent extras");
        }


        // Chuyển đến trang RecipeProcedure khi nhấn vào nút
        Button procedureButton = findViewById(R.id.procedureButton);
        procedureButton.setOnClickListener(view -> {
            Intent intent = new Intent(RecipeIngredient.this, RecipeProcedure.class);
            intent.putExtra("RECIPE_ID", recipeId);
            intent.putExtra("IMAGE_URL", imageUrl);
            intent.putExtra("TIME", time);
            intent.putExtra("DISH_NAME", dishName);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });


        // Chuyển đến trang Bookmark khi nhấn vào icon
        ImageView bookmarkIcon = findViewById(R.id.iconBookMark);
        bookmarkIcon.setOnClickListener(view -> {
            Intent intent = new Intent(RecipeIngredient.this, ShowAllRecipeAndProcedureActivity.class);
            intent.putExtra("RECIPE_ID", recipeId);
            intent.putExtra("IMAGE_URL", imageUrl);
            intent.putExtra("TIME", time);
            intent.putExtra("DISH_NAME", dishName);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });


        TextView userName = findViewById(R.id.userChannel);
        userName.setOnClickListener(view -> {
            Intent intent = new Intent(RecipeIngredient.this, UserProfileActivity.class);
            intent.putExtra("RECIPE_ID", recipeId);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });


        ImageView profileImage = findViewById(R.id.profileImage);
        profileImage.setOnClickListener(view -> {
            Intent intent = new Intent(RecipeIngredient.this, UserProfileActivity.class);
            intent.putExtra("RECIPE_ID", recipeId);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });


        // Nút quay lại
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish());
    }
}

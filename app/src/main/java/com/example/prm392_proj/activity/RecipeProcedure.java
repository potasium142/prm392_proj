package com.example.prm392_proj.activity;

import android.content.Intent;
import android.nfc.Tag;
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
import com.example.prm392_proj.model.Comment;
import com.example.prm392_proj.adapter.IngredientAdapter;
import com.example.prm392_proj.adapter.InstructionAdapter;
import com.example.prm392_proj.dao.IngredientDao;
import com.example.prm392_proj.dao.InstructionDao;
import com.example.prm392_proj.database.DatabaseHelper;
import com.example.prm392_proj.viewmodel.RecipeViewModel;
import com.example.prm392_proj.viewmodel.UserViewModel;
import com.squareup.picasso.Picasso;

public class RecipeProcedure extends AppCompatActivity {
    private static final String TAG = "RecipeIngredientActivity";
    private RecipeViewModel recipeViewModel;
    private InstructionAdapter instructionAdapter;
    private TextView dishTitle;
    private ImageView dishImage;
    private TextView timeView;
    private TextView userName;
    private TextView countItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_procedure);


        // Nhận dữ liệu từ Intent
        int recipeId = getIntent().getIntExtra("RECIPE_ID", -1);
        String dishName = getIntent().getStringExtra("DISH_NAME");
        String imageUrl = getIntent().getStringExtra("IMAGE_URL");
        int totalTime = getIntent().getIntExtra("TOTAL_TIME", 1);
        int userId = getIntent().getIntExtra("USER_ID", -1);


        // Ánh xạ các view từ layout
        dishTitle = findViewById(R.id.dishTitle);
        dishImage = findViewById(R.id.foodImage);
        timeView = findViewById(R.id.time);
        userName = findViewById(R.id.userChannel);
        countItem = findViewById(R.id.countItem);


        // Hiển thị dữ liệu lên view
        if (dishName != null) {
            dishTitle.setText(dishName);
        } else {
            Log.e(TAG, "Dish name not found in intent extras");
        }

        // Hiển thị thời gian
        if (totalTime != -1) {
            timeView.setText(totalTime + " mins");
        } else {
            Log.e(TAG, "Total time is missing in intent extras");
        }

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(dishImage);
        }

        // Kiểm tra Recipe ID hợp lệ
        if (recipeId == -1) {
            Log.e(TAG, "Invalid Recipe ID, finishing activity.");
            finish();
            return;
        }


        // Thiết lập RecyclerView cho instruction
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        instructionAdapter = new InstructionAdapter();
        recyclerView.setAdapter(instructionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Khởi tạo ViewModel cho Recipe và Instruction
        recipeViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                InstructionDao instructionDao = DatabaseHelper.getInstance(getApplicationContext()).instructionDao();
                return (T) new RecipeViewModel(recipeId, instructionDao);
            }
        }).get(RecipeViewModel.class);


        recipeViewModel.getInstructions().observe(this, instructions -> {
            if (instructions != null && !instructions.isEmpty()) {
                Log.d(TAG, "Instrutions found: " + instructions.size());
                instructionAdapter.setInstructionList(instructions);
                countItem.setText("Instrutions: " + instructions.size());
            } else {
                Log.e(TAG, "No instrution found for Recipe ID: " + recipeId);
            }
        });


        // Lấy ID user
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


        // Nút chuyển sang trang Ingredient
        Button button = findViewById(R.id.ingredientButton);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(RecipeProcedure.this, RecipeIngredient.class);
            intent.putExtra("RECIPE_ID", recipeId);
            intent.putExtra("IMAGE_URL", imageUrl);
            intent.putExtra("TIME", totalTime);
            intent.putExtra("DISH_NAME", dishName);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });
        ImageView imageView1 = findViewById(R.id.menuButton);
        imageView1.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              Intent intent = new Intent(RecipeProcedure.this, Comment.class);
                                              startActivity(intent);
                                          }
                                      });
        //Button go to BookMark page


        // Nút chuyển sang trang Bookmark
        ImageView imageView = findViewById(R.id.iconBookMark);
        imageView.setOnClickListener(view -> {
            Intent intent = new Intent(RecipeProcedure.this, ShowAllRecipeAndProcedureActivity.class);
            intent.putExtra("RECIPE_ID", recipeId);
            intent.putExtra("IMAGE_URL", imageUrl);
            intent.putExtra("TIME", totalTime);
            intent.putExtra("DISH_NAME", dishName);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });


        TextView userName = findViewById(R.id.userChannel);
        userName.setOnClickListener(view -> {
            Intent intent = new Intent(RecipeProcedure.this, UserProfileActivity.class);
            intent.putExtra("RECIPE_ID", recipeId);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });


        ImageView profileImage = findViewById(R.id.profileImage);
        profileImage.setOnClickListener(view -> {
            Intent intent = new Intent(RecipeProcedure.this, UserProfileActivity.class);
            intent.putExtra("RECIPE_ID", recipeId);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });


        // Nút quay lại


        //Button back
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

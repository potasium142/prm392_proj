package com.example.prm392_proj.activity;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_proj.R;
import com.example.prm392_proj.model.Recipe;
import com.example.prm392_proj.model.User;
import com.example.prm392_proj.repository.UserRepository;
import com.squareup.picasso.Picasso;

public class RecipeEditableActivity extends AppCompatActivity {
    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_editable);

        userRepository = new UserRepository(this.getApplication());

        Intent intent = getIntent();
        Recipe recipe = (Recipe) intent.getSerializableExtra("recipe");

        // Set dish name
        TextView dishTittle = findViewById(R.id.dishTitle);
        dishTittle.setText(recipe.getDishName());

        ImageView dishImageView = findViewById(R.id.foodImage);
        Picasso.get().load(recipe.getPicture()).into(dishImageView);

        User user = userRepository.getUserById(recipe.getUserCreatorId());
        TextView userChannel = findViewById(R.id.userChannel);
        userChannel.setText(user.getProfileName());
    }
}


package com.example.prm392_proj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.prm392_proj.R;
import com.example.prm392_proj.adapter.RecipeEditableViewPagerAdapter;
import com.example.prm392_proj.dialog.InputDialog;
import com.example.prm392_proj.model.Recipe;
import com.example.prm392_proj.model.User;
import com.example.prm392_proj.repository.UserRepository;
import com.example.prm392_proj.util.InputValidation;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
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

        TextView totalTime = findViewById(R.id.totalTime);
        totalTime.setText(String.valueOf(recipe.getTotalTime()));
        ImageButton totalTimeEditButton = findViewById(R.id.totalTimeEditButton);
        totalTimeEditButton.setOnClickListener(v -> {
            InputDialog inputDialog = new InputDialog(this);
            inputDialog.setOnEnterListener(input -> {
                recipe.setTotalTime(Integer.parseInt(input));
                totalTime.setText(String.valueOf(recipe.getTotalTime()));
            });

            InputValidation inputValidation = new InputValidation("Total time");
            inputValidation.isRequired = true;
            inputValidation.isInteger = true;
            inputValidation.isPositiveNumber = true;
            inputDialog.setValidation(inputValidation);

            inputDialog.show();
        });

        ImageView dishImageView = findViewById(R.id.foodImage);
        Picasso.get().load(recipe.getPicture()).into(dishImageView);

        User user = userRepository.getUserById(recipe.getUserCreatorId());
        TextView userChannel = findViewById(R.id.userChannel);
        userChannel.setText(user.getProfileName());

        var adapter = new RecipeEditableViewPagerAdapter(this);
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
}


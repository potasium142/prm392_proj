package com.example.prm392_proj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.prm392_proj.R;

public class RecipeIngredient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingredient);

        // Button go to Procedure page
        Button button = findViewById(R.id.procedureButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeIngredient.this, RecipeProcedure.class);
                startActivity(intent);
            }
        });

        //Button go to BookMark page
        ImageView imageView = findViewById(R.id.iconBookMark);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeIngredient.this, BookMark.class);
                startActivity(intent);
            }
        });

        //Button back
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}


package com.example.prm392_proj.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_proj.R;
import com.example.prm392_proj.activity.RecipeIngredient;
import com.example.prm392_proj.model.Recipe;
import com.example.prm392_proj.model.User;
import com.example.prm392_proj.repository.UserRepository;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Test extends RecyclerView.Adapter<Test.ViewHolder> {
    private final Context context;
    private List<Recipe> recipes = new ArrayList<>();  // Ensure the list is initialized
    private UserRepository userRepository;  // Add UserRepository
    private LifecycleOwner lifecycleOwner;  // Add LifecycleOwner if needed for LiveData

    // Constructor for the adapter
    public Test(Context context) {
        this.context = context;

    }
    public Test(Context context, UserRepository userRepository) {
        this.context = context;
        this.userRepository = userRepository;

    }

    // Method to update the recipe list
    public void setRecipes(List<Recipe> recipes) {
        if (recipes != null) {
            this.recipes = recipes;
            notifyDataSetChanged();  // Notify adapter that data has changed
        } else {
            Log.e("TestAdapter", "Danh sách recipes rỗng hoặc null.");
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_detail, parent, false);  // Make sure you have correct layout
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe current = recipes.get(position);

        holder.productName.setText(current.getDishName());  // Set dish name to productName TextView

        // Get creator's name using the UserRepository
        User user = userRepository.getUserById(current.getUserCreatorId());
        holder.nameFood.setText(user != null ? "by " + user.getProfileName() : "by Unknown Creator");

        // Handle click event to open RecipeIngredient activity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecipeIngredient.class);
            intent.putExtra("DISH_NAME", current.getDishName());
            intent.putExtra("RECIPE_ID", current.getId());
            intent.putExtra("IMAGE_URL", current.getPicture());
            intent.putExtra("TOTAL_TIME", current.getTotalTime());
            intent.putExtra("USER_ID", current.getUserCreatorId());

            // Logging the data for debugging
            Log.d("TestAdapter", "Dish Name: " + current.getDishName());
            Log.d("TestAdapter", "Recipe ID: " + current.getId());
            Log.d("TestAdapter", "Image URL: " + current.getPicture());
            Log.d("TestAdapter", "Time: " + current.getTotalTime());
            Log.d("TestAdapter", "User ID: " + current.getUserCreatorId());

            context.startActivity(intent);
        });

        // Load image using Picasso
        Picasso.get().load(current.getPicture()).into(holder.dishImageView);
    }

    @Override
    public int getItemCount() {
        return recipes.size();  // Return the size of the recipe list
    }

    // ViewHolder class to hold UI elements
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;  // This is the dish name
        TextView nameFood;     // This is the creator's name
        ImageView dishImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);  // Make sure this is the correct TextView ID for recipe name
            nameFood = itemView.findViewById(R.id.nameFood);        // Make sure this is the correct TextView ID for creator's name
            dishImageView = itemView.findViewById(R.id.imageTestDetail);  // Make sure this is the correct ImageView ID
        }
    }
}

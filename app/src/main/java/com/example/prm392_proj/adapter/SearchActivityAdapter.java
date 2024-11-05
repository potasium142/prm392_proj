package com.example.prm392_proj.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_proj.R;
import com.example.prm392_proj.model.Recipe;
import com.example.prm392_proj.model.User;
import com.example.prm392_proj.repository.UserRepository;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchActivityAdapter extends RecyclerView.Adapter<SearchActivityAdapter.ViewHolder> {
    private List<Recipe> recipes;
    private List<Recipe> fullRecipeList; // Copy of the full recipe list for filtering
    private UserRepository userRepository; // Store the UserRepository reference
    private LifecycleOwner lifecycleOwner;

    public SearchActivityAdapter(List<Recipe> recipes, UserRepository userRepository, LifecycleOwner lifecycleOwner) {
        this.recipes = recipes;
        this.fullRecipeList = new ArrayList<>(recipes); // Initialize full list for filtering
        this.userRepository = userRepository; // Use UserRepository
        this.lifecycleOwner = lifecycleOwner; // Pass LifecycleOwner
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_adapter_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.dishNameTextView.setText(recipe.getDishName());

        // Get the user by ID
        User user = userRepository.getUserById(recipe.getUserCreatorId());
        holder.creatorTextView.setText(user != null ? "by " + user.getProfileName() : "by Unknown Creator");

        // Load image using Picasso
        Picasso.get()
                .load(recipe.getPicture()) // URL to the image
                .into(holder.recipeImageView);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    // Method to update the recipes and refresh the full list
    public void updateRecipes(List<Recipe> recipes) {
        this.recipes.clear();
        this.recipes.addAll(recipes);
        this.fullRecipeList = new ArrayList<>(recipes); // Update the full list for filtering
        notifyDataSetChanged();
    }

    // Method to filter the recipe list based on search input and date filter
    public void filter(String query, String dateFilter) {
        if (query.isEmpty() && dateFilter.equals("All")) {
            recipes.clear();
            recipes.addAll(fullRecipeList); // Reset to full list if query is empty
        } else {
            List<Recipe> filteredList = new ArrayList<>();
            long currentTime = System.currentTimeMillis();

            for (Recipe recipe : fullRecipeList) {
                boolean matchesQuery = recipe.getDishName().toLowerCase().contains(query.toLowerCase()) ||
                        (userRepository.getUserById(recipe.getUserCreatorId()) != null &&
                                userRepository.getUserById(recipe.getUserCreatorId()).getProfileName().toLowerCase().contains(query.toLowerCase()));

                boolean matchesDate = true; // Default to true unless filtering by date
                Date recipeDate = recipe.getCreationDate();

                // Handle date filtering
                if (!dateFilter.equals("All")) {
                    if (dateFilter.equals("Newest")) {
                        matchesDate = recipeDate != null && recipeDate.after(new Date(currentTime - 86400000L)); // last 24 hours
                    } else if (dateFilter.equals("Oldest")) {
                        matchesDate = recipeDate != null && recipeDate.before(new Date(currentTime - 86400000L)); // recipes older than 24 hours
                    }
                }

                if (matchesQuery && matchesDate) {
                    filteredList.add(recipe);
                }
            }
            recipes.clear();
            recipes.addAll(filteredList);
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dishNameTextView;
        public TextView creatorTextView;
        public ImageView recipeImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            dishNameTextView = itemView.findViewById(R.id.dishNametextView);
            creatorTextView = itemView.findViewById(R.id.dishCreatortextView);
            recipeImageView = itemView.findViewById(R.id.recipeImage);
        }
    }
}

package com.example.prm392_proj.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_proj.R;
import com.example.prm392_proj.activity.RecipeListEditableActivity;
import com.example.prm392_proj.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipeListEditableAdapter extends RecyclerView.Adapter<RecipeListEditableAdapter.ViewHolder> {
    private final RecipeListEditableActivity recipeListEditableActivity;
    private List<Recipe> recipes = new ArrayList<>();

    public RecipeListEditableAdapter(RecipeListEditableActivity recipeListEditableActivity) {
        this.recipeListEditableActivity = recipeListEditableActivity;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    @Override
    public RecipeListEditableAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_recipe_item_editable, parent, false);
        return new RecipeListEditableAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe current = recipes.get(position);
        holder.recipe_name.setText(current.getDishName());
        holder.editButton.setOnClickListener(v -> {
            recipeListEditableActivity.startEditRecipeActivity(current);
        });
        holder.deleteButton.setOnClickListener(v -> {
            recipeListEditableActivity.deleteRecipe(current);
        });
        holder.totalTime.setText(String.valueOf(current.getTotalTime()));
        Picasso.get().load(current.getPicture()).into(holder.dishImageView);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipe_name, totalTime;
        Button editButton, deleteButton;
        ImageView dishImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            recipe_name = itemView.findViewById(R.id.recipe_name);
            totalTime = itemView.findViewById(R.id.totalTime);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            dishImageView = itemView.findViewById(R.id.recipeImage);
        }
    }
}

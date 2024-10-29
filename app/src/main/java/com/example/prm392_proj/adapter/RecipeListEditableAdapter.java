package com.example.prm392_proj.adapter;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_proj.R;
import com.example.prm392_proj.activity.RecipeListEditableActivity;
import com.example.prm392_proj.activity.TestScreenActivity;
import com.example.prm392_proj.model.Recipe;
import com.example.prm392_proj.repository.RecipeRepository;
import com.google.android.material.snackbar.Snackbar;

public class RecipeListEditableAdapter extends ListAdapter<Recipe, RecipeListEditableAdapter.RecipeViewHolder> {
    RecipeListEditableActivity recipeListEditableActivity;;

    public RecipeListEditableAdapter(@NonNull DiffUtil.ItemCallback<Recipe> diffCallback, RecipeListEditableActivity recipeListEditableActivity) {
        super(diffCallback);
        this.recipeListEditableActivity = recipeListEditableActivity;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_recipe_item_editable, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe current = getItem(position);
        holder.recipe_name.setText(current.getDishName());
//        holder.recipe_price.setText(String.valueOf(current.getPrice()));
//        holder.recipe_quantity.setText(String.valueOf(current.getQuantity()));
        holder.edit_button.setOnClickListener(v -> {
            recipeListEditableActivity.startEditRecipeActivity(current);
        });
//        holder.delete_button.setOnClickListener(v -> {
//            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
//            builder.setTitle("Delete Recipe");
//            builder.setMessage("Are you sure you want to delete this recipe?");
//            builder.setPositiveButton("OK", (dialog, id) -> {
//                Recipe tmpRecipe = current;
//                recipeListEditableActivity.recipeListEditableActivity.delete(current);
//                Toast.makeText(v.getContext(), "Recipe deleted successfully", Toast.LENGTH_SHORT)
//                        .show();
//
//                Snackbar snackbar = Snackbar.make(v, "Recipe deleted successfully", Snackbar.LENGTH_LONG);
//                snackbar.setAction("Undo", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        recipeListEditableActivity.recipeListEditableActivity.insert(tmpRecipe);
//                    }
//                });
//                snackbar.setDuration(5000);
//                snackbar.show();
//            });
//            builder.setNegativeButton("Cancel", (dialog, id) -> {
//            });
//            AlertDialog dialog = builder.create();
//            dialog.show();
//        });
    }

    public static class WordDiff extends DiffUtil.ItemCallback<Recipe> {

        @Override
        public boolean areItemsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
            return oldItem.equals(newItem);
        }
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView recipe_name;
//        TextView recipe_price;
//        TextView recipe_quantity;
//        Button delete_button;
        Button edit_button;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipe_name = itemView.findViewById(R.id.recipe_name);
//            recipe_price = itemView.findViewById(R.id.recipe_price);
//            recipe_quantity = itemView.findViewById(R.id.recipe_quantity);
//            delete_button = itemView.findViewById(R.id.delete_button);
            edit_button = itemView.findViewById(R.id.edit_button);
        }
    }
}

package com.example.prm392_proj.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prm392_proj.R;
import com.example.prm392_proj.activity.RecipeIngredient;
import com.example.prm392_proj.dao.RecipeDao;
import com.example.prm392_proj.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ingredientViewHolder> {
    private List<Ingredient> ingredientList = new ArrayList<>();

    @NonNull
    @Override
    public ingredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_ingredient_item, parent, false);
        return new ingredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ingredientViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);
        holder.dishName.setText(ingredient.getName());
        holder.amountItem.setText(ingredient.getAmount());
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public void setIngredientList(List<Ingredient> ingredients) {
        this.ingredientList = ingredients;
        notifyDataSetChanged();
    }

    public static class ingredientViewHolder extends RecyclerView.ViewHolder {
        private TextView dishName;
        private TextView amountItem;
        ConstraintLayout linearLineout;

        @SuppressLint("WrongViewCast")
        public ingredientViewHolder(@NonNull View itemView) {
            super(itemView);
            dishName = itemView.findViewById(R.id.dishTitle);
            amountItem = itemView.findViewById(R.id.amountItem);
            linearLineout = itemView.findViewById(R.id.recyclerView);
        }
    }
}

package com.example.prm392_proj.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_proj.R;
import com.example.prm392_proj.dialog.InputDialog;
import com.example.prm392_proj.dialog.InputDialog2;
import com.example.prm392_proj.frament.IngredientListEditableFragment;
import com.example.prm392_proj.model.Ingredient;
import com.example.prm392_proj.util.InputValidation;

import java.util.ArrayList;
import java.util.List;

public class IngredientListEditableAdapter extends RecyclerView.Adapter<IngredientListEditableAdapter.ViewHolder> {
    private IngredientListEditableFragment ingredientListEditableFragment;
    private List<Ingredient> ingredients = new ArrayList<>();

    public IngredientListEditableAdapter() {}

    public IngredientListEditableAdapter(IngredientListEditableFragment ingredientListEditableFragment) {
        this.ingredientListEditableFragment = ingredientListEditableFragment;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    @Override
    public IngredientListEditableAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_item_editable, parent, false);
        return new IngredientListEditableAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient current = ingredients.get(position);
        holder.ingredientName.setText(current.getName());
        holder.ingredientAmount.setText(String.valueOf(current.getAmount()));
        holder.editButton.setOnClickListener(v -> {
            ingredientListEditableFragment.onEditButtonClick(current);
        });
        holder.deleteButton.setOnClickListener(v -> {
            ingredientListEditableFragment.onDeleteButtonClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientName, ingredientAmount;
        ImageView editButton, deleteButton;
        Button addMoreButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.ingredientName);
            ingredientAmount = itemView.findViewById(R.id.ingredientAmount);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}

package com.example.prm392_proj.frament;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_proj.R;
import com.example.prm392_proj.adapter.IngredientListEditableAdapter;
import com.example.prm392_proj.dialog.InputDialog2;
import com.example.prm392_proj.model.Ingredient;
import com.example.prm392_proj.util.InputValidation;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class IngredientListEditableFragment extends Fragment {
    List<Ingredient> ingredients;
    IngredientListEditableAdapter adapter;
    TextView totalIngredient;

    public IngredientListEditableFragment(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingredient_list_editable, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.instructionsRecyclerView);
        adapter = new IngredientListEditableAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setIngredients(ingredients);

        totalIngredient = view.findViewById(R.id.totalInstruction);
        totalIngredient.setText(String.valueOf(ingredients.size()));


        Button addMoreButton = view.findViewById(R.id.addMoreButton);
        addMoreButton.setOnClickListener(v -> onAddButtonClick());
    }

    public void onEditButtonClick(Ingredient ingredient) {
        InputDialog2 inputDialog2 = new InputDialog2(this.getContext(),
                ingredient.getName(),
                ingredient.getAmount(),
                "Ingredient details");
        inputDialog2.setOnEnterListener((input, input2) -> {
            ingredient.setName(input);
            ingredient.setAmount(input2);
            adapter.notifyDataSetChanged();

            var sharedPreferences = this.getContext().getSharedPreferences("edit_recipe", Context.MODE_PRIVATE);
            sharedPreferences.edit().putString("changed", "changed").apply();
        });

        InputValidation inputValidation = new InputValidation("Name");
        inputValidation.isRequired = true;

        InputValidation inputValidation2 = new InputValidation("Amount");
        inputValidation2.isRequired = true;

        inputDialog2.setValidation(inputValidation, inputValidation2);

        inputDialog2.show();
    }

    public void onDeleteButtonClick(int position) {
        Ingredient ingredient = ingredients.get(position);
        new AlertDialog.Builder(this.getContext()).setTitle("Delete ingredient")
                .setMessage("Are you sure you want to delete this ingredient?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    ingredients.remove(ingredient);
                    totalIngredient.setText(String.valueOf(ingredients.size()));
                    adapter.notifyDataSetChanged();

                    Snackbar.make(getView(), "Ingredient deleted", Snackbar.LENGTH_LONG)
                            .setAction("Undo", v -> {
                                ingredients.add(position, ingredient);
                                totalIngredient.setText(String.valueOf(ingredients.size()));
                                adapter.notifyDataSetChanged();
                            })
                            .setDuration(5000)
                            .show();

                    var sharedPreferences = this.getContext().getSharedPreferences("edit_recipe", Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString("changed", "changed").apply();
                })
                .setNegativeButton("No", null)
                .show();

    }

    public void onAddButtonClick() {
        Ingredient ingredient = new Ingredient();
        InputDialog2 inputDialog2 = new InputDialog2(this.getContext(),
                "Ingredient details");
        inputDialog2.setOnEnterListener((input, input2) -> {
            ingredient.setName(input);
            ingredient.setAmount(input2);
            ingredients.add(ingredient);
            totalIngredient.setText(String.valueOf(ingredients.size()));
            adapter.notifyDataSetChanged();

            var sharedPreferences = this.getContext().getSharedPreferences("edit_recipe", Context.MODE_PRIVATE);
            sharedPreferences.edit().putString("changed", "changed").apply();
        });

        InputValidation inputValidation = new InputValidation("Name");
        inputValidation.isRequired = true;

        InputValidation inputValidation2 = new InputValidation("Amount");
        inputValidation2.isRequired = true;

        inputDialog2.setValidation(inputValidation, inputValidation2);

        inputDialog2.show();
    }
}
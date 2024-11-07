package com.example.prm392_proj.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.prm392_proj.frament.IngredientListEditableFragment;
import com.example.prm392_proj.frament.InstructionListEditableFragment;
import com.example.prm392_proj.model.Ingredient;
import com.example.prm392_proj.model.Instruction;

import java.util.List;

public class RecipeEditableViewPagerAdapter extends FragmentStateAdapter {
    List<Ingredient> ingredients;
    List<Instruction> instructions;


    public RecipeEditableViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public RecipeEditableViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Ingredient> ingredients, List<Instruction> instructions) {
        super(fragmentActivity);
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new IngredientListEditableFragment(ingredients);
        } else if (position == 1) {
            return new InstructionListEditableFragment(instructions);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

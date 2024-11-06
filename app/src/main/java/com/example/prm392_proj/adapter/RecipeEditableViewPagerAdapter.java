package com.example.prm392_proj.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.prm392_proj.frament.IngredientListEditableFragment;
import com.example.prm392_proj.model.Ingredient;

import java.util.List;

public class RecipeEditableViewPagerAdapter extends FragmentStateAdapter {
    List<Ingredient> ingredients;


    public RecipeEditableViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public RecipeEditableViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Ingredient> ingredients) {
        super(fragmentActivity);
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new IngredientListEditableFragment(ingredients);
        } else if (position == 1) {
//            return new RecipeInstructionsFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

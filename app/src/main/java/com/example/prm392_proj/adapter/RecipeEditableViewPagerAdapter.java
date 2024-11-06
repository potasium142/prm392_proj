package com.example.prm392_proj.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.prm392_proj.frament.IngredientListEditableFragment;

public class RecipeEditableViewPagerAdapter extends FragmentStateAdapter {


    public RecipeEditableViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new IngredientListEditableFragment();
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

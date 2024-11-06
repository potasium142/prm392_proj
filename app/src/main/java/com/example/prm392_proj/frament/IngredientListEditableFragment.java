package com.example.prm392_proj.frament;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prm392_proj.R;
import com.example.prm392_proj.adapter.IngredientListEditableAdapter;
import com.example.prm392_proj.model.Ingredient;

import java.util.List;

public class IngredientListEditableFragment extends Fragment {
    List<Ingredient> ingredients;
    IngredientListEditableAdapter adapter;

    public IngredientListEditableFragment(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingredient_list_editable, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.ingredientsRecyclerView);
        adapter = new IngredientListEditableAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setIngredients(ingredients);
    }

}
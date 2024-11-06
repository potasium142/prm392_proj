package com.example.prm392_proj.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prm392_proj.R;
import com.example.prm392_proj.activity.RecipeIngredient;
import com.example.prm392_proj.activity.TestActivity;
import com.example.prm392_proj.model.Recipe;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class Test extends RecyclerView.Adapter<Test.ViewHolder> {
    private final Context context;
    private final TestActivity activity;
    private List<Recipe> recipes = new ArrayList<>();

    public Test(Context context, TestActivity activity) {
        this.activity = activity;
        this.context = context;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    @Override
    public Test.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_detail, parent, false);
        return new Test.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Test.ViewHolder holder, int position) {
        Recipe current = recipes.get(position);
        holder.productName.setText(current.getDishName());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecipeIngredient.class);
            intent.putExtra("DISH_NAME", current.getDishName());
            intent.putExtra("RECIPE_ID", current.getId());
            intent.putExtra("IMAGE_URL", current.getPicture());
            intent.putExtra("TIME", current.getTime());
            intent.putExtra("USER_ID", current.getUserCreatorId());
            context.startActivity(intent);
        });
        Picasso.get().load(current.getPicture()).into(holder.dishImageView);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        ImageView dishImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.nameFood);
            dishImageView = itemView.findViewById(R.id.imageTestDetail);
        }
    }
}

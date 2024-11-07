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
    private List<Recipe> recipes = new ArrayList<>();

    public Test(Context context, TestActivity activity) {
        this.context = context;
    }

    public void setRecipes(List<Recipe> recipes) {
        if (recipes != null && !recipes.isEmpty()) {
            this.recipes = recipes;
            notifyDataSetChanged();
        } else {
            Log.e("AdapterTest", "Danh sách recipes rỗng hoặc null.");
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe current = recipes.get(position);
        holder.productName.setText(current.getDishName());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecipeIngredient.class);

            // Truyền dữ liệu qua intent với đúng khóa
            intent.putExtra("DISH_NAME", current.getDishName());
            intent.putExtra("RECIPE_ID", current.getId());
            intent.putExtra("IMAGE_URL", current.getPicture());
            intent.putExtra("TIME", current.getTime());
            intent.putExtra("USER_ID", current.getUserCreatorId());

            // Ghi nhật ký để kiểm tra dữ liệu trước khi truyền
            Log.d("TestAdapter", "Dish Name: " + current.getDishName());
            Log.d("TestAdapter", "Recipe ID: " + current.getId());
            Log.d("TestAdapter", "Image URL: " + current.getPicture());
            Log.d("TestAdapter", "Time: " + current.getTime());
            Log.d("TestAdapter", "User ID: " + current.getUserCreatorId());

            context.startActivity(intent);
        });

        holder.productName.setText(current.getDishName());
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

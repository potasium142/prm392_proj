package com.example.prm392_proj.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_proj.R;
import com.example.prm392_proj.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Comment> reviews = new ArrayList<>();

    public void setReviews(List<Comment> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_comment, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Comment review = reviews.get(position);
        if (review == null) {
            return;
        }

        // Thiết lập dữ liệu cho ViewHolder
        holder.tvUserName.setText(review.getProfileName());
        holder.tvReviewText.setText(review.getCommentText());

        // Nếu cần thiết, hãy tải ảnh vào ImageView
        // Ví dụ sử dụng Glide hoặc Picasso để tải ảnh từ URL
        // Glide.with(holder.itemView.getContext()).load(review.getAvatarUrl()).into(holder.idAvatar);

        holder.tvLikeCount.setText(String.valueOf(review.getLikeCount()));
        holder.tvDislikeCount.setText(String.valueOf(review.getDislikeCount()));
    }

    @Override
    public int getItemCount() {
        return reviews != null ? reviews.size() : 0;
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        private final ImageView idAvatar;
        private final TextView tvUserName;
        private final TextView tvReviewText;
        private final TextView tvLikeCount;
        private final TextView tvDislikeCount;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            idAvatar = itemView.findViewById(R.id.idAvatar);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvReviewText = itemView.findViewById(R.id.tvReviewText);
            tvLikeCount = itemView.findViewById(R.id.tvLikeCount);
            tvDislikeCount = itemView.findViewById(R.id.tvDislikeCount);
        }
    }
}

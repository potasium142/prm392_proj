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
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> commentList;

    public void setCommentList(List<Comment> list) {
        this.commentList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        if (comment == null) {
            return;
        }

        // Gán giá trị cho các TextView và ImageView trong ViewHolder
        holder.tvCommentText.setText(comment.getComment());  // Nội dung bình luận
        holder.tvUserName.setText(comment.getUserId());         // Tên người dùng (nếu có)
        // Gán ảnh đại diện nếu có sẵn, giả sử bạn có một trường hoặc phương thức để lấy ảnh đại diện

        // Ví dụ: sử dụng ảnh mặc định cho ảnh đại diện (có thể thay bằng Glide hoặc Picasso cho ảnh từ URL)
        holder.ivAvatar.setImageResource(R.drawable.ic_launcher_foreground);
    }

    @Override
    public int getItemCount() {
        if (commentList != null) {
            return commentList.size();
        }
        return 0;
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCommentText;
        private TextView tvUserName;
        private ImageView ivAvatar;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCommentText = itemView.findViewById(R.id.tvReviewText); // Nội dung bình luận
            tvUserName = itemView.findViewById(R.id.tvUserName);       // Tên người dùng
            ivAvatar = itemView.findViewById(R.id.ivAvatar);           // Ảnh đại diện
        }
    }
}

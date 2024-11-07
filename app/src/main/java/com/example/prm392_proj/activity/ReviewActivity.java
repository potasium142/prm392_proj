package com.example.prm392_proj.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prm392_proj.R;
import com.example.prm392_proj.adapter.CommentAdapter;
import com.example.prm392_proj.database.DatabaseHelper;
import com.example.prm392_proj.model.Comment;
import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {
    private EditText editTextReview;
    private Button buttonSend;
    private RecyclerView rcvComment;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList;
    private int recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ativity_review);  // Đảm bảo tên file XML là activity_review

        init();

        // Lấy recipeId từ Intent
        recipeId = getIntent().getIntExtra("recipeId", -1);

        // Khởi tạo CommentAdapter với danh sách bình luận
        commentList = DatabaseHelper.getInstance(this).commentDao().getCommentsByRecipeId(recipeId);
        commentAdapter = new CommentAdapter();
        rcvComment.setLayoutManager(new LinearLayoutManager(this));
        rcvComment.setAdapter(commentAdapter);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment();
            }
        });
    }

    private void init() {
        editTextReview = findViewById(R.id.comment_input);
        buttonSend = findViewById(R.id.send_button);
        rcvComment = findViewById(R.id.rcv_comment);
    }

    private void addComment() {
        String commentText = editTextReview.getText().toString().trim();
        if (commentText.isEmpty()) {
            Toast.makeText(this, "Please enter a comment", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo Comment mới và lưu vào cơ sở dữ liệu
        Comment comment = new Comment();
        DatabaseHelper.getInstance(this).commentDao().insert(comment);

        // Cập nhật danh sách và thông báo cho Adapter
        commentList = DatabaseHelper.getInstance(this).commentDao().getCommentsByRecipeId(recipeId);
        commentAdapter.setCommentList(commentList);
        commentAdapter.notifyDataSetChanged();

        // Xóa ô nhập liệu sau khi thêm bình luận
        editTextReview.setText("");
        Toast.makeText(this, "Comment added", Toast.LENGTH_SHORT).show();
    }
}

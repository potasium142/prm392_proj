package com.example.prm392_proj.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ativity_review);
        init();
        CommentAdapter CommentAdapter = new CommentAdapter();
        commentList = new ArrayList<>();
        rcvComment.setAdapter((RecyclerView.Adapter) commentList);
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

    int recipeId = getIntent().getIntExtra("recipeId", -1); // Giá trị mặc định là -1 nếu không có

    private void addComment() {
        String commentText = editTextReview.getText().toString().trim();
        Comment comment = new Comment(commentText);
        DatabaseHelper.getInstance(this).commentDao().insert(comment);
        Toast.makeText(this, "Comment added", Toast.LENGTH_SHORT).show();
        editTextReview.setText("");
        commentList = DatabaseHelper.getInstance(this).commentDao().getCommentsByRecipeId(recipeId);
        commentAdapter.setCommentList(commentList);
    }
}

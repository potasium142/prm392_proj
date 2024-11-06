package com.example.prm392_proj.activities;

import android.os.Bundle;
<<<<<<< Updated upstream:app/src/main/java/com/example/prm392_proj/activities/RecipeProcedure.java

import androidx.appcompat.app.AppCompatActivity;
=======
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
>>>>>>> Stashed changes:app/src/main/java/com/example/prm392_proj/activity/ReviewActivity.java

import com.example.prm392_proj.R;
import com.example.prm392_proj.adapter.CommentAdapter;
import com.example.prm392_proj.database.DatabaseHelper;
import com.example.prm392_proj.model.Comment;

import java.util.ArrayList;
import java.util.List;

<<<<<<< Updated upstream:app/src/main/java/com/example/prm392_proj/activities/RecipeProcedure.java
public class RecipeProcedure extends AppCompatActivity {
=======
public class ReviewActivity extends AppCompatActivity {
    private EditText editTextReview;
    private Button buttonSend;
    private RecyclerView rcvComment;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList;
>>>>>>> Stashed changes:app/src/main/java/com/example/prm392_proj/activity/ReviewActivity.java

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< Updated upstream:app/src/main/java/com/example/prm392_proj/activities/RecipeProcedure.java
        setContentView(R.layout.activity_recipe_procedure);
=======
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
>>>>>>> Stashed changes:app/src/main/java/com/example/prm392_proj/activity/ReviewActivity.java
    }

    private void init() {
        editTextReview = findViewById(R.id.comment_input);
        buttonSend = findViewById(R.id.send_button);
        rcvComment = findViewById(R.id.rcvComment);

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
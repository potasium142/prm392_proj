package com.example.prm392_proj.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Entity(tableName = "Comment")
public class Comment implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int commentId; // ID cho từng comment
    private int userId; // ID của người dùng thực hiện comment
    private String profileName; // Tên hiển thị của người dùng
    private String avatar; // Link ảnh đại diện của người dùng
    private String commentText; // Nội dung của comment
    private int recipeId; // ID của recipe mà comment thuộc về
    private int likeCount; // Số lượt like cho comment
    private int dislikeCount; // Số lượt dislike cho comment

    public Comment(int commentId, int userId, String profileName, String avatar, int likeCount, int recipeId, String commentText, int dislikeCount) {
        this.commentId = commentId;
        this.userId = userId;
        this.profileName = profileName;
        this.avatar = avatar;
        this.likeCount = likeCount;
        this.recipeId = recipeId;
        this.commentText = commentText;
        this.dislikeCount = dislikeCount;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }
}

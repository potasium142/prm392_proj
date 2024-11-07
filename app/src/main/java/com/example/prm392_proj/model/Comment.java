package com.example.prm392_proj.model;


import androidx.room.Entity;

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
@Entity(primaryKeys = {"userId", "recipeId"}, tableName = "comment")
public class Comment {
    private int userId;
    private int recipeId;
    private String comment;
    private int like;
    private int dislike;
}

package com.example.prm392_proj.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
@Entity(tableName = "comment")
public class Comment {
    @PrimaryKey(autoGenerate = true)
    private int commentid;
    private int userId;
    private int recipeId;
    private String comment;
    private int like;
    private int dislike;
}

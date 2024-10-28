package com.example.prm392_proj.model;

import androidx.room.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Entity(primaryKeys = {"userId", "recipeId"}, tableName = "bookmark")
public class Bookmark {
    private int userId;
    private int recipeId;
}



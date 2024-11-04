package com.example.prm392_proj.model;

import androidx.room.Entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(primaryKeys = {"userId", "recipeId"}, tableName = "rate")
public class Rate implements Serializable {
    private int userId;
    private int recipeId;
    private int rate;
    private String comment;

    public Rate(int userId, int recipeId, int rate) {
        this.userId = userId;
        this.recipeId = recipeId;
        this.rate = rate;
    }

}

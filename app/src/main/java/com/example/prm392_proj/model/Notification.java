package com.example.prm392_proj.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(tableName = "notification")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Notification {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private String title;
    private String content;
    private Date date;
    private boolean read;
    private int recipeId;

//    public Notification(int id, int userId, String title, String content, Date date, boolean read, int recipeId) {
//        this.id = id;
//        this.userId = userId;
//        this.title = title;
//        this.content = content;
//        this.date = date;
//        this.read = read;
//        this.recipeId = recipeId;
//    }
}

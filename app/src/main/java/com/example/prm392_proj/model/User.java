package com.example.prm392_proj.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(tableName = "user", indices = {@Index(value = {"username"}, unique = true)})
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String email;
    private String password;
    private String profileName;
    private String avatar;

    public User(String username, String email, String password, String profileName, String avatar) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.profileName = profileName;
        this.avatar = avatar;
    }

}

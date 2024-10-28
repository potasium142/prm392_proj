package com.example.prm392_proj.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(tableName = "recipe")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Recipe implements Parcelable {
    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userCreatorId;
    private String dishName;
    private String picture;
    private String description;
    private Date creationDate;

    public Recipe(int userCreatorId, String dishName, String picture, String description) {
        this.userCreatorId = userCreatorId;
        this.dishName = dishName;
        this.picture = picture;
        this.description = description;
        this.creationDate = new Date();
    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        userCreatorId = in.readInt();
        dishName = in.readString();
        picture = in.readString();
        description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.userCreatorId);
        dest.writeString(this.dishName);
        dest.writeString(this.picture);
        dest.writeString(this.description);
    }
}

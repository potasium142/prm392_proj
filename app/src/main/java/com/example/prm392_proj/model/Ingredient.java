package com.example.prm392_proj.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
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
@Entity(tableName = "ingredient")
public class Ingredient implements Parcelable {
    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int recipeId;
    private String name;
    private String amount;

    public Ingredient(int recipeId, String name, String amount) {
        this.recipeId = recipeId;
        this.name = name;
        this.amount = amount;
    }

    protected Ingredient(Parcel in) {
        id = in.readInt();
        recipeId = in.readInt();
        name = in.readString();
        amount = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.recipeId);
        dest.writeString(this.name);
        dest.writeString(this.amount);
    }
}

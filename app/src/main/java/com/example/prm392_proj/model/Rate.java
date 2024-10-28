package com.example.prm392_proj.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;

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
public class Rate implements Parcelable {
    private int userId;
    private int recipeId;
    private int rate;
    private String comment;

    public Rate(int userId, int recipeId, int rate) {
        this.userId = userId;
        this.recipeId = recipeId;
        this.rate = rate;
    }

    protected Rate(Parcel in) {
        userId = in.readInt();
        recipeId = in.readInt();
        rate = in.readInt();
        comment = in.readString();
    }

    public static final Creator<Rate> CREATOR = new Creator<Rate>() {
        @Override
        public Rate createFromParcel(Parcel in) {
            return new Rate(in);
        }

        @Override
        public Rate[] newArray(int size) {
            return new Rate[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeInt(recipeId);
        dest.writeInt(rate);
        dest.writeString(comment);
    }
}

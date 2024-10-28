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

@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "instruction")
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Instruction implements Parcelable {
    public static final Creator<Instruction> CREATOR = new Creator<Instruction>() {
        @Override
        public Instruction createFromParcel(Parcel in) {
            return new Instruction(in);
        }

        @Override
        public Instruction[] newArray(int size) {
            return new Instruction[size];
        }
    };
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int recipeId;
    private int index;
    private String description;


    public Instruction(int recipeId, int index, String description) {
        this.recipeId = recipeId;
        this.index = index;
        this.description = description;
    }

    protected Instruction(Parcel in) {
        id = in.readInt();
        recipeId = in.readInt();
        index = in.readInt();
        description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.recipeId);
        dest.writeInt(this.index);
        dest.writeString(this.description);
    }
}

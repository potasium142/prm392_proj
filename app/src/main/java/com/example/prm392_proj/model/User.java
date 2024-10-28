package com.example.prm392_proj.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

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
public class User implements Parcelable {
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
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

    protected User(Parcel in) {
        id = in.readInt();
        username = in.readString();
        email = in.readString();
        password = in.readString();
        profileName = in.readString();
        avatar = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.username);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeString(this.profileName);
    }
}

package entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username;
    private String password;
    private String profileName;

    private byte[] avatar;
}

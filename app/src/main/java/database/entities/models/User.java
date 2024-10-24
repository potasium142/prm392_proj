package database.entities.models;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(tableName = "user", indices = {@Index(value = {"username"}, unique = true)})
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @PrimaryKey(autoGenerate = true)
    private int userId;

    private String username;
    private String email;
    private String password;
    private String profileName;

    private String avatar;
}

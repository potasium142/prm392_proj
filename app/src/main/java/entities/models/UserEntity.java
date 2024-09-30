package entities.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @PrimaryKey(autoGenerate = true)
    private int userId;

    private String username;
    private String password;
    private String profileName;

    private byte[] avatar;
}

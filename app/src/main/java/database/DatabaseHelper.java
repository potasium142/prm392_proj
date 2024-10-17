package database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import database.dao.UserDAO;
import database.entities.models.Bookmark;
import database.entities.models.IngredientList;
import database.entities.models.InstructionList;
import database.entities.models.Rate;
import database.entities.models.RecipeEntity;
import database.entities.models.UserEntity;

@Database(entities = {
        Bookmark.class,
        IngredientList.class,
        InstructionList.class,
        Rate.class,
        RecipeEntity.class,
        UserEntity.class
}, version = 1)
public abstract class DatabaseHelper extends RoomDatabase {
    private static final String DB_NAME = "PRM392";

    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            DatabaseHelper.class,
                            DB_NAME
                    )
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract UserDAO userDAO();
}

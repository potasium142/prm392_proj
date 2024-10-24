package database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import database.dao.RecipeDao;
import database.dao.UserDAO;
import database.entities.models.Bookmark;
import database.entities.models.Ingredient;
import database.entities.models.Instruction;
import database.entities.models.Rate;
import database.entities.models.Recipe;
import database.entities.models.User;

@Database(entities = {Bookmark.class, Ingredient.class, Instruction.class, Rate.class, Recipe.class, User.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class DatabaseHelper extends RoomDatabase {
    private static final String DB_NAME = "PRM392";

    private static volatile DatabaseHelper INSTANCE;

    public static DatabaseHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseHelper.class, DB_NAME).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                RecipeDao recipeDao = INSTANCE.recipeDao();
            });
        }
    };

    public abstract UserDAO userDAO();
    public abstract RecipeDao recipeDao();
}

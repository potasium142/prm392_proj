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

import database.dao.IngredientDao;
import database.dao.RecipeDao;
import database.dao.UserDao;
import database.entities.models.Bookmark;
import database.entities.models.Ingredient;
import database.entities.models.Instruction;
import database.entities.models.Rate;
import database.entities.models.Recipe;
import database.entities.models.User;

@Database(entities = {Bookmark.class, Ingredient.class, Instruction.class, Rate.class, Recipe.class, User.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class DatabaseHelper extends RoomDatabase {
    private static final String DB_NAME = "PRM392";
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile DatabaseHelper INSTANCE;
    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                RecipeDao recipeDao = INSTANCE.recipeDao();
                IngredientDao ingredientDao = INSTANCE.ingredientDao();
                UserDao userDao = INSTANCE.userDAO();

                User user = User.builder()
                        .username("admin")
                        .password("admin")
                        .profileName("admin")
                        .build();
                userDao.insert(user);
                user = userDao.getUser(user.getUsername());

                Recipe recipe = Recipe.builder()
                        .userCreatorId(user.getId())
                        .dishName("Mediterranean Baked Cod with Lemon")
                        .picture("dish_1.webp")
                        .description("This Mediterranean baked cod with lemon, deliciously seasoned with fresh Mediterranean herbs, garlic, and lemon, is ready in 25 minutes, start to finish. Serve with your favorite potato dish, and a green vegetable or salad, and your meal is done.")
                        .build();
                recipeDao.insert(recipe);

                Ingredient ingredient = Ingredient.builder()
                        .recipeId(recipe.getId())
                        .name("cod filets")
                        .amount("4 (6 ounce)")
                        .build();
                ingredientDao.insert(ingredient);

                ingredient = Ingredient.builder()
                        .recipeId(recipe.getId())
                        .name("unsalted butter, softened")
                        .amount("3 tablespoons")
                        .build();
                ingredientDao.insert(ingredient);

                ingredient = Ingredient.builder()
                        .recipeId(recipe.getId())
                        .name("finely minced fresh garlic")
                        .amount("1 tablespoon")
                        .build();
                ingredientDao.insert(ingredient);

                ingredient = Ingredient.builder()
                        .recipeId(recipe.getId())
                        .name("minced fresh oregano")
                        .amount("2 teaspoons")
                        .build();
                ingredientDao.insert(ingredient);
            });
        }
    };

    public static DatabaseHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseHelper.class, DB_NAME)
                    .allowMainThreadQueries()
                    .addCallback(sRoomDatabaseCallback)
                    .build();
        }
        return INSTANCE;
    }

    public abstract UserDao userDAO();

    public abstract RecipeDao recipeDao();

    public abstract IngredientDao ingredientDao();
}

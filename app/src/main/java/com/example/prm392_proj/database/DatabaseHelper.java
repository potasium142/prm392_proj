package com.example.prm392_proj.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.prm392_proj.dao.CommentDao;
import com.example.prm392_proj.dao.IngredientDao;
import com.example.prm392_proj.dao.InstructionDao;
import com.example.prm392_proj.dao.RecipeDao;
import com.example.prm392_proj.dao.UserDao;
import com.example.prm392_proj.model.Bookmark;
import com.example.prm392_proj.model.Comment;
import com.example.prm392_proj.model.Ingredient;
import com.example.prm392_proj.model.Instruction;
import com.example.prm392_proj.model.Rate;
import com.example.prm392_proj.model.Recipe;
import com.example.prm392_proj.model.User;


@Database(entities = {Bookmark.class, Ingredient.class, Instruction.class, Rate.class, Recipe.class, User.class, Comment.class}, version = 12, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class DatabaseHelper extends RoomDatabase {
    private static final String DB_NAME = "PRM392_final_project";
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(
            NUMBER_OF_THREADS);
    private static volatile DatabaseHelper INSTANCE;
    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                RecipeDao recipeDao = INSTANCE.recipeDao();
                IngredientDao ingredientDao = INSTANCE.ingredientDao();
                InstructionDao instructionDao = INSTANCE.instructionDao();
                UserDao userDao = INSTANCE.userDAO();
                CommentDao commentDao = INSTANCE.commentDao();

                User user = User.builder()
                        .username("admin")
                        .password("admin")
                        .profileName("admin")
                        .build();
                userDao.insert(user);
                user = userDao.getUser(user.getUsername());

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date tempDate;
                try {
                    tempDate = dateFormat.parse("01-01-2024");
                } catch (ParseException e) {
                    e.printStackTrace();
                    tempDate = new Date(); // fallback to current date if parsing fails
                }



                Recipe recipe = Recipe.builder()
                        .userCreatorId(user.getId())
                        .totalTime(10)
                        .dishName("Mediterranean Baked Cod with Lemon")
                        .picture("https://www.allrecipes.com/thmb/1blq_he4MHCz2acTU7arELCnGrI=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/8576313_Mediterranean-Baked-Cod-with-Lemon_Brenda-Venable_4x3-b34ff9cd504b4aca9ba74d5ca8ba0c4d.jpg")
                        .description("This Mediterranean baked cod with lemon, deliciously seasoned with fresh Mediterranean herbs, garlic, and lemon, is ready in 25 minutes, start to finish. Serve with your favorite potato dish, and a green vegetable or salad, and your meal is done.")
                        .creationDate(tempDate)

                        .build();
                Long recipeId = recipeDao.insert(recipe);
                recipe.setId(Integer.parseInt(String.valueOf(recipeId)));

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

                Instruction instruction = Instruction.builder()
                        .recipeId(recipe.getId())
                        .index(1)
                        .description("Preheat the oven to 400 degrees F (200 degrees C).")
                        .build();
                instructionDao.insert(instruction);

                instruction = Instruction.builder()
                        .recipeId(recipe.getId())
                        .index(2)
                        .description(
                                "Place softened butter, minced garlic, parsley, oregano, and thyme or rosemary on a cutting board. Using a sharp knife, cut herbs and garlic into each other and the butter, cutting and mixing as you go. Add pink salt, black pepper, and paprika, and mix until well blended.")
                        .build();
                instructionDao.insert(instruction);

                instruction = Instruction.builder()
                        .recipeId(recipe.getId())
                        .index(3)
                        .description(
                                "Pat cod filets dry. In a 12x18-inch casserole or baking pan, place each filet on top of 2 lemon slices. Evenly divide herb butter mixture among the filets; use a fork or offset spatula to spread herb butter over filets. Top each filet with 2 remaining lemon slices.")
                        .build();
                instructionDao.insert(instruction);

                instruction = Instruction.builder()
                        .recipeId(recipe.getId())
                        .index(4)
                        .description(
                                "Bake in the preheated oven until cod flakes easily with a fork, 13 to 15 minutes. See note.")
                        .build();
                instructionDao.insert(instruction);

                instruction = Instruction.builder()
                        .recipeId(recipe.getId())
                        .index(5)
                        .description(
                                "To serve, drizzle each filet with extra virgin olive oil, and garnish with fresh parsley, if desired.")
                        .build();
                instructionDao.insert(instruction);

//                second data
                User user2 = User.builder()
                        .username("admin2")
                        .password("admin2")
                        .profileName("admin2")
                        .build();
                userDao.insert(user2);
                user2 = userDao.getUser(user2.getUsername());

//                try {
//                    tempDate = dateFormat.parse("2024-01-01");
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                    tempDate = new Date(); // fallback to current date if parsing fails
//                }



                Recipe recipe2 = Recipe.builder()
                        .userCreatorId(user2.getId())
                        .dishName("Pro Mediterranean Baked Cod with Lemon")
                        .picture("https://www.allrecipes.com/thmb/1blq_he4MHCz2acTU7arELCnGrI=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/8576313_Mediterranean-Baked-Cod-with-Lemon_Brenda-Venable_4x3-b34ff9cd504b4aca9ba74d5ca8ba0c4d.jpg")
                        .description("This Mediterranean baked cod with lemon, deliciously seasoned with fresh Mediterranean herbs, garlic, and lemon, is ready in 25 minutes, start to finish. Serve with your favorite potato dish, and a green vegetable or salad, and your meal is done.")
                        .creationDate(new Date())
                        .build();
                recipeId = recipeDao.insert(recipe2);
                recipe2.setId(Integer.parseInt(String.valueOf(recipeId)));

                Ingredient ingredient2 = Ingredient.builder()
                        .recipeId(recipe2.getId())
                        .name("cod filets")
                        .amount("4 (6 ounce)")
                        .build();
                ingredientDao.insert(ingredient2);

                ingredient2 = Ingredient.builder()
                        .recipeId(recipe2.getId())
                        .name("unsalted butter, softened")
                        .amount("3 tablespoons")
                        .build();
                ingredientDao.insert(ingredient2);

                ingredient2 = Ingredient.builder()
                        .recipeId(recipe2.getId())
                        .name("finely minced fresh garlic")
                        .amount("1 tablespoon")
                        .build();
                ingredientDao.insert(ingredient2);

                ingredient2 = Ingredient.builder()
                        .recipeId(recipe2.getId())
                        .name("minced fresh oregano")
                        .amount("2 teaspoons")
                        .build();
                ingredientDao.insert(ingredient2);

                Instruction instruction2 = Instruction.builder()
                        .recipeId(recipe2.getId())
                        .index(1)
                        .description("Preheat the oven to 400 degrees F (200 degrees C).")
                        .build();
                instructionDao.insert(instruction2);

                instruction2 = Instruction.builder()
                        .recipeId(recipe2.getId())
                        .index(2)
                        .description("Place softened butter, minced garlic, parsley, oregano, and thyme or rosemary on a cutting board. Using a sharp knife, cut herbs and garlic into each other and the butter, cutting and mixing as you go. Add pink salt, black pepper, and paprika, and mix until well blended.")
                        .build();
                instructionDao.insert(instruction2);

                instruction2 = Instruction.builder()
                        .recipeId(recipe2.getId())
                        .index(3)
                        .description("Pat cod filets dry. In a 12x18-inch casserole or baking pan, place each filet on top of 2 lemon slices. Evenly divide herb butter mixture among the filets; use a fork or offset spatula to spread herb butter over filets. Top each filet with 2 remaining lemon slices.")
                        .build();
                instructionDao.insert(instruction2);

                instruction2 = Instruction.builder()
                        .recipeId(recipe2.getId())
                        .index(4)
                        .description("Bake in the preheated oven until cod flakes easily with a fork, 13 to 15 minutes. See note.")
                        .build();
                instructionDao.insert(instruction2);

                instruction2 = Instruction.builder()
                        .recipeId(recipe2.getId())
                        .index(5)
                        .description("To serve, drizzle each filet with extra virgin olive oil, and garnish with fresh parsley, if desired.")
                        .build();
                instructionDao.insert(instruction2);


            });
        }
    };


    public static DatabaseHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    DatabaseHelper.class,
                    DB_NAME).allowMainThreadQueries().addCallback(sRoomDatabaseCallback).build();
        }
        return INSTANCE;
    }

    public abstract UserDao userDAO();

    public abstract RecipeDao recipeDao();

    public abstract IngredientDao ingredientDao();

    public abstract InstructionDao instructionDao();

    public abstract CommentDao commentDao();

}

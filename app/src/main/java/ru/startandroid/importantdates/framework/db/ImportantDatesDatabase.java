package ru.startandroid.importantdates.framework.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {EventEntity.class, CategoryEntity.class},
        exportSchema = false,
        version = 1
)
public abstract class ImportantDatesDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "importantdates.db";
    private static final Object LOCK = new Object();
    private static ImportantDatesDatabase instance;

    public static void create(Context context) {
        synchronized (LOCK) {
            instance = Room.databaseBuilder(context,
                    ImportantDatesDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
    }

    public static ImportantDatesDatabase getInstance(Context context) {
        if (instance == null) {
            create(context);
        }
        return instance;
    }

    public abstract EventDao eventDao();

    public abstract CategoryDao categoryDao();
}

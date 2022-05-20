package ru.startandroid.importantdates.framework.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {EventEntity.class, CategoryEntity.class},
        version = 1,
        exportSchema = false
)
abstract class ImportantDatesDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "important-dates.db";
    private static ImportantDatesDatabase instance;

    private static ImportantDatesDatabase create(Context context) {
        return Room.databaseBuilder(context, ImportantDatesDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    public static ImportantDatesDatabase getInstance(Context context) {
        ImportantDatesDatabase instance = ImportantDatesDatabase.instance;
        if (instance == null) {
            instance = create(context);
            ImportantDatesDatabase.instance = instance;
        }
        return instance;
    }

    public abstract EventDao eventDao();

    public abstract CategoryDao categoryDao();
}

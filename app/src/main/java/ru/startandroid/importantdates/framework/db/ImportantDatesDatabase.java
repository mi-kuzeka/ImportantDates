package ru.startandroid.importantdates.framework.db;

import android.content.Context;
import android.os.Parcelable;

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

    public static ImportantDatesDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        ImportantDatesDatabase.class, DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return instance;
    }

    public abstract EventDao eventDao();

    public abstract CategoryDao categoryDao();
}

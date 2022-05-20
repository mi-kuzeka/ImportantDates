package ru.startandroid.importantdates.framework.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addEvent(EventEntity event);

    @Query("SELECT * FROM events")
    public List<EventEntity> getAllEvents();

    @Query("SELECT * FROM events WHERE month = :month")
    public List<EventEntity> getEventsByMonth(int month);

    @Query("SELECT * FROM events WHERE id = :id")
    public EventEntity getEventById(int id);

    @Update
    public void updateEvent(EventEntity event);

    @Delete
    public void deleteEvent(EventEntity event);
}

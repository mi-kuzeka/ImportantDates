package ru.startandroid.importantdates.framework.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "events")
public class EventEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "month")
    public int month;
    @ColumnInfo(name = "day")
    public int day;
    @ColumnInfo(name = "year")
    public int year;
    @ColumnInfo(name = "notes")
    public String notes;
    @ColumnInfo(name = "category_id")
    public int category_id;
    @ColumnInfo(name = "image")
    public byte[] image;

    /**
     * Create new {@link EventEntity} object
     *
     * @param id         event ID
     * @param name       event name
     * @param month      month of event occurrence
     * @param day        day of event occurrence
     * @param year       year of event occurrence
     * @param category_id ID of event type
     * @param notes      custom notes about event
     * @param image      event image
     */
    public EventEntity(int id, String name, int month, int day, int year,
                       String notes, int category_id, byte[] image) {
        this.id = id;
        this.name = name;
        this.month = month;
        this.day = day;
        this.year = year;
        this.notes = notes;
        this.category_id = category_id;
        this.image = image;
    }

    /**
     * Create new {@link EventEntity} object
     *
     * @param name       event name
     * @param month      month of event occurrence
     * @param day        day of event occurrence
     * @param year       year of event occurrence
     * @param category_id ID of event type
     * @param notes      custom notes about event
     * @param image      event image
     */
    @Ignore
    public EventEntity(String name, int month, int day, int year,
                       String notes, int category_id, byte[] image) {
        this.name = name;
        this.month = month;
        this.day = day;
        this.year = year;
        this.notes = notes;
        this.category_id = category_id;
        this.image = image;
    }
}

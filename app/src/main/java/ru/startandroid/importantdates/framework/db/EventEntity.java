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
    @ColumnInfo(name = "note")
    public String note;
    @ColumnInfo(name = "category_id")
    public int category_id;
    @ColumnInfo(name = "image")
    public byte[] image;

    public EventEntity(int id, String name, int month, int day, int year,
                       String note, int categoryId, byte[] image) {
        this.id = id;
        this.name = name;
        this.month = month;
        this.day = day;
        this.year = year;
        this.note = note;
        this.category_id = categoryId;
        this.image = image;
    }

    @Ignore
    public EventEntity(String name, int month, int day, int year,
                       String note, int categoryId, byte[] image) {
        this.name = name;
        this.month = month;
        this.day = day;
        this.year = year;
        this.note = note;
        this.category_id = categoryId;
        this.image = image;
    }
}

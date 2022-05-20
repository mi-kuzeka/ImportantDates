package ru.startandroid.importantdates.framework.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "events")
public class EventEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "name")
    public int name;
    @ColumnInfo(name = "day")
    public int day;
    @ColumnInfo(name = "month")
    public int month;
    @ColumnInfo(name = "year")
    public int year;
    @ColumnInfo(name = "note")
    public String note;
    @ColumnInfo(name = "category_id")
    public int category_id;
    @ColumnInfo(name = "image")
    public byte[] image;
}

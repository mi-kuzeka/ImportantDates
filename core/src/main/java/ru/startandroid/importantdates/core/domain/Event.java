package ru.startandroid.importantdates.core.domain;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class Event {
    private final int id;
    private final String name;
    private final EventDate date;
    private final Category category;
    private final String notes;
    private final Bitmap image;

    public Event(int id, String name, @NonNull EventDate date,
                 @NonNull Category category, String notes,
                 Bitmap image) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.category = category;
        this.notes = notes;
        this.image = image;
    }

    public Event(int id, String name, @NonNull EventDate date,
                 @NonNull Category category, String notes) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.category = category;
        this.notes = notes;
        this.image = null;
    }

    /**
     * Get event ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * Get name of the Event
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get date of the Event
     */
    public EventDate getDate() {
        return this.date;
    }

    /**
     * Get day number of the Event
     */
    public int getDay() {
        return this.date.getDay();
    }

    /**
     * Get month number of the Event
     */
    public int getMonth() {
        return this.date.getMonth();
    }

    /**
     * Get year of the Event
     */
    public int getYear() {
        return this.date.getYear();
    }

    /**
     * Get category of the Event
     */
    public Category getCategory() {
        return this.category;
    }

    /**
     * Get custom notes about the Event
     */
    public String getNotes() {
        return this.notes;
    }

    /**
     * Get the {@link Bitmap} data of the event image
     */
    public Bitmap getBitmapImage() {
        return this.image;
    }

    /**
     * Get age of the Event
     */
    public int getAge() {
        return EventAgeHelper.getAge(date.getYear());
    }

    /**
     * Get age of the Event with current year value
     */
    public int getAge(int currentYear) {
        return EventAgeHelper.getAge(date.getYear(), currentYear);
    }

}

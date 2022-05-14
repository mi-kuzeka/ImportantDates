package ru.startandroid.importantdates.entity;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Event {
    private final String logTag = Event.class.getSimpleName();

    //If it is impossible to calculate age of the event, set the value -1
    private final int noDataAboutAge = -1;

    private final long id;
    private final String name;
    private final EventDate date;
    private final EventCategory category;
    private final String notes;

    public Event(long id, String name, EventDate date, EventCategory category, String notes) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.category = category;
        this.notes = notes;
    }

    /**
     * Get event ID
     */
    protected long getId() {
        return this.id;
    }

    /**
     * Get name of the Event
     */
    protected String getName() {
        return this.name;
    }

    /**
     * Get date of the Event
     */
    protected EventDate getDate() {
        return this.date;
    }

    /**
     * Get day number of the Event
     */
    protected int getDay() {
        return this.date.getDay();
    }

    /**
     * Get month number of the Event
     */
    protected int getMonth() {
        return this.date.getMonth();
    }

    /**
     * Get category of the Event
     */
    protected EventCategory getCategory() {
        return this.category;
    }

    /**
     * Get custom notes about the Event
     */
    protected String getNotes() {
        return this.notes;
    }

    /**
     * Check whether or not it possible to calculate age of the event.
     * It can be impossible if the user didn't type the year of the event.
     */
    protected boolean canCalculateAge() {
        return date.hasYear();
    }

    /**
     * Get age of the Event
     */
    protected int getAge(int currentYear) {
        return calculateAge(currentYear);
    }

    /**
     * Get age of the Event
     */
    protected int getAge() {
        return calculateAge(getCurrentYear());
    }

    /**
     * Calculate age for event
     *
     * @param currentYear current year
     * @return period between two dates
     */
    private int calculateAge(int currentYear) {
        if (this.canCalculateAge()) {
            return currentYear - this.date.getYear();
        }
        return this.noDataAboutAge;
    }

    /**
     * Get current year as number
     */
    private int getCurrentYear() {
        Date today = new Date();
        SimpleDateFormat yearFormat =
                new SimpleDateFormat("yyyy", Locale.getDefault());
        try {
            String currentYear = yearFormat.format(today);
            return Integer.parseInt(currentYear);
        } catch (Exception exception) {
            Log.e(logTag, "Error with parsing year of the event with name" + this.name);
        }
        return this.noDataAboutAge;
    }
}

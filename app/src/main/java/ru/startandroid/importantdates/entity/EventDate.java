package ru.startandroid.importantdates.entity;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EventDate {
    private final String logTag = EventDate.class.getSimpleName();

    //If it is impossible to calculate age of the event, set the value -1
    private final int noDataAboutAge = -1;

    private final int month;
    private final int day;
    private final int year;

    /**
     * Create date object by month and day of the event
     */
    public EventDate(int month, int day) {
        this.month = month;
        this.day = day;
        this.year = -1;
    }

    /**
     * Create date object by month, day and year of the event
     */
    public EventDate(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    /**
     * Get month of the Event
     */
    public int getMonth() {
        return this.month;
    }

    /**
     * Get day of the Event
     */
    public int getDay() {
        return this.day;
    }

    /**
     * Get year of the event
     * @return -1 if there's no year in the date for this event
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Check if event has the year in date
     */
    public boolean hasYear() {
        return this.year != -1;
    }

    /**
     * Calculate age for event
     *
     * @return period between two dates
     */
    public int getAge() {
        int currentYear = getCurrentYear();
        return getAge(currentYear);
    }

    /**
     * Calculate age for event with current year value
     *
     * @return period between two dates
     */
    public int getAge(int currentYear) {
        if (this.canCalculateAge()) {
            if (currentYear == noDataAboutAge) return currentYear;
            return currentYear - this.getYear();
        }
        return this.noDataAboutAge;
    }

    /**
     * Check whether or not it possible to calculate age of the event.
     * It can be impossible if the user didn't type the year of the event.
     */
    private boolean canCalculateAge() {
        return this.hasYear();
    }

    /**
     * Get current year as number
     */
    private int getCurrentYear() {
        // Date of today
        Date today = new Date();
        // DateFormat for year
        SimpleDateFormat yearFormat =
                new SimpleDateFormat("yyyy", Locale.getDefault());
        try {
            // Try to parse year from date string
            String currentYear = yearFormat.format(today);
            return Integer.parseInt(currentYear);
        } catch (Exception exception) {
            Log.e(logTag, "Error with parsing year of the event.");
        }
        return this.noDataAboutAge;
    }
}

package ru.startandroid.importantdates.entity;

public class EventDate {
    private final int day;
    private final int month;
    private final int year;

    /**
     * Create date object by date and month of the event
     */
    public EventDate(int day, int month) {
        this.day = day;
        this.month = month;
        this.year = -1;
    }

    /**
     * Create date object by date, month and year of the event
     */
    public EventDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * Get day of the Event
     */
    protected int getDay() {
        return this.day;
    }

    /**
     * Get month of the Event
     */
    protected int getMonth() {
        return this.month;
    }

    /**
     * Get year of the event
     * @return -1 if there's no year in the date for this event
     */
    protected int getYear() {
        return this.year;
    }

    /**
     * Check if event has the year in date
     */
    protected boolean hasYear() {
        return this.year != -1;
    }
}

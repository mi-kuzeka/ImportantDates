package ru.startandroid.importantdates.core.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EventDate implements Parcelable {
    private final int emptyYear = -1;

    private final int month;
    private final int day;
    private final int year;

    /**
     * Create date object by month and day of the event
     */
    public EventDate(int month, int day) {
        this.month = month;
        this.day = day;
        this.year = this.emptyYear;
    }

    /**
     * Create date object by month, day and year of the event
     */
    public EventDate(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    public EventDate(Parcel source) {
        month = source.readInt();
        day = source.readInt();
        year = source.readInt();
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
     *
     * @return -1 if there's no year in the date for this event
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Check if event has the year in date
     */
    public boolean hasYear() {
        return this.year != this.emptyYear;
    }

    /**
     * Get string of the event date formatted by date pattern
     */
    public String getFormattedDate(String datePattern) {
        SimpleDateFormat dateFormat =
                new SimpleDateFormat(datePattern, Locale.getDefault());
        return dateFormat.format(getEventDate(emptyYear));
    }

    /**
     * Get date of the event
     */
    public Date getEventDate(int year) {
        final int emptyValue = 0;
        int eventYear;
        if (year == this.emptyYear) {
            eventYear = this.hasYear() ? this.getYear() : emptyValue;
        } else {
            eventYear = year;
        }

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, eventYear);
        cal.set(Calendar.MONTH, this.month - 1);
        cal.set(Calendar.DAY_OF_MONTH, this.day);
        cal.set(Calendar.HOUR_OF_DAY, emptyValue);
        cal.set(Calendar.MINUTE, emptyValue);
        cal.set(Calendar.SECOND, emptyValue);
        cal.set(Calendar.MILLISECOND, emptyValue);
        return cal.getTime();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(month);
        parcel.writeInt(day);
        parcel.writeInt(year);
    }

    public static final Creator<EventDate> CREATOR = new Creator<EventDate>() {
        @Override
        public EventDate[] newArray(int size) {
            return new EventDate[size];
        }

        @Override
        public EventDate createFromParcel(Parcel source) {
            return new EventDate(source);
        }
    };
}

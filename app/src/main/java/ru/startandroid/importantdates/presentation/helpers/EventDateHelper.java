package ru.startandroid.importantdates.presentation.helpers;

import android.content.Context;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import ru.startandroid.importantdates.R;
import ru.startandroid.importantdates.core.domain.EventDate;

public class EventDateHelper {
    private static final String LOG_TAG = EventDateHelper.class.getSimpleName();

    /**
     * Convert date string to {@link EventDate}
     */
    public static EventDate getEventDateObject(Context context, String dateText) {
        DateTime date;

        String fullDateFormatPattern =
                context.getResources().getString(R.string.full_date_format);
        // DateFormat for full date
        DateTimeFormatter fullDateFormat = DateTimeFormat.forPattern(fullDateFormatPattern);

        try {
            date = new DateTime(fullDateFormat.parseDateTime(dateText));
            // If date is successfully parsed
            // and user input text length equals pattern length,
            // then date is valid
            if (dateText.length() == fullDateFormatPattern.length())
                return new EventDate(date.getMonthOfYear(), date.getDayOfMonth(), date.getYear());
        } catch (Exception e) {
            Log.v(LOG_TAG, "Can't parse full date");
        }

        String shortDateFormatPattern =
                context.getResources().getString(R.string.date_format_without_year);
        // DateFormat for date without year
        DateTimeFormatter shortDateFormat = DateTimeFormat.forPattern(shortDateFormatPattern);

        try {
            date = new DateTime(shortDateFormat.parseDateTime(dateText));
            // If date is successfully parsed
            // and user input text length equals short pattern length,
            // then date is valid
            if (dateText.length() == shortDateFormatPattern.length())
                return new EventDate(date.getMonthOfYear(), date.getDayOfMonth());
        } catch (Exception exception) {
            Log.v(LOG_TAG, "Invalid date");
        }
        return null;
    }

    /**
     * Convert {@link EventDate} to string using format pattern
     */
    public static String getEventDateText(Context context, EventDate eventDate) {
        DateTime date;
        String dateFormatPattern;
        if (eventDate.hasYear()) {
            date = new DateTime(eventDate.getYear(), eventDate.getMonth(), eventDate.getDay(),
                    0, 0, 0);

            // DateFormat for full date
            dateFormatPattern = context.getResources().getString(R.string.full_date_format);
        } else {
            date = new DateTime(0, eventDate.getMonth(), eventDate.getDay(),
                    0, 0, 0);

            // DateFormat for date without year
            dateFormatPattern = context.getResources().getString(R.string.date_format_without_year);
        }
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern(dateFormatPattern);
        return dateFormat.print(date);
    }

    /**
     * Convert {@link EventDate} to string using format pattern for view mode
     */
    public static String getEventDateForViewMode(Context context, EventDate eventDate) {
        DateTime date;
        String dateFormatPattern;
        if (eventDate.hasYear()) {
            date = new DateTime(eventDate.getYear(), eventDate.getMonth(), eventDate.getDay(),
                    0, 0, 0);

            // DateFormat for full date
            dateFormatPattern = context.getResources()
                    .getString(R.string.full_date_format_view_mode);
        } else {
            date = new DateTime(0, eventDate.getMonth(), eventDate.getDay(),
                    0, 0, 0);

            // DateFormat for date without year
            dateFormatPattern = context.getResources()
                    .getString(R.string.date_format_without_year_view_mode);
        }
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern(dateFormatPattern);
        return dateFormat.print(date);
    }
}

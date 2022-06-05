package ru.startandroid.importantdates.presentation.helpers;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.startandroid.importantdates.R;

public class EventAgeHelper {
    //If it is impossible to calculate age of the event, set the value -1
    public static final int EMPTY_AGE = -1;

    public static String getAgeText(Context context, int eventYear) {
        String formatPattern = context.getResources().getString(R.string.event_age_label);
        return String.format(formatPattern, getAge(eventYear));
    }

    /**
     * Calculate age for event
     *
     * @return period between two dates
     */
    public static int getAge(int eventYear) {
        // Date of today
        Date today = new Date();
        int currentYear = getCurrentYear(today);
        return getAge(eventYear, currentYear);
    }

    /**
     * Calculate age for event with current date value
     *
     * @return period between two dates
     */
    public static int getAge(int eventYear, Date today) {
        int currentYear = getCurrentYear(today);
        return getAge(eventYear, currentYear);
    }

    /**
     * Calculate age for event with current year value
     *
     * @return period between two dates
     */
    public static int getAge(int eventYear, int currentYear) {
        if (eventYear < 0) return EventAgeHelper.EMPTY_AGE;
        if (currentYear == EMPTY_AGE) return EventAgeHelper.EMPTY_AGE;
        return currentYear - eventYear;
    }

    /**
     * Get current year as number
     */
    private static int getCurrentYear(Date today) {
        // DateFormat for year
        SimpleDateFormat yearFormat =
                new SimpleDateFormat("yyyy", Locale.getDefault());
        try {
            // Try to parse year from date string
            String currentYear = yearFormat.format(today);
            return Integer.parseInt(currentYear);
        } catch (Exception exception) {
            return EventAgeHelper.EMPTY_AGE;
        }
    }
}

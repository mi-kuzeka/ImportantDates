package ru.startandroid.importantdates.presentation.helpers;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.startandroid.importantdates.R;
import ru.startandroid.importantdates.core.domain.EventDate;

public class EventAgeHelper {
    //If it is impossible to calculate age of the event, set the value -1
    public static final int EMPTY_AGE = -1;

    public static String getAgeText(Context context, EventDate eventDate) {
        int currentYear = getDateYear(new Date());
        boolean isPastDate =
                eventDate.getEventDate(currentYear).getTime() < System.currentTimeMillis();
        String formatPattern = context.getResources().getString(
                isPastDate ? R.string.event_age_past_label : R.string.event_age_label);
        return String.format(formatPattern, getAge(eventDate.getYear(), currentYear));
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
     * Get date year as number
     */
    private static int getDateYear(Date date) {
        // DateFormat for year
        SimpleDateFormat yearFormat =
                new SimpleDateFormat("yyyy", Locale.getDefault());
        try {
            // Try to parse year from date string
            String dateYear = yearFormat.format(date);
            return Integer.parseInt(dateYear);
        } catch (Exception exception) {
            return EventAgeHelper.EMPTY_AGE;
        }
    }
}

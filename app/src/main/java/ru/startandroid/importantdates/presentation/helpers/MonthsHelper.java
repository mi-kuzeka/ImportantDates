package ru.startandroid.importantdates.presentation.helpers;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.startandroid.importantdates.R;

public class MonthsHelper {
    private MonthsHelper() {
    }

    private static final int minMonthNumber = 0;
    private static final int maxMonthNumber = 11;
    private static final int monthsCount = 12;

    /**
     * Get list with months names
     */
    public static List<String> getMonthNames(Context context) {
        List<String> months = new ArrayList<String>() {};
        months.add(context.getString(R.string.january_month));
        months.add(context.getString(R.string.february_month));
        months.add(context.getString(R.string.march_month));
        months.add(context.getString(R.string.april_month));
        months.add(context.getString(R.string.may_month));
        months.add(context.getString(R.string.june_month));
        months.add(context.getString(R.string.july_month));
        months.add(context.getString(R.string.august_month));
        months.add(context.getString(R.string.september_month));
        months.add(context.getString(R.string.october_month));
        months.add(context.getString(R.string.november_month));
        months.add(context.getString(R.string.december_month));
        return months;
    }

    public static int getCurrentMonth() {
        // Get current month.
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        if (currentMonth <= maxMonthNumber) {
            return currentMonth;
        }
        return minMonthNumber;
    }

    public static int getMinMonthNumber() {
        return minMonthNumber;
    }

    public static int getMaxMonthNumber() {
        return maxMonthNumber;
    }

    public static int getMonthsCount() {
        return monthsCount;
    }
}

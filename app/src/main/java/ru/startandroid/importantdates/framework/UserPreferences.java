package ru.startandroid.importantdates.framework;

import android.content.Context;
import android.content.SharedPreferences;

import ru.startandroid.importantdates.R;

public class UserPreferences {
    private final String orderByKey;
    private final String orderByDefault;
    private final String orderByDayAsc;
    private final String orderByDayDesc;
    private final String orderByNameAsc;
    private final String orderByNameDesc;
    public static final int ORDER_BY_DEFAULT = 0;
    public static final int ORDER_BY_DAY_ASC = 0;
    public static final int ORDER_BY_DAY_DESC = 1;
    public static final int ORDER_BY_NAME_ASC = 2;
    public static final int ORDER_BY_NAME_DESC = 3;

    public UserPreferences(Context context) {
        orderByKey = context.getString(R.string.order_by_key);
        orderByDefault = context.getString(R.string.order_by_default);
        orderByDayAsc = context.getString(R.string.order_by_day_asc);
        orderByDayDesc = context.getString(R.string.order_by_day_desc);
        orderByNameAsc = context.getString(R.string.order_by_name_asc);
        orderByNameDesc = context.getString(R.string.order_by_name_desc);
    }

    public int turnOrderByPreference(Context context) {
        SharedPreferences sp = context.getSharedPreferences(orderByKey, Context.MODE_PRIVATE);
        int currentOrderBy = getOrderBy(context);
        int newOrderBy = currentOrderBy + 1;
        if (newOrderBy > ORDER_BY_NAME_DESC) newOrderBy = ORDER_BY_DAY_ASC;
        SharedPreferences.Editor orderByEdit = sp.edit();
        orderByEdit.putString(orderByKey, getStringOrderBy(newOrderBy));
        orderByEdit.apply();
        return newOrderBy;
    }

    public String getOrderByPreference(Context context) {
        SharedPreferences sp = context.getSharedPreferences(orderByKey, Context.MODE_PRIVATE);
        return sp.getString(orderByKey, orderByDefault);
    }

    public int getOrderBy(Context context) {
        String orderBy = getOrderByPreference(context);
        if (orderBy.equals(orderByDayAsc)) return ORDER_BY_DAY_ASC;
        if (orderBy.equals(orderByDayDesc)) return ORDER_BY_DAY_DESC;
        if (orderBy.equals(orderByNameAsc)) return ORDER_BY_NAME_ASC;
        if (orderBy.equals(orderByNameDesc)) return ORDER_BY_NAME_DESC;
        return ORDER_BY_DEFAULT;
    }

    public String getStringOrderBy(int orderBy) {
        switch (orderBy) {
            case ORDER_BY_DAY_ASC:
                return orderByDayAsc;
            case ORDER_BY_DAY_DESC:
                return orderByDayDesc;
            case ORDER_BY_NAME_ASC:
                return orderByNameAsc;
            case ORDER_BY_NAME_DESC:
                return orderByNameDesc;
            default:
                return orderByDefault;
        }
    }

    public String getStringOrderBy(Context context) {
        return getStringOrderBy(getOrderBy(context));
    }
}

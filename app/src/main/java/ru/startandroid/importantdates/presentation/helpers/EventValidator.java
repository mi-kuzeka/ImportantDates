package ru.startandroid.importantdates.presentation.helpers;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Locale;

import ru.startandroid.importantdates.R;

public class EventValidator {
    private static final String LOG_TAG = EventValidator.class.getSimpleName();

    /**
     * Check if input string is not empty and set error if string is empty
     */
    public static boolean validateRequired(Context context,
                                        Editable inputText,
                                        TextInputLayout textInputLayout) {
        boolean isValid =
                EventValidator.requiredFieldIsValid(inputText);
        if (isValid) {
            EventValidator.clearError(context, textInputLayout);
        } else {
            EventValidator.setErrorFieldIsRequired(context, textInputLayout);
        }
        return isValid;
    }

    /**
     * Check if input date is valid and set error if date is invalid
     */
    public static boolean validateDate(Context context,
                                    String inputText,
                                    TextInputLayout textInputLayout) {
        boolean isEmpty =
                EventValidator.requiredFieldIsEmpty(inputText);
        if (isEmpty) {
            EventValidator.setErrorFieldIsRequired(context, textInputLayout);
            return false;
        }

        boolean isValid =
                EventValidator.dateFieldIsValid(context, inputText);
        if (isValid) {
            EventValidator.clearError(context, textInputLayout);
        } else {
            EventValidator.setErrorInvalidDate(context, textInputLayout);
        }
        return isValid;
    }

    /**
     * Check if input string is valid date
     */
    public static boolean dateFieldIsValid(Context context, String dateText) {
        String fullDateFormatPattern =
                context.getResources().getString(R.string.full_date_format);
        // DateFormat for full date
        SimpleDateFormat fullDateFormat = new SimpleDateFormat(
                fullDateFormatPattern,
                Locale.getDefault());
        // Don't try to parse non-existent date,like 20.45.2000
        fullDateFormat.setLenient(false);

        try {
            fullDateFormat.parse(dateText);
            // If date is successfully parsed
            // and user input text length equals pattern length,
            // then date is valid
            if (dateText.length() == fullDateFormatPattern.length())
                return true;
        } catch (Exception e) {
            Log.v(LOG_TAG, "Can't parse full date");
        }

        String shortDateFormatPattern =
                context.getResources().getString(R.string.date_format_without_year);
        // DateFormat for date without year
        SimpleDateFormat shortDateFormat = new SimpleDateFormat(
                shortDateFormatPattern,
                Locale.getDefault());
        // Don't try to parse non-existent date,like 20.45
        shortDateFormat.setLenient(false);

        try {
            shortDateFormat.parse(dateText);
            // If date is successfully parsed
            // and user input text length equals short pattern length,
            // then date is valid
            if (dateText.length() == shortDateFormatPattern.length())
                return true;
        } catch (Exception exception) {
            Log.v(LOG_TAG, "Invalid date");
        }
        return false;
    }

    /**
     * Check if input string is empty
     */
    public static boolean requiredFieldIsEmpty(String inputText) {
        return TextUtils.isEmpty(inputText.trim());
    }

    /**
     * Check if input string is not empty
     */
    public static boolean requiredFieldIsValid(Editable inputText) {
        if (inputText == null) return false;
        return !TextUtils.isEmpty(inputText.toString().trim());
    }

    /**
     * Set error message to TextInputLayout about field is required
     */
    public static void setErrorFieldIsRequired(Context context,
                                               TextInputLayout textInputLayout) {
        textInputLayout.setError(
                context.getResources().getString(R.string.error_field_is_required));
    }

    /**
     * Set error message to TextInputLayout about date incorrect format
     */
    public static void setErrorInvalidDate(Context context,
                                           TextInputLayout textInputLayout) {
        textInputLayout.setError(
                context.getResources().getString(R.string.error_date_incorrect_format));
    }

    /**
     * Clear error message in TextInputLayout
     */
    public static void clearError(Context context,
                                  TextInputLayout textInputLayout) {
        textInputLayout.setError(null);
    }
}

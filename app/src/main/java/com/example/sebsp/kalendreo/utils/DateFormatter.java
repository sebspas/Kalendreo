package com.example.sebsp.kalendreo.utils;

import android.content.Context;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Gaetan on 17/11/2017.
 * Class used to get Dates in desired format
 */
public class DateFormatter {

    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;

    /**
     * Create a DateFormatter according to the application context
     * Take in account the locale & the preferred format of the user
     *
     * @param context Android context in order to get the local and user preferences
     */
    public DateFormatter(Context context) {
        Format androidFormat = android.text.format.DateFormat.getLongDateFormat(context);
        String format = ((SimpleDateFormat) androidFormat).toLocalizedPattern();
        dateFormat = new SimpleDateFormat(format, context.getResources().getConfiguration().locale);
        timeFormat = new SimpleDateFormat("HH:mm", context.getResources().getConfiguration().locale);
    }

    /**
     * Format the date given
     *
     * @param date the date to format
     * @return the date formatted according to the application context
     */
    public String getDate(Calendar date) {
        return dateFormat.format(date.getTime());
    }

    /**
     * Format the date given to a time (HH:mm)
     *
     * @param date the date to format
     * @return the date formatted according to the application context
     */
    public String getTime(Calendar date) {
        return timeFormat.format(date.getTime());
    }

    /**
     * Return date and time
     *
     * @param date the date to format
     * @return the date formatted according to the application context
     */
    public String getFullDate(Calendar date) {
        return getDate(date) + " " + getTime(date);
    }

    /**
     * Truncate today's date to midnight
     *
     * @param date Date to truncate
     * @return the date with time set to 00:00:00
     */
    public static Calendar midnight(Calendar date) {
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        return date;
    }

    /**
     * @param date @see this.midnight
     * @return Midnight of tomorrow
     * @see this.midnight
     */
    public static Calendar nextMidnight(Calendar date) {
        Calendar newDate = Calendar.getInstance();
        newDate.setTimeInMillis(midnight(date).getTimeInMillis());
        newDate.add(Calendar.DAY_OF_MONTH, 1);
        return newDate;
    }

}

package com.example.sebsp.kalendreo.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeBoundsChecker {

    private static int MAX_HOUR = 24;
    private static int MAX_MONTH = 24;

    // nbOfHours must be between 0 and 24 !
    public static int[] checkForHour(int nbOfHours, int hour, int day, int month, int year){
        int tableHour[] = new int[4];

        hour -= nbOfHours;
        if (hour < 0){
            hour = MAX_HOUR-1;
            int tableDay[] = checkForDay(1, day, month, year);

            tableHour[1] = tableDay[0];
            tableHour[2] = tableDay[1];
            tableHour[3] = tableDay[2];

        }else {
            tableHour[1] = day;
            tableHour[2] = month;
            tableHour[3] = year;
        }
        tableHour[0] = hour;

        return tableHour;
    }

    // nbOfDays must be between 0 and 28 (max) !
    public static int[] checkForDay(int nbOfDays, int day, int month, int year){
        int tableDay[] = new int[3];

        day -= nbOfDays;
        if (day < 1){

            int tableMonth[] = checkForMonth(1, month, year);
            tableDay[1] = tableMonth[0];
            tableDay[2] = tableMonth[1];

            // Create a calendar object and set year and month
            Calendar mycal = new GregorianCalendar(tableDay[2], tableDay[1], 2);

            // Get the max number of days in that month
            int maxDaysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);

            day = maxDaysInMonth-nbOfDays;
        }
        else {
            tableDay[1] = month;
            tableDay[2] = year;
        }
        tableDay[0] = day;

        return tableDay;
    }

    // nbOfMonth must be between 0 and 11 !
    public static int[] checkForMonth(int nbOfMonth, int month, int year){
        int tableMonth[] = new int[2];

        month -= nbOfMonth;
        if (month < 1){
            month = MAX_MONTH-nbOfMonth;
            tableMonth[1] = --year;
        }
        else {
            tableMonth[1] = year;
        }
        tableMonth[0] = month;

        return tableMonth;
    }
}

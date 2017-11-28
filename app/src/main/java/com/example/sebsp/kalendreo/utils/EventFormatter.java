package com.example.sebsp.kalendreo.utils;

import android.content.Context;

import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.model.Event;

/**
 * Created by Gaetan on 28/11/2017.
 * Class Used to display an event to text properly
 */
public class EventFormatter {

    private static DateFormatter dateFormatter;

    public static String getText(Context context, Event event) {
        if (dateFormatter == null) dateFormatter = new DateFormatter(context);

        String result = "";
        result += event.getTitle() + " " + context.getResources().getString(R.string.from) + " ";
        result += dateFormatter.getFullDate(event.getStartDate()) + " ";
        result += context.getResources().getString(R.string.to) + " " + dateFormatter.getFullDate(event.getEndDate());
        return result;
    }
}

package com.example.sebsp.kalendreo.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.sebsp.kalendreo.model.Event;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class ReminderManager {

    static int ALARM_TYPE_EVENT = 100;

    public static void createAReminder(Context context, Event e) {
        //get calendar instance to be able to select what time notification should be scheduled
        Calendar calendar = Calendar.getInstance();

        String[] dateSplit = e.dateDeb.split("/");
        int year = Integer.parseInt(dateSplit[0]);
        int month = Integer.parseInt(dateSplit[1])-1;
        int day = Integer.parseInt(dateSplit[2]);

        String[] hourSplit = e.startHour.split(":");
        int hour = Integer.parseInt(hourSplit[0]);
        int min = Integer.parseInt(hourSplit[1]);

        // set the notification time
        calendar.set(year, month, day, hour, min, 00);

        //Setting intent to class where Alarm broadcast message will be handled
        Intent intent = new Intent(context, AlarmReceiver.class);
        // we add the name of the event has param
        intent.putExtra("Title", e.title);
        intent.putExtra("StartDate", e.dateDeb);
        intent.putExtra("StartTime", e.startHour);
        intent.putExtra("EndTime", e.endHour);
        intent.putExtra("EndDate", e.dateFin);

        //Setting alarm pending intent to go on the broadcast receiver
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM_TYPE_EVENT,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //getting instance of AlarmManager service
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);

        //Setting alarm for the given time
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}

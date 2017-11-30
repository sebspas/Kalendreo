package com.example.sebsp.kalendreo.utils;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Debug;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.sebsp.kalendreo.Manifest;
import com.example.sebsp.kalendreo.model.Event;

import static android.content.Context.ALARM_SERVICE;

public class ReminderManager {

    static int ALARM_TYPE_EVENT = 100;

    public static void createAReminder(Context context, Event e) {

        DateFormatter dateFormatter = new DateFormatter(context);

        //Setting intent to class where Alarm broadcast message will be handled
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("Title", e.getTitle());
        intent.putExtra("StartDate", dateFormatter.getFullDate(e.getStartDate()));
        intent.putExtra("EndDate", dateFormatter.getFullDate(e.getEndDate()));
        intent.putExtra("Categorie", e.getCategory());
        intent.putExtra("Type", "Start"); // to specify if it's the beginning or the end reminder

        setStartReminder(e, intent, context);

        // we add an other reminder to unmute the phone at the end of the event
        if (e.getCategory().equals("Work")) {
            //Setting intent to class where Alarm broadcast message will be handled
            Intent endIntent = new Intent(context, AlarmReceiver.class);
            endIntent.putExtra("Title", e.getTitle());
            endIntent.putExtra("StartDate", dateFormatter.getFullDate(e.getStartDate()));
            endIntent.putExtra("EndDate", dateFormatter.getFullDate(e.getEndDate()));
            endIntent.putExtra("Categorie", e.getCategory());
            endIntent.putExtra("Type", "End");

            setEndReminder(e, endIntent, context);
        }
    }

    private static void setStartReminder(Event e, Intent intent, Context context) {

        //getting instance of AlarmManager service
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        //Setting alarm pending intent to go on the broadcast receiver
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM_TYPE_EVENT,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Setting alarm for the given time
        alarmManager.set(AlarmManager.RTC_WAKEUP, e.getStartDate().getTimeInMillis(), pendingIntent);

        // TODO set various reminders according to the event category
//        if (e.categorie.equals("Fun")){
//            // check the correctness of the date for a 2-hour-early reminder
//            int checkedDate[] = TimeBoundsChecker.checkForHour(2, hour, day, month, year);
//            calendar.set(checkedDate[3], checkedDate[2], checkedDate[1], checkedDate[0], min, 00);
//
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//        }

    }

    private static void setEndReminder(Event e, Intent intent, Context context) {

        //getting instance of AlarmManager service
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        //Setting alarm pending intent to go on the broadcast receiver
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM_TYPE_EVENT+1,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Setting alarm for the given time
        alarmManager.set(AlarmManager.RTC_WAKEUP, e.getEndDate().getTimeInMillis(), pendingIntent);
    }
}

package com.example.sebsp.kalendreo.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.sebsp.kalendreo.model.Event;

import static android.content.Context.ALARM_SERVICE;

public class ReminderManager {

    static int ALARM_TYPE_EVENT = 100;

    public static void createAReminder(Context context, Event e) {


        DateFormatter dateFormatter = new DateFormatter(context);

        //Setting intent to class where Alarm broadcast message will be handled
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("Title", e.getTitle());
        intent.putExtra("StartDate", dateFormatter.getFullDateFromContext(e.getStartDate()));
        intent.putExtra("EndDate", dateFormatter.getFullDateFromContext(e.getEndDate()));

        //Setting alarm pending intent to go on the broadcast receiver
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM_TYPE_EVENT,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //getting instance of AlarmManager service
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        setReminders(alarmManager, e, pendingIntent);
    }

    private static void setReminders(AlarmManager alarmManager, Event e, PendingIntent pendingIntent) {


        //Setting alarm for the given time
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, e.getStartDate().getTimeInMillis(), pendingIntent);

        // TODO set various reminders according to the event category
//        if (e.categorie.equals("Fun")){
//            // check the correctness of the date for a 2-hour-early reminder
//            int checkedDate[] = TimeBoundsChecker.checkForHour(2, hour, day, month, year);
//            calendar.set(checkedDate[3], checkedDate[2], checkedDate[1], checkedDate[0], min, 00);
//
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//        }
    }
}

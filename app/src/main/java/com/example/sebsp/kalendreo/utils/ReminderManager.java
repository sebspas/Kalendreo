package com.example.sebsp.kalendreo.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.sebsp.kalendreo.model.Event;

import java.util.Calendar;

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

        //Setting alarm pending intent to go on the broadcast receiver
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM_TYPE_EVENT,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //getting instance of AlarmManager service
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        setReminders(alarmManager, e, pendingIntent);
    }

    private static void setReminders(AlarmManager alarmManager, Event e, PendingIntent pendingIntent) {

        Calendar calendar = e.getStartDate();
        // start date of the event
        Calendar eDate = e.getStartDate();
        int min = eDate.get(Calendar.MINUTE);
        int hour = eDate.get(Calendar.HOUR_OF_DAY);
        int day = eDate.get(Calendar.DAY_OF_MONTH);
        int month = eDate.get(Calendar.MONTH) +1;   // 0 based months !
        int year = eDate.get(Calendar.YEAR);

        //Setting alarm for the given time
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, eDate.getTimeInMillis(), pendingIntent);

        // TODO set various reminders according to the event category
       if (e.getCategory().equals("Fun")){
           Log.d("FUN:::::", "hellloooooooooooooooo");
            // check the correctness of the date for a 2-hour-early reminder
            int checkedDate[] = TimeBoundsChecker.checkForHour(2, hour, day, month, year);
            Log.d("BEFORE:::::", String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
            calendar.set(checkedDate[3], checkedDate[2], checkedDate[1], checkedDate[0], min, 00);
            Log.d("AFTER:::::", String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }
}

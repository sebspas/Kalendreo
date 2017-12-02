package com.example.sebsp.kalendreo.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.example.sebsp.kalendreo.model.Event;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class ReminderManager {

    // to check unlocked phone for work categorie
    static PhoneUnlockedReceiver unlockedReceiver;

    static int ALARM_TYPE_EVENT = 100;
    private static long MINUTES = 60000;
    private static long HOURS =  3600000;
    private static long DAYS = 86400000;

    private static long currentTime = Calendar.getInstance().getTimeInMillis();

    public static void createAReminder(Context context, Event e) {
        setStartReminder(e, getStartIntent(e, context), context);
    }

    private static void setStartReminder(Event e, Intent intent, Context context) {

        //getting instance of AlarmManager service
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        //Setting alarm pending intent to go on the broadcast receiver
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM_TYPE_EVENT,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Setting alarm for the given time
        alarmManager.set(AlarmManager.RTC_WAKEUP, e.getStartDate().getTimeInMillis(), pendingIntent);


        int alarm_type_event = ALARM_TYPE_EVENT+1;
        setCategoryReminders(e, intent, context, alarm_type_event);
    }

    private static void setEndReminder(Event e, Context context, int alarm_type_event) {
        //getting instance of AlarmManager service
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        //Setting alarm pending intent to go on the broadcast receiver
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarm_type_event,
                getEndIntent(e, context), PendingIntent.FLAG_UPDATE_CURRENT);

        //Setting alarm for the given time
        alarmManager.set(AlarmManager.RTC_WAKEUP, e.getEndDate().getTimeInMillis(), pendingIntent);
    }

    private static void setCategoryReminders(Event e, Intent intent, Context context, int alarm_type_event){
        //getting instance of AlarmManager service
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        //Setting alarm pending intent to go on the broadcast receiver
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarm_type_event,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Setting alarm for the given time
        long reminderTime = e.getStartDate().getTimeInMillis();

        switch (e.getCategory()){
            case "Fun" :
                // 1 hour before
                reminderTime -= (HOURS);
                if (currentTime <= reminderTime) {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, reminderTime, pendingIntent);
                }
                break;

            case "Serious":
                // 1 hour before
                reminderTime -= (HOURS);
                if (currentTime <= reminderTime) {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, reminderTime, pendingIntent);
                }

                // 1 day before
                reminderTime = e.getStartDate().getTimeInMillis() - DAYS;
                if (currentTime <= reminderTime) {
                    pendingIntent = PendingIntent.getBroadcast(context, ++alarm_type_event,
                            intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, reminderTime, pendingIntent);
                }
                break;

            case "Very Important":
                // 1 hour before
                reminderTime -= (HOURS);
                if (currentTime <= reminderTime) {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, reminderTime, pendingIntent);
                }

                // 1 day before
                reminderTime = e.getStartDate().getTimeInMillis() - DAYS;
                if (currentTime <= reminderTime) {
                    pendingIntent = PendingIntent.getBroadcast(context, ++alarm_type_event,
                            intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, reminderTime, pendingIntent);
                }

                // 7 days before
                reminderTime = e.getStartDate().getTimeInMillis() - (7*DAYS);
                if (currentTime <= reminderTime) {
                    pendingIntent = PendingIntent.getBroadcast(context, ++alarm_type_event,
                            intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, reminderTime, pendingIntent);
                }
                break;

            case "Work":
                // 10 minutes before
                reminderTime -= (10*MINUTES);
                if (currentTime <= reminderTime) {
                    pendingIntent = PendingIntent.getBroadcast(context, ++alarm_type_event,
                            getIntent(e, context), PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, reminderTime, pendingIntent);
                }

                setEndReminder(e, context, 0);
                break;

            case "Silent":

                // 20 minutes before
                reminderTime -= (20*MINUTES);
                if (currentTime <= reminderTime) {
                    pendingIntent = PendingIntent.getBroadcast(context, ++alarm_type_event,
                            getIntent(e, context), PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, reminderTime, pendingIntent);
                }

                setEndReminder(e, context, 1);
                break;

            default:
                break;
        }
    }

    // to not turn off volume for early work or silent reminders
    private static Intent getIntent(Event e, Context context){
        DateFormatter dateFormatter = new DateFormatter(context);

        //Setting intent to class where Alarm broadcast message will be handled
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("Title", e.getTitle());
        intent.putExtra("StartDate", dateFormatter.getFullDate(e.getStartDate()));
        intent.putExtra("EndDate", dateFormatter.getFullDate(e.getEndDate()));
        intent.putExtra("Category", e.getCategory());
        intent.putExtra("Type", "None"); // to specify it's neither the beginning or the end reminder

        return intent;
    }

    private static Intent getStartIntent(Event e, Context context){
        DateFormatter dateFormatter = new DateFormatter(context);

        //Setting intent to class where Alarm broadcast message will be handled
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("Title", e.getTitle());
        intent.putExtra("StartDate", dateFormatter.getFullDate(e.getStartDate()));
        intent.putExtra("EndDate", dateFormatter.getFullDate(e.getEndDate()));
        intent.putExtra("Category", e.getCategory());
        intent.putExtra("Type", "Start"); // to specify if it's the beginning or the end reminder

        return intent;
    }

    private static Intent getEndIntent(Event e, Context context){
        DateFormatter dateFormatter = new DateFormatter(context);
        Intent endIntent = new Intent(context, AlarmReceiver.class);

        endIntent.putExtra("Title", e.getTitle());
        endIntent.putExtra("StartDate", dateFormatter.getFullDate(e.getStartDate()));
        endIntent.putExtra("EndDate", dateFormatter.getFullDate(e.getEndDate()));
        endIntent.putExtra("Category", e.getCategory());
        endIntent.putExtra("Type", "End");

        return endIntent;
    }

    public static void getPersuasive(Context context){
        //Setting intent to class where Alarm broadcast message will be handled
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("Title", "Got you! ;)");
        intent.putExtra("Content", "Better get back to work, heh?");
        intent.putExtra("Category", "Persuasive");
        intent.putExtra("Type", "None"); // to specify it's neither the beginning or the end reminder

        //getting instance of AlarmManager service
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        //Setting alarm pending intent to go on the broadcast receiver
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 8,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Setting alarm for the current time
        alarmManager.set(AlarmManager.RTC_WAKEUP, currentTime, pendingIntent);
    }

    public static IntentFilter setPhoneUnlockedReceiver(){
        unlockedReceiver = new PhoneUnlockedReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_USER_PRESENT);
        filter.addAction(Intent.ACTION_SCREEN_ON);

        return filter;
    }
}

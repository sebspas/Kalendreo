package com.example.sebsp.kalendreo.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v4.app.NotificationCompat;
import android.widget.CalendarView;

import com.example.sebsp.kalendreo.R;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Intent to invoke app when click on notification.
        Intent intentOnClick = new Intent(context, CalendarView.class);

        //set flag to restart/relaunch the app
        intentOnClick.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //Pending intent to handle launch of Activity in intent above
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, ReminderManager.ALARM_TYPE_EVENT, intentOnClick,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        String contentText = "From " + intent.getStringExtra("StartDate") +
                " To " + intent.getStringExtra("EndDate");
        //Build notification
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(intent.getStringExtra("Title"))
                        .setContentText(contentText);

        // define the onclick action
        mBuilder.setContentIntent(pendingIntent);

        //Send local notification
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(ReminderManager.ALARM_TYPE_EVENT, mBuilder.build());

        String categorie = intent.getStringExtra("Categorie");
        String type = intent.getStringExtra("Type");
        if ((categorie.equals("Work") || categorie.equals("Silent")) && type.equals("Start")) {
            setRingerToSilent(context);
        } else if ((categorie.equals("Work") || categorie.equals("Silent")) && type.equals("End")) {
            setRingerToNormal(context);
        }
    }

    /**
     * To Enable silent mode
     */
    private void setRingerToSilent(Context context){
        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }

    /**
     * To Enable Ringer mode
     */
    private void setRingerToNormal(Context context) {
        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }

}
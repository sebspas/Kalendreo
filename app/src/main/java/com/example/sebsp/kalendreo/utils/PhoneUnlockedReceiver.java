package com.example.sebsp.kalendreo.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class PhoneUnlockedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //String TAG = "PHONE:::::";
        if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)){
            //Log.d(TAG, "Phone unlocked");

            // get reprimanded with a toast
            CharSequence text = "Your work session is not over yet, be brave!";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();

        }else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            //Log.d(TAG, "Phone screen on");

            // send a persuasive notif
            ReminderManager.getPersuasive(context);
        }
    }
}
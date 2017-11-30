package com.example.sebsp.kalendreo.calendar;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.components.layouts.EventViewGroup;
import com.example.sebsp.kalendreo.model.Event;
import com.example.sebsp.kalendreo.structure.AbstractLoggedInActivity;

import static com.facebook.FacebookSdk.getApplicationContext;

public class CreateEventActivity extends AbstractLoggedInActivity {

    LinearLayout linearLayoutContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        linearLayoutContainer = findViewById(R.id.create_event_container);

        // ASK for the right to modify the sound
        NotificationManager notificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {
            Intent newPolicyIntent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

            startActivity(newPolicyIntent);
        }

        Event event = getEventSetFromIntent();
        // Create & add the new event view group
        linearLayoutContainer.addView(new EventViewGroup(this, event, this));
    }

    /**
     * Check if a start date has been passed in an intent
     *
     * @return a new default event if no star date passed, else an event with start_date
     * set to intent value passed
     */
    private Event getEventSetFromIntent() {
        long dateLong = getLongFromIntent(getString(R.string.EXTRA_START_DATE_SELECTED));
        if (dateLong != -1) { // If not found
            return new Event(dateLong);
        }
        return new Event();
    }
}

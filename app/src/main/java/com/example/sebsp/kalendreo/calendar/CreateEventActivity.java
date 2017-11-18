package com.example.sebsp.kalendreo.calendar;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.sebsp.kalendreo.AbstractLoggedInActivity;
import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.components.EventViewGroup;
import com.example.sebsp.kalendreo.model.Event;

public class CreateEventActivity extends AbstractLoggedInActivity {

    LinearLayout linearLayoutContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        linearLayoutContainer = findViewById(R.id.create_event_container);

        // Create & add the new event view group
        linearLayoutContainer.addView(new EventViewGroup(this, new Event(), this));
    }
}

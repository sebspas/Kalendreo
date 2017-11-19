package com.example.sebsp.kalendreo.calendar;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.sebsp.kalendreo.structure.AbstractEventActivity;
import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.components.layouts.EventViewGroup;
import com.example.sebsp.kalendreo.model.Event;

public class EditEventActivity extends AbstractEventActivity {

    LinearLayout linearLayoutContainer;
    private EventViewGroup eventViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        linearLayoutContainer = findViewById(R.id.create_event_container);
    }

    @Override
    protected void loadEvent(String idEvent) {
        Event.getReference(firebaseUser.getUid()).child(idEvent).
                addListenerForSingleValueEvent(listener);
    }

    @Override
    public void onEventLoaded(Event event) {
        super.onEventLoaded(event);
        linearLayoutContainer.removeView(this.eventViewGroup);
        eventViewGroup = new EventViewGroup(EditEventActivity.this, event, EditEventActivity.this);
        linearLayoutContainer.addView(eventViewGroup);
    }
}

package com.example.sebsp.kalendreo.calendar;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.sebsp.kalendreo.AbstractEventActivity;
import com.example.sebsp.kalendreo.EventLoadedListener;
import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.components.EventViewGroup;
import com.example.sebsp.kalendreo.model.Event;
import com.google.firebase.database.FirebaseDatabase;

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
                addListenerForSingleValueEvent(new EventLoadedListener(this));
    }

    @Override
    protected void onEventLoaded(Event event) {
        super.onEventLoaded(event);
        linearLayoutContainer.removeView(this.eventViewGroup);
        eventViewGroup = new EventViewGroup(EditEventActivity.this, event, EditEventActivity.this);
        linearLayoutContainer.addView(eventViewGroup);
    }
}

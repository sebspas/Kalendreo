package com.example.sebsp.kalendreo.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;

import com.example.sebsp.kalendreo.AbstractEventActivity;
import com.example.sebsp.kalendreo.EventLoadedListener;
import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.model.Event;
import com.example.sebsp.kalendreo.utils.DateFormatter;

public class EventViewActivity extends AbstractEventActivity {

    private TextView eventType;
    private TextView title;
    private TextView startDate;
    private TextView startTime;
    private TextView endDate;
    private TextView endTime;
    private FloatingActionButton editEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);

        title = findViewById(R.id.eventTitle);
        startDate = findViewById(R.id.SelectStartDate);
        startTime = findViewById(R.id.SelectStartHours);
        endDate = findViewById(R.id.SelectEndDate);
        endTime = findViewById(R.id.SelectEndHours);
        eventType = findViewById(R.id.ListCategorie);
        editEvent = findViewById(R.id.EditEvent);
    }

    @Override
    protected void loadEvent(String idEvent) {
        // Refresh automatically (not only once)
        Event.getReference(firebaseUser.getUid()).child(idEvent).
                addValueEventListener(listener);
    }

    @Override
    public void onEventLoaded(Event event) {
        super.onEventLoaded(event);
        displayViews(event);
    }

    private void displayViews(final Event event) {
        setTitle(getString(R.string.title_activity_event_view) + " " + event.getTitle());

        DateFormatter dateFormatter = new DateFormatter(this);

        title.setText(event.getTitle());
        startDate.setText(dateFormatter.getDate(event.getStartDate()));
        startTime.setText(dateFormatter.getTime(event.getStartDate()));
        endDate.setText(dateFormatter.getDate(event.getEndDate()));
        endTime.setText(dateFormatter.getTime(event.getEndDate()));
        eventType.setText(event.getCategory());

        editEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventViewActivity.this, EditEventActivity.class);
                intent.putExtra(getString(R.string.EXTRA_EVENT_ID), event.getId());
                launchAndClose(intent);
            }
        });
    }

}

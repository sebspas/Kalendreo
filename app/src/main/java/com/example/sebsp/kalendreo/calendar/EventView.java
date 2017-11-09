package com.example.sebsp.kalendreo.calendar;

import android.os.Bundle;
import android.widget.TextView;

import com.example.sebsp.kalendreo.AbstractLoggedInActivity;
import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.model.AllEvents;
import com.example.sebsp.kalendreo.model.Event;

public class EventView extends AbstractLoggedInActivity {
    Event currEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);


        // get the event data from the db
        currEvent = AllEvents.getInstance().listOfEvents.get(
                Integer.parseInt(getIntent().getStringExtra("EventId")));

        // fill the different textView
        TextView title = findViewById(R.id.eventTitle);
        title.setText(currEvent.title);

        TextView startDate = findViewById(R.id.SelectStartDate);
        startDate.setText(currEvent.dateDeb);

        TextView startTime = findViewById(R.id.SelectStartHours);
        startTime.setText(currEvent.startHour);

        TextView endDate = findViewById(R.id.SelectEndDate);
        endDate.setText(currEvent.dateFin);

        TextView endTime = findViewById(R.id.SelectEndHours);
        endTime.setText(currEvent.endHour);

        TextView eventType = findViewById(R.id.ListCategorie);
        eventType.setText(currEvent.categorie);
    }

}

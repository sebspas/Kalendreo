package com.example.sebsp.kalendreo.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sebsp.kalendreo.AbstractMultipleEventsActivity;
import com.example.sebsp.kalendreo.MultipleEventsLoadedListener;
import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.model.Event;
import com.example.sebsp.kalendreo.utils.DateFormatter;

import java.util.Calendar;
import java.util.List;

public class MyEventsActivity extends AbstractMultipleEventsActivity {

    ListView eventsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        FloatingActionButton createEvent = findViewById(R.id.btn_add_event);
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyEventsActivity.this, CreateEventActivity.class));
            }
        });

        eventsList = findViewById(R.id.my_events_list_view);
    }

    @Override
    protected void loadEvents() {
        Event.getReference(firebaseUser.getUid()).orderByChild("startDate")
//                .startAt(System.currentTimeMillis())
                .startAt(DateFormatter.midnight(Calendar.getInstance()).getTimeInMillis()) // To display all events today (not only future)
                .addValueEventListener(listener);
    }
    
    @Override
    protected void onEventsLoaded(List<Event> eventsLoaded) {
        super.onEventsLoaded(eventsLoaded);
        displayListView(eventsList);
    }

}

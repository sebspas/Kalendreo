package com.example.sebsp.kalendreo.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sebsp.kalendreo.AbstractLoggedInActivity;
import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.model.AllEvents;
import com.example.sebsp.kalendreo.model.Event;

import java.util.ArrayList;
import java.util.Calendar;

public class MyEventsActivity extends AbstractLoggedInActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        FloatingActionButton createEvent = findViewById(R.id.AddEvent);
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyEventsActivity.this, CreateEventActivity.class));
            }
        });

        // get all the events
        AllEvents.getInstance();
        displayAllEventsFromTodayOn();
    }

    protected String getTodayDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "/" + month + "/" + day;
    }

    protected void displayAllEventsFromTodayOn(){
        ListView eventsList = findViewById(R.id.my_events_list_view);
        String today = getTodayDate();

        ArrayList<String> fromTodayEventString = new ArrayList<>();
        final ArrayList<Event> fromTodayEvents = new ArrayList<>();

        for (Event event :  AllEvents.getInstance().listOfEvents){

            if (event.dateDeb.compareTo(today) >= 0){
                fromTodayEventString.add(event.toString());
                fromTodayEvents.add(event);
            }
        }

        final ArrayAdapter adapter = new ArrayAdapter<>(MyEventsActivity.this,
                android.R.layout.simple_list_item_1, fromTodayEventString);
        eventsList.setAdapter(adapter);

        eventsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String key = String.valueOf(AllEvents.getInstance()
                        .listOfEvents.indexOf(fromTodayEvents.get(i)));

                Intent intent = new Intent(MyEventsActivity.this, EventView.class);
                intent.putExtra("EventId", key);
                startActivity(intent);
            }
        });
    }
}

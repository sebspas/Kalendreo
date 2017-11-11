package com.example.sebsp.kalendreo.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sebsp.kalendreo.AbstractLoggedInActivity;
import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.model.AllEvents;
import com.example.sebsp.kalendreo.model.Event;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarViewActivity extends AbstractLoggedInActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

        //TODO move this to the splash screen activity
        // get all the events
        AllEvents.getInstance();
        //createEventList();
        firstDayEventDisplay();

        FloatingActionButton createEvent = findViewById(R.id.AddEvent);
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CalendarViewActivity.this, CreateEventActivity.class));
            }
        });

        // display date of the selected day
        final android.widget.CalendarView calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new android.widget.CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull android.widget.CalendarView calendarView, int year, int month, int day) {
                String selectedDay = year + "/" + (++month) + "/" + day;
                displayEventList(selectedDay);
            }
        });
    }

    protected void firstDayEventDisplay(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String todayDate =  year + "/" + month + "/" + day;
        displayEventList(todayDate);
    }

    protected void displayEventList(String selectedDay){
        TextView nEvent = findViewById(R.id.no_event);
        ListView calendarList = findViewById(R.id.calendar_list_view);

        int numberOfEvent = 0;
        ArrayList<String> todayEventString = new ArrayList<>();
        final ArrayList<Event> todayEvents = new ArrayList<>();

        for (Event event :  AllEvents.getInstance().listOfEvents){
            // check if there is an event for the day
            if(event.dateDeb.equals(selectedDay)){
                numberOfEvent++;
                todayEventString.add(event.toString());
                todayEvents.add(event);
                //Log.d("calendar::", "EVENT FOR TODAY");
            }
        }

        if (numberOfEvent > 0){
            String msg = numberOfEvent + getString(R.string.some_event_text) + selectedDay;
            nEvent.setText(msg);

            final ArrayAdapter adapter = new ArrayAdapter<>(CalendarViewActivity.this,
                    android.R.layout.simple_list_item_1, todayEventString);
            calendarList.setAdapter(adapter);
            calendarList.setVisibility(View.VISIBLE);

            calendarList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String key = String.valueOf(AllEvents.getInstance()
                            .listOfEvents.indexOf(todayEvents.get(i)));
                    //Toast.makeText(CalendarViewActivity.this, key, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CalendarViewActivity.this, EventView.class);
                    intent.putExtra("EventId", key);
                    startActivity(intent);
                }
            });
        }
        else {
            String msg = getString(R.string.no_event_text) + selectedDay;
            nEvent.setText(msg);

            calendarList.setVisibility(View.INVISIBLE);
        }
    }
}


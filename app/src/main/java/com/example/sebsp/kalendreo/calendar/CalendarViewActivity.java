package com.example.sebsp.kalendreo.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.sebsp.kalendreo.AbstractLoggedInActivity;
import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.model.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class CalendarViewActivity extends AbstractLoggedInActivity {

    protected ArrayList<Event> listOfEvents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        createEventList();

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
                TextView nEvent = findViewById(R.id.no_event);
                TextView summary = findViewById(R.id.event_summary);

                String selectedDay = year + "/" + (++month) + "/" + day;

                String eventForTheDay = "";
                int numberOfEvent = 0;

                for (Event event : listOfEvents){
                    // check if there is an event for the day
                    if(event.dateDeb.equals(selectedDay)){
                        numberOfEvent++;

                        Log.d("calendar::", "EVENT FOR TODAY");
                        eventForTheDay = event.title + "\nstarts on " + event.dateDeb + ", ends on "
                                + event.dateFin + "\nfrom " + event.startHour + " to " + event.endHour;
                    }
                }

                if (numberOfEvent > 0){
                    String msg = numberOfEvent + getString(R.string.some_event_text) + selectedDay;
                    nEvent.setText(msg);

                    summary.setVisibility(View.VISIBLE);
                    summary.setText(eventForTheDay);
                }
                else {
                    String msg = getString(R.string.no_event_text) + selectedDay;
                    nEvent.setText(msg);

                    summary.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    protected void createEventList(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("events").child(firebaseUser.getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> eventIterable = dataSnapshot.getChildren();

                for(DataSnapshot eventSnap : eventIterable) {

                    Map eventMap = (Map) eventSnap.getValue();

                    Event event = new Event((String)eventMap.get("title"),
                            (String)eventMap.get("dateDeb"),
                            (String)eventMap.get("dateFin"),
                            (String)eventMap.get("startHour"),
                            (String)eventMap.get("endHour"));

                    Log.d("event::",
                            " val: t=" + event.title + " _ dd=" + event.dateDeb + " _ df="
                                    + event.dateFin + " _ sh=" + event.startHour + " _ eh=" + event.endHour);

                    listOfEvents.add(event);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}


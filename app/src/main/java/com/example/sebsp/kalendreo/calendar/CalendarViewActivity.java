package com.example.sebsp.kalendreo.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sebsp.kalendreo.structure.AbstractMultipleEventsActivity;
import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.model.Event;
import com.example.sebsp.kalendreo.utils.DateFormatter;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarViewActivity extends AbstractMultipleEventsActivity {

    /**
     * Date selected by the calendar view, today by default
     */
    private Calendar dateSelected = new GregorianCalendar();

    private DateFormatter dateFormatter;
    private TextView nEvent;
    private ListView calendarList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

        dateFormatter = new DateFormatter(this);

        FloatingActionButton createEvent = findViewById(R.id.btn_add_event);
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CalendarViewActivity.this, CreateEventActivity.class));
            }
        });

        nEvent = findViewById(R.id.no_event);
        calendarList = findViewById(R.id.calendar_list_view);
        final android.widget.CalendarView calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new android.widget.CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull android.widget.CalendarView calendarView, int year, int month, int day) {
                dateSelected = DateFormatter.midnight(new GregorianCalendar(year, month, day));
                loadEvents();
            }
        });
    }

    @Override
    protected void loadEvents() {
        Event.getReference(firebaseUser.getUid()).orderByChild("startDate")
                .startAt(DateFormatter.midnight(dateSelected).getTimeInMillis())
                .endAt(DateFormatter.nextMidnight(dateSelected).getTimeInMillis())
                .addValueEventListener(listener);
    }

    @Override
    public void onEventsLoaded(List<Event> events) {
        super.onEventsLoaded(events);

        String message;
        if (events.isEmpty()) {
            message = getString(R.string.no_event_text) + dateFormatter.getDate(dateSelected);
            calendarList.setVisibility(View.INVISIBLE);
        } else {
            message = events.size() + getString(R.string.some_event_text) + dateFormatter.getDate(dateSelected);
            displayListView(calendarList);
        }
        nEvent.setText(message);
    }
}


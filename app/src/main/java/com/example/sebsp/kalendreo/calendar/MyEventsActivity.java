package com.example.sebsp.kalendreo.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sebsp.kalendreo.AbstractLoggedInActivity;
import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.model.Event;
import com.example.sebsp.kalendreo.model.pojo.EventPOJO;
import com.example.sebsp.kalendreo.utils.Tag;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyEventsActivity extends AbstractLoggedInActivity {

    private List<Event> events = new ArrayList<>();

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

        long now = System.currentTimeMillis();
        Event.getReference(firebaseUser.getUid()).orderByChild("startDate").startAt(now).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> eventIterable = dataSnapshot.getChildren();

                events.clear();
                for (DataSnapshot eventSnap : eventIterable) {
                    Event event = new Event(eventSnap.getValue(EventPOJO.class));
                    events.add(event);
                }
                displayEvents();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(Tag.FIREBASE_ERROR, databaseError.getMessage());
            }
        });
    }


    protected void displayEvents() {

        final ArrayAdapter adapter = new ArrayAdapter<>(MyEventsActivity.this,
                android.R.layout.simple_list_item_1, events);
        eventsList.setAdapter(adapter);

        eventsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String key = events.get(i).getId();
                Intent intent = new Intent(MyEventsActivity.this, EventViewActivity.class);
                intent.putExtra(getString(R.string.EXTRA_EVENT_ID), key);
                startActivity(intent);
            }
        });

    }

}

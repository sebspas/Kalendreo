package com.example.sebsp.kalendreo.components.listeners;

import android.util.Log;

import com.example.sebsp.kalendreo.model.Event;
import com.example.sebsp.kalendreo.model.pojo.EventPOJO;
import com.example.sebsp.kalendreo.structure.AbstractMultipleEventsActivity;
import com.example.sebsp.kalendreo.utils.Tag;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gaetan on 17/11/2017.
 * Multiple Event Loaded Listener
 */
public class MultipleEventsLoadedListener implements ValueEventListener {

    private final AbstractMultipleEventsActivity activity;

    public MultipleEventsLoadedListener(AbstractMultipleEventsActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        List<Event> events = new ArrayList<>();

        Iterable<DataSnapshot> eventIterable = dataSnapshot.getChildren();
        for (DataSnapshot eventSnap : eventIterable) {
            Event event = new Event(eventSnap.getValue(EventPOJO.class));
            events.add(event);
        }

        activity.onEventsLoaded(events);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.e(Tag.FIREBASE_ERROR, databaseError.toString());
        activity.errorGettingEvent();
    }
}

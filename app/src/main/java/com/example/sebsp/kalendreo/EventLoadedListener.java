package com.example.sebsp.kalendreo;

import android.util.Log;

import com.example.sebsp.kalendreo.model.Event;
import com.example.sebsp.kalendreo.model.pojo.EventPOJO;
import com.example.sebsp.kalendreo.utils.Tag;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Gaetan on 17/11/2017.
 * Event Loaded Listener
 */
public class EventLoadedListener implements ValueEventListener {

    private final AbstractEventActivity activity;

    public EventLoadedListener(AbstractEventActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Event event = new Event(dataSnapshot.getValue(EventPOJO.class));
        activity.onEventLoaded(event);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.e(Tag.FIREBASE_ERROR, databaseError.toString());
        activity.errorGettingEvent();
    }
}

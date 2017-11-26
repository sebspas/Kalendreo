package com.example.sebsp.kalendreo.social;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sebsp.kalendreo.MainActivity;
import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.components.adapters.EventsAdapter;
import com.example.sebsp.kalendreo.model.Event;
import com.example.sebsp.kalendreo.model.User;
import com.example.sebsp.kalendreo.structure.AbstractMultipleEventsActivity;
import com.example.sebsp.kalendreo.utils.DateFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Gaetan on 26/11/2017.
 * Display the events of a given friend
 */
public class FriendEvents extends AbstractMultipleEventsActivity {

    ListView eventsList;
    TextView friendName;
    private String facebookUserId;
    private TextView noEventsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_events);
        eventsList = findViewById(R.id.friend_events_list_view);
        friendName = findViewById(R.id.friend_name);
        noEventsView = findViewById(R.id.no_events);

        facebookUserId = getStringFromIntent(R.string.EXTRA_FACEBOOK_USER_ID);
        if (facebookUserId == null) launchAndClose(new Intent(this, MainActivity.class));
        this.friendName.setText(getStringFromIntent(R.string.EXTRA_FACEBOOK_USER_NAME));
    }

    @Override
    protected void loadEvents() {

        // First we have to get the firebaseUser Uid of the friend
        User.getReference().orderByChild("facebookId").equalTo(facebookUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot == null || dataSnapshot.getChildren() == null) {
                    displayNoEvents();
                    return;
                }

                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    String uid = (String) user.child("id").getValue();
                    Event.getReference(uid).orderByChild("startDate")
                            .startAt(DateFormatter.midnight(Calendar.getInstance()).getTimeInMillis())
                            .addValueEventListener(listener);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                FriendEvents.super.errorGettingEvent();
            }
        });
    }

    @Override
    public void onEventsLoaded(List<Event> eventsLoaded) {
        if (eventsLoaded.isEmpty()) {
            displayNoEvents();
        } else {
            super.onEventsLoaded(eventsLoaded);
            noEventsView.setVisibility(View.INVISIBLE);
            displayListView(eventsList);
        }
    }

    private void displayNoEvents() {
        events.clear();
        eventsList.setAdapter(null);
        noEventsView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void displayListView(ListView eventsList) {
        final ArrayAdapter adapter = new FriendEventsAdapter(this, events);
        eventsList.setAdapter(adapter);
        eventsList.setVisibility(View.VISIBLE);
    }

    /**
     * Same events adapter than usual lists, but disable the clicks
     */
    private class FriendEventsAdapter extends EventsAdapter {
        private FriendEventsAdapter(@NonNull Context context, @NonNull List<Event> events) {
            super(context, events);
            enableClick = false;
        }

    }
}

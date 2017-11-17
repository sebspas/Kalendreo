package com.example.sebsp.kalendreo.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.sebsp.kalendreo.AbstractLoggedInActivity;
import com.example.sebsp.kalendreo.MainActivity;
import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.components.EventViewGroup;
import com.example.sebsp.kalendreo.model.Event;
import com.example.sebsp.kalendreo.model.pojo.EventPOJO;
import com.example.sebsp.kalendreo.utils.Tag;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditEventActivity extends AbstractLoggedInActivity {

    LinearLayout linearLayoutContainer;
    private EventViewGroup eventViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        linearLayoutContainer = findViewById(R.id.create_event_container);

        String idEvent = getStringFromIntent(R.string.EXTRA_EVENT_ID);
        if (idEvent == null) {
            this.errorGettingEvent();
        }

        // TODO: Display progress bar here
        displayEditForm(idEvent);
    }

    /**
     * Display the EventViewGroup form
     *
     * @param idEvent child Event id
     */
    private void displayEditForm(String idEvent) {
        FirebaseDatabase.getInstance().getReference().child(Event.getTableName()).child(idEvent).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Event event = new Event(dataSnapshot.getValue(EventPOJO.class));
                eventViewGroup = new EventViewGroup(EditEventActivity.this, event, EditEventActivity.this);
                EditEventActivity.this.addViewInContainer(eventViewGroup);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(Tag.FIREBASE_ERROR, databaseError.toString());
                errorGettingEvent();
            }
        });
    }

    /**
     * Add the eventViewGroup loaded with the given event
     *
     * @param eventViewGroup view to add
     */
    private void addViewInContainer(EventViewGroup eventViewGroup) {
        // TODO Hide progressbar here
        linearLayoutContainer.addView(eventViewGroup);
    }

    /**
     * End this view in case of error
     */
    private void errorGettingEvent() {
        Log.e(Tag.INTENT_ERROR, "Could not get id event from intent");
        launchAndClose(new Intent(this, MainActivity.class));
    }
}

package com.example.sebsp.kalendreo.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sebsp.kalendreo.AbstractLoggedInActivity;
import com.example.sebsp.kalendreo.MainActivity;
import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.components.EventViewGroup;
import com.example.sebsp.kalendreo.model.AllEvents;
import com.example.sebsp.kalendreo.model.Event;
import com.example.sebsp.kalendreo.model.pojo.EventPOJO;
import com.example.sebsp.kalendreo.utils.ReminderManager;
import com.example.sebsp.kalendreo.utils.Tag;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateEventActivity extends AbstractLoggedInActivity {

    LinearLayout linearLayoutContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        linearLayoutContainer = findViewById(R.id.create_event_container);

        // Create & add the new event view group
        linearLayoutContainer.addView(new EventViewGroup(this, new Event(), this));
    }
}

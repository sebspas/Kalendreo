package com.example.sebsp.kalendreo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.sebsp.kalendreo.calendar.CalendarViewActivity;
import com.example.sebsp.kalendreo.calendar.CreateEventActivity;
import com.example.sebsp.kalendreo.calendar.MyEventsActivity;
import com.example.sebsp.kalendreo.model.Event;
import com.example.sebsp.kalendreo.social.FriendsListActivity;
import com.example.sebsp.kalendreo.utils.ReminderManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AbstractLoggedInActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button createEvent = findViewById(R.id.CreateAnEvent);
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateEventActivity.class));
            }
        });

        Button viewMyEvent = findViewById(R.id.MyEvents);
        viewMyEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MyEventsActivity.class));
            }
        });

        Button viewCalendar = findViewById(R.id.ViewCalendar);
        viewCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CalendarViewActivity.class));
            }
        });

        Button logOut = findViewById(R.id.LogOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disconnect();
            }
        });

        Button seeFriends = findViewById(R.id.show_friends);
        seeFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FriendsListActivity.class));
            }
        });
    }

    /**
     * Create the notification for all the event depending on the categorie
     */


    public void createAlarmForNotificationForEvent() {
        //TODO change this to get the event during the splashScreen
        // temporary get all the event from the db again
        final ArrayList<Event> listOfEvents = new ArrayList<>();

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
                            (String)eventMap.get("endHour"),
                            (String)eventMap.get("categorie"));

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

        for (Event e: listOfEvents
             ) {
            // for each event of the user we create a new reminder
            ReminderManager.createAReminder(this, e);
        }
    }



    @Override
    public void onStop() {
        super.onStop();
        //mAuth.signOut();
    }
}
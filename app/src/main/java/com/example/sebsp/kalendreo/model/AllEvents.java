package com.example.sebsp.kalendreo.model;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class AllEvents {

    private static final AllEvents ourInstance = new AllEvents();

    public static AllEvents getInstance() {
        return ourInstance;
    }

    public ArrayList<Event> listOfEvents = new ArrayList<>();

    public ArrayList<String> listOfEventsFirebaseKey = new ArrayList<>();

    private AllEvents() {
        createEventList();
    }

    protected void createEventList(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("events")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        ref.orderByChild("dateDeb").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listOfEvents.clear();
                listOfEventsFirebaseKey.clear();

                Iterable<DataSnapshot> eventIterable = dataSnapshot.getChildren();

                for(DataSnapshot eventSnap : eventIterable) {

                    //Log.d("calendar::", eventSnap.getKey());

                    Map eventMap = (Map) eventSnap.getValue();

                    Event event = new Event((String)eventMap.get("title"),
                            (String)eventMap.get("dateDeb"),
                            (String)eventMap.get("dateFin"),
                            (String)eventMap.get("startHour"),
                            (String)eventMap.get("endHour"),
                            (String)eventMap.get("categorie"));

                    listOfEvents.add(event);
                    listOfEventsFirebaseKey.add(eventSnap.getKey());
                }

                /*
                String debug = "[ ";
                for (Event e : listOfEvents) {
                    debug += e.title + " : " + e.dateDeb + " ! ";
                }
                debug += "]";
                Log.d("all_events::", debug);
                */
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}

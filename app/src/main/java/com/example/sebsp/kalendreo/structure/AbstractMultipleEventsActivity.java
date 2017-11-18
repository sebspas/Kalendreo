package com.example.sebsp.kalendreo.structure;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sebsp.kalendreo.MainActivity;
import com.example.sebsp.kalendreo.components.listeners.MultipleEventsLoadedListener;
import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.calendar.EventViewActivity;
import com.example.sebsp.kalendreo.model.Event;
import com.example.sebsp.kalendreo.utils.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gaetan on 17/11/2017.
 * Generic multiple events activity : activity where multiple events are involved
 */
public abstract class AbstractMultipleEventsActivity extends AbstractLoggedInActivity {

    protected List<Event> events = new ArrayList<>();

    protected MultipleEventsLoadedListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = new MultipleEventsLoadedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // TODO Display progress
        loadEvents();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Event.getReference(firebaseUser.getUid()).removeEventListener(listener);
    }

    protected abstract void loadEvents();

    /**
     * Called when events are loaded
     *
     * @param events events loaded
     */
    public void onEventsLoaded(List<Event> events) {
        // TODO Hide Progress
        this.events = events;
    }

    /**
     * End this view in case of error
     */
    public void errorGettingEvent() {
        Log.e(Tag.FIREBASE_ERROR, "Error getting the events");
        Toast.makeText(this, getText(R.string.can_not_load_event), Toast.LENGTH_SHORT).show();
        launchAndClose(new Intent(this, MainActivity.class));
    }

    /**
     * Display the events on the given List View
     *
     * @param eventsList List view where events should be displayed
     */
    protected void displayListView(ListView eventsList) {
        // TODO GM: Create proper listview adapter
        final ArrayAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, events);
        eventsList.setAdapter(adapter);

        eventsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String key = events.get(i).getId();
                Intent intent = new Intent(AbstractMultipleEventsActivity.this, EventViewActivity.class);
                intent.putExtra(getString(R.string.EXTRA_EVENT_ID), key);
                startActivity(intent);
            }
        });
        eventsList.setVisibility(View.VISIBLE);
    }
}

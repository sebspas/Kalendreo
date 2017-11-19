package com.example.sebsp.kalendreo.components.listeners;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.calendar.EventViewActivity;
import com.example.sebsp.kalendreo.model.Event;

/**
 * Created by Gaetan on 19/11/2017.
 * Open the event view on click
 */
public class EventOnClickListener implements OnClickListener {

    private final Event event;

    public EventOnClickListener(Event event) {
        this.event = event;
    }

    @Override
    public void onClick(View v) {
        String key = event.getId();
        Intent intent = new Intent(v.getContext(), EventViewActivity.class);
        intent.putExtra(v.getContext().getString(R.string.EXTRA_EVENT_ID), key);
        v.getContext().startActivity(intent);
    }
}

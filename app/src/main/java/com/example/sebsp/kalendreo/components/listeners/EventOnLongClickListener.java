package com.example.sebsp.kalendreo.components.listeners;

import android.content.Intent;
import android.view.View;
import android.view.View.OnLongClickListener;

import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.model.Event;
import com.example.sebsp.kalendreo.utils.EventFormatter;

/**
 * Created by Gaetan on 28/11/2017.
 * LongClickListener on event
 */
public class EventOnLongClickListener implements OnLongClickListener {

    private final Event event;

    public EventOnLongClickListener(Event event) {
        this.event = event;
    }

    /**
     * Share the event
     *
     * @param v the view pressed onLongClick
     * @return false
     */
    @Override
    public boolean onLongClick(View v) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, EventFormatter.getText(v.getContext(), event));
        v.getContext().startActivity(Intent.createChooser(sharingIntent, v.getContext().getResources().getString(R.string.share_event)));
        return false;
    }
}

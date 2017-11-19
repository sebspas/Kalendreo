package com.example.sebsp.kalendreo.components.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.components.listeners.EventOnClickListener;
import com.example.sebsp.kalendreo.model.Event;
import com.example.sebsp.kalendreo.utils.DateFormatter;

import java.util.List;

/**
 * Created by Gaetan on 19/11/2017.
 * Adapter for list views
 */
public class EventsAdapter extends ArrayAdapter<Event> {


    private final DateFormatter dateFormatter;

    public EventsAdapter(@NonNull Context context, @NonNull List<Event> events) {
        super(context, 0, events);
        dateFormatter = new DateFormatter(context);
    }

    @NonNull
    @Override
    public View getView(int position, View row, @NonNull ViewGroup parent) {

        Event event = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (row == null) {
            row = LayoutInflater.from(getContext()).inflate(R.layout.item_event, parent, false);
        }

        // Lookup view for data population
        TextView tvTitle = row.findViewById(R.id.event_title);
        TextView tvStartDate = row.findViewById(R.id.event_start_date);
        TextView tvEndDate = row.findViewById(R.id.event_end_date);
        TextView tvCategory = row.findViewById(R.id.event_category);

        row.setOnClickListener(new EventOnClickListener(event));

        // Populate the data into the template view using the data object
        if (event != null) {
            tvTitle.setText(event.getTitle());
            tvStartDate.setText(dateFormatter.getFullDate(event.getStartDate()));
            tvEndDate.setText(dateFormatter.getFullDate(event.getEndDate()));
            tvCategory.setText(event.getCategory());
        }
        // Return the completed view to render on screen
        return row;
    }
}

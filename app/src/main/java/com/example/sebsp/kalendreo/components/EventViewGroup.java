package com.example.sebsp.kalendreo.components;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.sebsp.kalendreo.AbstractAppCompatActivity;
import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.calendar.EventViewActivity;
import com.example.sebsp.kalendreo.model.Event;
import com.example.sebsp.kalendreo.model.ModelNotValidException;
import com.example.sebsp.kalendreo.model.User;
import com.example.sebsp.kalendreo.utils.ReminderManager;

/**
 * Created by Gaetan on 15/11/2017.
 * Display a form to edit/create an Event
 */
public class EventViewGroup extends AbstractLinearLayout {

    private Event event;
    private DateTimePickerLinearLayout startDateTime;
    private DateTimePickerLinearLayout endDateTime;
    private TextView titleTV;
    private EditText titleET;
    private TextView categoriesTV;
    private Spinner categoriesSpinner;
    private Button saveButton;
    private User user;

    // ---------------- Constructors

    public EventViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EventViewGroup(Context context, Event event, AbstractAppCompatActivity activity) {
        super(context);
        this.user = new User(activity.getFirebaseUser());
        this.currentActivity = activity;
        init(event);
    }

    // ---------------- Methods

    protected void init() {
        init(new Event());
    }

    /**
     * Build the EventView layout
     *
     * @param event event assigned to the EventViewGroup
     */
    protected void init(Event event) {

        this.event = event;
        this.setOrientation(LinearLayout.VERTICAL);
        this.setLayoutParams(getDefaultLayoutParams());
        this.setId(generateViewId());

        this.constructLayouts();

        this.constructViews();

        this.addViews();
    }

    /**
     * Create & construct the sub layouts (Datetime pickers)
     */
    private void constructLayouts() {

        startDateTime = new DateTimePickerLinearLayout(
                getContext(),
                event.getStartDate(),
                getResources().getString(R.string.start_date_time));

        endDateTime = new DateTimePickerLinearLayout(
                getContext(),
                event.getEndDate(),
                getResources().getString(R.string.end_date_time));
    }

    /**
     * Create & construct the views
     */
    private void constructViews() {
        // Event Title
        titleTV = new TextView(getContext());
        titleTV.setLayoutParams(getDefaultLayoutParams());
        titleTV.setTextColor(getResources().getColor(R.color.colorAccent));
        titleTV.setText(getResources().getText(R.string.EventTitle));

        titleET = new EditText(getContext());
        titleET.setLayoutParams(getDefaultLayoutParams());
        titleET.setEms(10);
        titleET.setInputType(InputType.TYPE_CLASS_TEXT);
        titleET.setTextColor(getResources().getColor(R.color.colorTextInput));
        titleET.setHintTextColor(getResources().getColor(R.color.colorTextInput));
        titleET.setHint(getResources().getText(R.string.EventTitle));
        titleET.setText(event.getTitle());

        // Categories Spinner
        categoriesTV = new TextView(getContext());
        categoriesTV.setLayoutParams(getDefaultLayoutParams());
        categoriesTV.setTextColor(getResources().getColor(R.color.colorAccent));
        categoriesTV.setText(getResources().getText(R.string.select_category));

        constructSpinner();

        // Save Button
        saveButton = new Button(getContext());
        this.setAllPaddingsInDp(saveButton, 16);
        saveButton.setText(getResources().getText(R.string.save));
        saveButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean eventCreated = EventViewGroup.this.saveEvent();
                if (eventCreated) {
                    Intent intent = new Intent(getContext(), EventViewActivity.class);
                    intent.putExtra(getContext().getString(R.string.EXTRA_EVENT_ID), event.getId());
                    getContext().startActivity(intent);
                    currentActivity.finish();
                }
            }
        });
    }

    /**
     * Build the spinner and set automatically the category selected if an already existing event is given
     */
    private void constructSpinner() {
        categoriesSpinner = new Spinner(getContext());
        CharSequence[] categoriesArray = getResources().getTextArray(R.array.event_categories);

        // Get the index of the category of the event
        int selectedIndex = 0;
        for (int i = 0; i < categoriesArray.length; i++) {
            if (categoriesArray[i].toString().equals(event.getCategory())) {
                selectedIndex = i;
            }
        }

        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, categoriesArray);
        categoriesSpinner.setAdapter(spinnerAdapter);
        this.setAllPaddingsInDp(categoriesSpinner, 16);
        categoriesSpinner.setSelection(selectedIndex);
    }

    /**
     * Add all the views to this layout
     */
    private void addViews() {
        this.addView(titleTV);
        this.addView(titleET);
        this.addView(startDateTime);
        this.addView(endDateTime);
        this.addView(categoriesTV);
        this.addView(categoriesSpinner);
        this.addView(saveButton);
    }

    /**
     * Fill the events with the fields of the form and save it with firebase
     *
     * @return true if event is saved, false if a field is invalid and the event is not saved
     */
    private boolean saveEvent() {
        event.setStartDate(EventViewGroup.this.startDateTime.getDate());
        event.setEndDate(EventViewGroup.this.endDateTime.getDate());
        event.setTitle(EventViewGroup.this.titleET.getText().toString());
        event.setCategory(categoriesSpinner.getSelectedItem().toString());
        event.setUser(this.user.getId());
        try {
            event.save();
            ReminderManager.createAReminder(currentActivity, event);
        } catch (ModelNotValidException e) {
            e.displayMessage(getContext());
            return false;
        }
        return true;
    }
}

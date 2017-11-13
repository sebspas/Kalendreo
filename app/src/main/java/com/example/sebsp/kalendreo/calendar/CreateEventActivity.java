package com.example.sebsp.kalendreo.calendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sebsp.kalendreo.AbstractLoggedInActivity;
import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.model.AllEvents;
import com.example.sebsp.kalendreo.model.Event;
import com.example.sebsp.kalendreo.utils.ReminderManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateEventActivity extends AbstractLoggedInActivity {
    // Component to select the begining
    private EditText startDatePicker, startTimePicker, titleEvent;

    // Component to select the endding
    private EditText endDatePicker, endTimePicker;

    private Spinner listCategorie;

    // date de début de l'event à créer
    private int startYear, startMonth, startDay;
    private int startHour, startMin;

    // date de fin de l'event à créer
    private int endYear, endMonth, endDay;
    private int endHour, endMin;

    // bool to say if we update
    private boolean update = false;

    private String cleanTime(String time) {
        String[] timeSplit = time.split(":");
        String hour = timeSplit[0];
        String min = timeSplit[1];

        String ret = "";

        if (hour.length() == 1) {
            ret += "0" + hour;
        } else {
            ret += hour;
        }

        if (min.length() == 1) {
            ret += ":0" + min;
        } else {
            ret += ":" + min;
        }

        return ret;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        startDatePicker = findViewById(R.id.SelectStartDate);
        startTimePicker = findViewById(R.id.SelectStartHours);

        endDatePicker = findViewById(R.id.SelectEndDate);
        endTimePicker = findViewById(R.id.SelectEndHours);

        titleEvent = findViewById(R.id.eventTitle);

        Button create = findViewById(R.id.CreateEvent);

        //setup the categorie list
        listCategorie = findViewById(R.id.ListCategorie);
        //TODO change this to get the categorie from a proper file or database
        ArrayList<String> arraySpinner = new ArrayList<>();
        arraySpinner.add("Fun");
        arraySpinner.add("Serious");
        arraySpinner.add("Work");
        arraySpinner.add("Very Important");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, arraySpinner);
        listCategorie.setAdapter(adapter);

        //To show current date in the datepicker
        Calendar mcurrentDate=Calendar.getInstance();
        startYear = endYear = mcurrentDate.get(Calendar.YEAR);
        startMonth = endMonth = mcurrentDate.get(Calendar.MONTH);
        startDay = endDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        // To show the current time in the timepicker
        Calendar mcurrentTime = Calendar.getInstance();
        startHour = endHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        startMin = endMin = mcurrentTime.get(Calendar.MINUTE);
        setTitle("Create a new Event");
        // if we come from EventView
        if(getIntent().getStringExtra("EventId") != null) {
            // get the event data from the db
            Event currEvent = AllEvents.getInstance().listOfEvents.get(
                    Integer.parseInt(getIntent().getStringExtra("EventId")));

            // then we set the value of the different field
            startDatePicker.setText(currEvent.dateDeb);
            startTimePicker.setText(currEvent.startHour);
            endDatePicker.setText(currEvent.dateFin);
            endTimePicker.setText(currEvent.endHour);
            titleEvent.setText(currEvent.title);
            listCategorie.setSelection(arraySpinner.indexOf(currEvent.categorie));
            create.setText("Edit event");


            String[] splitStartDate = currEvent.dateDeb.split("/");
            startYear = Integer.parseInt(splitStartDate[0]);
            startMonth = Integer.parseInt(splitStartDate[1])-1;
            startDay = Integer.parseInt(splitStartDate[2]);

            String[] splitEndDate = currEvent.dateFin.split("/");
            endYear = Integer.parseInt(splitEndDate[0]);
            endMonth = Integer.parseInt(splitEndDate[1])-1;
            endDay = Integer.parseInt(splitEndDate[2]);

            String[] splitStartTime = currEvent.startHour.split(":");
            startHour = Integer.parseInt(splitStartTime[0]);
            startMin = Integer.parseInt(splitStartTime[1]);

            String[] splitEndTime = currEvent.endHour.split(":");
            endHour = Integer.parseInt(splitEndTime[0]);
            endMin = Integer.parseInt(splitEndTime[1]);

            // we say we are updating not creating (used in the database part)
            update = true;

            setTitle("Edit - " + currEvent.title);
        }

        startDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePicker = new DatePickerDialog(CreateEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // we change the value of the day of deb of the event
                        startYear = selectedyear;
                        startMonth = selectedmonth;
                        startDay =selectedday;

                        startDatePicker.setText(selectedyear + "/" + (startMonth+1) + "/" + selectedday);
                    }
                }, startYear, startMonth, startDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });

        startTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMin) {
                        startHour = selectedHour;
                        startMin = selectedMin;

                        startTimePicker.setText(cleanTime(selectedHour + ":" + selectedMin));
                    }
                }, startHour, startMin, true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        endDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePicker = new DatePickerDialog(CreateEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // we change the value of the day of deb of the event
                        endYear = selectedyear;
                        endMonth = selectedmonth;
                        endDay =selectedday;

                        endDatePicker.setText(endYear  + "/" + (endMonth+1) + "/" + endDay);
                    }
                }, endYear, endMonth, endDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });

        endTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMin) {
                        endHour = selectedHour;
                        endMin = selectedMin;

                        endTimePicker.setText(cleanTime(selectedHour + ":" + selectedMin));
                    }
                }, endHour, endMin, true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titre = titleEvent.getText().toString();
                if (TextUtils.isEmpty(titre)) {
                    Toast.makeText(getApplicationContext(), "Enter title for the event!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String dateDeb = startDatePicker.getText().toString();
                if (TextUtils.isEmpty(dateDeb)) {
                    Toast.makeText(getApplicationContext(), "Enter a start date!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String dateEnd = endDatePicker.getText().toString();
                if (TextUtils.isEmpty(dateEnd)) {
                    Toast.makeText(getApplicationContext(), "Enter a end date!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String startTime = startTimePicker.getText().toString();
                if (TextUtils.isEmpty(startTime)) {
                    Toast.makeText(getApplicationContext(), "Enter a valid start time!", Toast.LENGTH_SHORT).show();
                    return;
                }


                String endTime = endTimePicker.getText().toString();
                if (TextUtils.isEmpty(endTime)) {
                    Toast.makeText(getApplicationContext(), "Enter a valid end time!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String categorie = listCategorie.getSelectedItem().toString();

                // we check that the date of begining is before the date of end
                Calendar eventStartDate = Calendar.getInstance();
                eventStartDate.set(startYear, startMonth, startDay, startHour, startMin);
                Calendar eventEndDate = Calendar.getInstance();
                eventEndDate.set(endYear, endMonth, endDay, endHour, endMin);
                if (eventStartDate.getTimeInMillis() >= eventEndDate.getTimeInMillis()) {
                    Toast.makeText(getApplicationContext(), "The start date must be before the end date!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // then if all is ok we create a new Event()
                Event event = new Event(titre, dateDeb, dateEnd, startTime, endTime, categorie);

                // then we add it to the database
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                if (update) {
                    ref.child("events").child(firebaseUser.getUid()).child(
                            AllEvents.getInstance().listOfEventsFirebaseKey
                                    .get(Integer.parseInt(getIntent().getStringExtra("EventId"))))
                    .setValue(event);
                } else {
                    ref.child("events").child(firebaseUser.getUid()).push().setValue(event);
                }


                Toast.makeText(getApplicationContext(), "Event Created!", Toast.LENGTH_SHORT).show();

                // add a reminder for this event
                ReminderManager.createAReminder(CreateEventActivity.this, event);

                // then we go back to the previous mainPage
                startActivity(new Intent(CreateEventActivity.this, CalendarViewActivity.class));
                finish();
            }
        });

    }
}

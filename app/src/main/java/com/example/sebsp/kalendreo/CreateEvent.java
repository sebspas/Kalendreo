package com.example.sebsp.kalendreo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import com.example.sebsp.kalendreo.model.Event;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class CreateEvent extends LoggedInAppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        startDatePicker = findViewById(R.id.SelectStartDate);
        startTimePicker = findViewById(R.id.SelectStartHours);

        endDatePicker = findViewById(R.id.SelectEndDate);
        endTimePicker = findViewById(R.id.SelectEndHours);

        titleEvent = findViewById(R.id.eventTitle);

        //To show current date in the datepicker
        Calendar mcurrentDate=Calendar.getInstance();
        startYear = endYear = mcurrentDate.get(Calendar.YEAR);
        startMonth = endMonth = mcurrentDate.get(Calendar.MONTH);
        startDay = endDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        // To show the current time in the timepicker
        Calendar mcurrentTime = Calendar.getInstance();
        startHour = endHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        startMin = endMin = mcurrentTime.get(Calendar.MINUTE);

        //setup the categorie list
        listCategorie = findViewById(R.id.ListCategorie);
        //TODO change this to get the categorie from a proper file or database
        String[] arraySpinner = new String[] {
                "Fun", "Sérieux", "Travail", "Ultra Important"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        listCategorie.setAdapter(adapter);

        startDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePicker = new DatePickerDialog(CreateEvent.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // we change the value of the day of deb of the event
                        startYear = selectedyear;
                        startMonth = selectedmonth+1;
                        startDay =selectedday;

                        startDatePicker.setText(selectedyear + "/" + startMonth + "/" + selectedday);
                    }
                }, startYear, startMonth, startDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });

        startTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMin) {
                        startHour = selectedHour;
                        startMin = selectedMin;

                        startTimePicker.setText(selectedHour + ":" + selectedMin);
                    }
                }, startHour, startMin, true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        endDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePicker = new DatePickerDialog(CreateEvent.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // we change the value of the day of deb of the event
                        endYear = selectedyear;
                        endMonth = selectedmonth+1;
                        endDay =selectedday;

                        endDatePicker.setText(endYear  + "/" + endMonth + "/" + endDay);
                    }
                }, endYear, endMonth, endDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });

        endTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMin) {
                        endHour = selectedHour;
                        endMin = selectedMin;

                        endTimePicker.setText(selectedHour + ":" + selectedMin);
                    }
                }, endHour, endMin, true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });


        // create the event inside the db
        Button create = findViewById(R.id.CreateEvent);

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

                String endTime = startTimePicker.getText().toString();
                if (TextUtils.isEmpty(endTime)) {
                    Toast.makeText(getApplicationContext(), "Enter a valid start time!", Toast.LENGTH_SHORT).show();
                    return;
                }


                // then if all is ok we create a new Event()
                Event event = new Event(titre, dateDeb, dateEnd, startTime, endTime);
                // then we add it to the database
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                ref.child("events").child(user.getUid()).push().setValue(event);

                Toast.makeText(getApplicationContext(), "Event Created!", Toast.LENGTH_SHORT).show();

                // then we go back to the previous page
                finish();
            }
        });

    }
}

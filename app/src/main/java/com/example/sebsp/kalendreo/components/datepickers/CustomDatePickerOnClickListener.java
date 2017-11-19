package com.example.sebsp.kalendreo.components.datepickers;

import android.app.DatePickerDialog;
import android.view.View;

import java.util.Calendar;

/**
 * Created by Gaetan on 14/11/2017.
 * Define an On Click Listener for the CustomDatePicker
 */
public class CustomDatePickerOnClickListener implements View.OnClickListener {

    private IDatePicker datePicker;
    private DatePickerDialog.OnDateSetListener dialogListener;

    CustomDatePickerOnClickListener(IDatePicker datePicker, DatePickerDialog.OnDateSetListener dialogListener) {
        this.datePicker = datePicker;
        this.dialogListener = dialogListener;
    }

    @Override
    public void onClick(View v) {
        int year = datePicker.getDate().get(Calendar.YEAR);
        int month = datePicker.getDate().get(Calendar.MONTH);
        int day = datePicker.getDate().get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(datePicker.getContext(), dialogListener, year, month, day);
//        datePickerDialog.setTitle("Select a date");
        datePickerDialog.show();
    }
}

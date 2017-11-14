package com.example.sebsp.kalendreo.components;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Gaetan on 14/11/2017.
 * Define an On Click Listener for the CustomDatePicker
 */
public class CustomTimePickerOnClickListener implements View.OnClickListener {

    private IDatePicker datePicker;
    private TimePickerDialog.OnTimeSetListener dialogListener;

    CustomTimePickerOnClickListener(IDatePicker datePicker, TimePickerDialog.OnTimeSetListener dialogListener) {
        this.datePicker = datePicker;
        this.dialogListener = dialogListener;
    }

    @Override
    public void onClick(View v) {
        int hour = datePicker.getDate().get(Calendar.HOUR_OF_DAY);
        int minute = datePicker.getDate().get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                datePicker.getContext(), dialogListener , hour, minute, true);
//        timePickerDialog.setTitle("Select a time");
        timePickerDialog.show();
    }
}

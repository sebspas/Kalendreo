package com.example.sebsp.kalendreo.components;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

import java.util.GregorianCalendar;

/**
 * Created by Gaetan on 14/11/2017.
 * Custom Date Picker Dialog associated to a CustomDatePicker view
 */
public class CustomDatePickerDialogOnDateListener implements DatePickerDialog.OnDateSetListener {

    private final IDatePicker view;

    CustomDatePickerDialogOnDateListener(IDatePicker view) {
        this.view = view;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (this.view != null) {
            this.view.setDate(new GregorianCalendar(year, month, dayOfMonth));
        }
    }
}

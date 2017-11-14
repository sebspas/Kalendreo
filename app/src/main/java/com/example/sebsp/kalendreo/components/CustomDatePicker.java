package com.example.sebsp.kalendreo.components;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CustomDatePicker extends AbstractDateTimePicker {

    //  ----------------- Constructors

    public CustomDatePicker(Context context) {
        super(context);
    }

    public CustomDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomDatePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomDatePicker(Context context, Calendar date) {
        super(context, date);
    }

    //  ----------------- Methods

    @Override
    protected void setListeners() {
        this.setOnClickListener(new CustomDatePickerOnClickListener(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        CustomDatePicker.this.setDate(new GregorianCalendar(year, month, dayOfMonth));
                    }
                }));
    }
}

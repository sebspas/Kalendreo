package com.example.sebsp.kalendreo.components;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;


public class CustomTimePicker extends AbstractDateTimePicker {

    //  ----------------- Constructors

    public CustomTimePicker(Context context) {
        super(context);
    }

    public CustomTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTimePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //  ----------------- Methods
    @Override
    protected void setListeners() {
        this.setOnClickListener(new CustomTimePickerOnClickListener(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar time = new GregorianCalendar();
                        time.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        time.set(Calendar.MINUTE, minute);
                        CustomTimePicker.this.setDate(time);
                    }
                }));
    }

    /**
     * Set the Format for the picker
     */
    @Override
    protected void setSdf() {
        this.sdf = new SimpleDateFormat("HH:mm", getResources().getConfiguration().locale);
    }


}

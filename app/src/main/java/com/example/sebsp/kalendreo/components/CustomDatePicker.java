package com.example.sebsp.kalendreo.components;

import android.content.Context;
import android.util.AttributeSet;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CustomDatePicker extends android.support.v7.widget.AppCompatEditText implements IDatePicker {

    //  ----------------- Attributes
    private SimpleDateFormat sdf;
    private Calendar date;

    //  ----------------- Getters & setters
    @Override
    public Calendar getDate() {
        return date;
    }

    @Override
    public void setDate(Calendar date) {
        this.date = date;
        this.setText(this.date);
    }

    //  ----------------- Constructors
    public CustomDatePicker(Context context) {
        this(context, new GregorianCalendar());
    }

    public CustomDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomDatePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomDatePicker(Context context, Calendar date) {
        super(context);
        init(date);
    }

    private void init() {
        this.init(new GregorianCalendar());
    }

    /**
     * Initialize the custom date picker : Set the the default DateFormat according to the device
     * @param date Date
     */
    private void init(Calendar date) {
        this.sdf = new SimpleDateFormat(getDefaultFormat(), getResources().getConfiguration().locale);
        this.setDate(date);
        this.setListeners();
    }

    //  ----------------- Methods

    /**
     * Set the text as the date formatted by this SimpleDateFormat
     *
     * @param date Date to display
     */
    public void setText(Calendar date) {
        this.setText(dateToText(date));
    }

    /**
     * Set the listeners
     */
    private void setListeners() {
        this.setOnClickListener(new CustomDatePickerOnClickListener(this,
                new CustomDatePickerDialogOnDateListener(this)));
    }

    /**
     * Find the date format according to the system
     *
     * @return the proper current Date Format, i.e: "dd/mm/yyyy"
     */
    private String getDefaultFormat() {
        Format dateFormat = android.text.format.DateFormat.getDateFormat(this.getContext());
        return ((SimpleDateFormat) dateFormat).toLocalizedPattern();
    }

    /**
     * Return the string value of the date entered
     *
     * @return String date to local format
     */
    public String getFormattedDate() {
        return this.dateToText(this.date);
    }

    /**
     * Return the string value of the given date
     * formatted by this.sdf
     *
     * @param date the date to format
     * @return String date formatted with the current locale & context
     */
    private String dateToText(Calendar date) {
        return this.sdf.format(date.getTime());
    }
}

package com.example.sebsp.kalendreo.components.datepicker;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;

import com.example.sebsp.kalendreo.R;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Abstract DateTimePicker
 * Created by Gaetan on 14/11/2017.
 */
abstract class AbstractDateTimePicker extends AppCompatEditText implements IDatePicker {
    //  ----------------- Attributes
    protected SimpleDateFormat sdf;
    protected Calendar calendar;

    //  ----------------- Getters & setters
    @Override
    public Calendar getDate() {
        return calendar;
    }

    @Override
    public void setDate(Calendar date) {
        this.calendar = date;
        this.setText(this.calendar);
    }

    //  ----------------- Constructors
    public AbstractDateTimePicker(Context context) {
        this(context, new GregorianCalendar());
    }

    public AbstractDateTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AbstractDateTimePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public AbstractDateTimePicker(Context context, Calendar date) {
        super(context);
        init(date);
    }

    private void init() {
        this.init(new GregorianCalendar());
    }

    /**
     * Initialize the custom calendar picker : Set the the default DateFormat according to the device
     *
     * @param date Date
     */
    private void init(Calendar date) {
        this.setSdf();
        this.setDate(date);
        this.setListeners();
        this.setAttributes();
    }

    /**
     * Set the attributes of the view
     */
    protected void setAttributes() {
        this.setId(ViewGroup.generateViewId());
        this.setCustomLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        this.setEms(10);
        this.setFocusable(false);
        this.setTextColor(getResources().getColor(R.color.colorTextInput));
        this.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
    }

    /**
     * Layout Params
     */
    protected abstract void setCustomLayoutParams(ViewGroup.LayoutParams actualParams);

    /**
     * Set the Format for the picker
     */
    protected void setSdf() {
        // Override me!
        this.sdf = new SimpleDateFormat(getDefaultFormat(), getResources().getConfiguration().locale);
    }

    /**
     * Find the calendar format according to the system
     *
     * @return the proper current Date Format, i.e: "dd/mm/yyyy"
     */
    protected String getDefaultFormat() {
        Format dateFormat = android.text.format.DateFormat.getDateFormat(this.getContext());
        return ((SimpleDateFormat) dateFormat).toLocalizedPattern();
    }

    /**
     * Set the text as the calendar formatted by this SimpleDateFormat
     *
     * @param date Date to display
     */
    public void setText(Calendar date) {
        this.setText(dateToText(date));
    }

    /**
     * Set the listeners
     */
    protected abstract void setListeners();

    /**
     * Return the string value of the calendar entered
     *
     * @return String calendar to local format
     */
    public String getFormattedDate() {
        return this.dateToText(this.calendar);
    }

    /**
     * Return the string value of the given calendar
     * formatted by this.sdf
     *
     * @param date the calendar to format
     * @return String calendar formatted with the current locale & context
     */
    private String dateToText(Calendar date) {
        return this.sdf.format(date.getTime());
    }
}

package com.example.sebsp.kalendreo.components;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sebsp.kalendreo.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Gaetan on 15/11/2017.
 * Linear Layout : Contains a text view, a DatePicker & a TimePicker
 */
public class DateTimePickerLinearLayout extends AbstractLinearLayout {

    private CustomDatePicker datePicker;
    private CustomTimePicker timePicker;

    // --------------- Constructors
    public DateTimePickerLinearLayout(Context context) {
        super(context);
        init();
    }

    public DateTimePickerLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DateTimePickerLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * Redefined constructor
     *
     * @param context Context
     * @param date    Date to init the pickers with
     * @param title   text of the text view before the pickers
     */
    public DateTimePickerLinearLayout(Context context, Calendar date, String title) {
        super(context);
        init(date, title);
    }

    // --------------- Methods

    /**
     * Return the date entered from the pickers
     * Concatenate the time and the date
     *
     * @return the date entered in the pickers
     */
    public Calendar getDate() {
        Calendar date = datePicker.getDate();
        Calendar time = timePicker.getDate();
        date.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
        date.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
        return date;
    }

    protected void init() {
        init(new GregorianCalendar(), getResources().getString(R.string.select_date));
    }

    private void init(Calendar date, String title) {

        // --------- Layout Params
        LayoutParams layoutParams = super.getDefaultLayoutParams();

        // --------- Pickers Layout
        LinearLayout pickersLayout = new LinearLayout(getContext());
        pickersLayout.setLayoutParams(layoutParams);
        pickersLayout.setOrientation(LinearLayout.HORIZONTAL);

        datePicker = new CustomDatePicker(getContext(), date);
        timePicker = new CustomTimePicker(getContext(), date);

        pickersLayout.addView(datePicker);
        pickersLayout.addView(timePicker);

        // --------- Text View
        TextView legend = new TextView(getContext());
        legend.setLayoutParams(layoutParams);
        legend.setTextColor(getResources().getColor(R.color.colorAccent));
        legend.setText(title);

        // --------- This Layout
        this.setOrientation(VERTICAL);
        this.setLayoutParams(layoutParams);
        this.addView(legend);
        this.addView(pickersLayout);
    }

}

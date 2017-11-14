package com.example.sebsp.kalendreo.components;

import android.content.Context;

import java.util.Calendar;

/**
 * Created by Gaetan on 14/11/2017.
 * Interface for Dates Picker
 */
interface IDatePicker {

    Calendar getDate();

    void setDate(Calendar date);

    Context getContext();
}

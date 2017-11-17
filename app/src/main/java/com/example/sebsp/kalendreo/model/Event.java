package com.example.sebsp.kalendreo.model;

import android.util.Log;

import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.model.pojo.EventPOJO;
import com.example.sebsp.kalendreo.utils.Tag;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

public class Event extends Model {

    public static String getTableName() {
        return "events";
    }

    // ---------------- Fields

    private Calendar startDate = new GregorianCalendar();
    private Calendar endDate = new GregorianCalendar();
    private User user;
    private String category = "";
    private String title = "";

    public Event(EventPOJO eventPOJO) {
        this.startDate.setTimeInMillis(eventPOJO.startDate);
        this.endDate.setTimeInMillis(eventPOJO.endDate);
        this.category = eventPOJO.category;
        this.title = eventPOJO.title;
        this.user = new User(eventPOJO.userId);
        this.id = eventPOJO.id;
    }

    // ---------------- Setters & getters

    public void setStartDate(Calendar date) {
        this.startDate = date;
    }

    public void setEndDate(Calendar date) {
        this.endDate = date;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public User getUser() {
        return user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // ----------------- Constructors

    public Event() {
        endDate.add(Calendar.HOUR_OF_DAY, 1); // End Date is one hour later by default
    }

    public Event(String title, Calendar startDate, Calendar endDate, String category, User user) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.user = user;
    }

    // ----------------- Override methods

    @Override
    public void create() {
        this.id = databaseReference.push().getKey();
        databaseReference.child(getTableName()).child(id).setValue(new EventPOJO(this));
        Log.i(Tag.MODEL_EVENT, "event created");
    }

    @Override
    public void update() {
        databaseReference.child(getTableName()).child(id).setValue(new EventPOJO(this));
        Log.i(Tag.MODEL_EVENT, "event updated");

    }

    @Override
    protected void valid() throws ModelNotValidException {
        if (Objects.equals(title, "")) {
            throw new ModelNotValidException(R.string.error_event_title_empty);
        }
        if (startDate.compareTo(endDate) >= 0) {
            throw new ModelNotValidException(R.string.error_event_start_end_time);
        }
    }


    // ----------------------- Others

    @Override
    public String toString() {
        return "\n" + this.title + "\n" + this.startDate.getTime().toString() + " - "
                + this.endDate.getTime().toString();
    }
}

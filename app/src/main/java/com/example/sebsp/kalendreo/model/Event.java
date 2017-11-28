package com.example.sebsp.kalendreo.model;

import android.util.Log;

import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.model.pojo.EventPOJO;
import com.example.sebsp.kalendreo.utils.Tag;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

public class Event extends Model {


    public static String getTableName() {
        return "events";
    }

    public static DatabaseReference getReference(String userId) {
        return databaseReference.child(Event.getTableName()).child(userId);
    }

    // ---------------- Fields

    private Calendar startDate = new GregorianCalendar();
    private Calendar endDate = new GregorianCalendar();
    private String userId;
    private String category = "";
    private String title = "";
    private boolean aPrivate = false;

    public Event(EventPOJO eventPOJO) {
        this.startDate.setTimeInMillis(eventPOJO.startDate);
        this.endDate.setTimeInMillis(eventPOJO.endDate);
        this.category = eventPOJO.category;
        this.title = eventPOJO.title;
        this.userId = eventPOJO.userId;
        this.id = eventPOJO.id;
        this.aPrivate = eventPOJO.aPrivate;
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

    public String getUserId() {
        return userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setUser(String userId) {
        this.userId = userId;
    }

    public boolean isPrivate() {
        return aPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        this.aPrivate = aPrivate;
    }

    // ----------------- Constructors

    public Event() {
        endDate.add(Calendar.HOUR_OF_DAY, 1); // End Date is one hour later by default
    }

    /**
     * Create an event with predefined start date
     *
     * @param starDate time in millis for the start date
     * @see Event()
     */
    public Event(long starDate) {
        this.startDate.setTimeInMillis(starDate);
        this.startDate.set(Calendar.HOUR_OF_DAY, 12); // Midday by default
        this.endDate.setTimeInMillis(starDate);
        this.endDate.set(Calendar.HOUR_OF_DAY, 13); // 1 pm
    }

    // ----------------- Override methods

    @Override
    public void create() {
        this.id = databaseReference.push().getKey();
        Event.getReference(userId).child(id).setValue(new EventPOJO(this));
        Log.i(Tag.MODEL_EVENT, "event created");
    }

    @Override
    public void update() {
        Event.getReference(userId).child(id).setValue(new EventPOJO(this));
        Log.i(Tag.MODEL_EVENT, "event updated");
    }

    @Override
    public void delete() {
        Event.getReference(userId).child(id).removeValue();
        Log.i(Tag.MODEL_EVENT, "event deleted");
    }

    @Override
    protected void valid() throws ModelNotValidException {
        if (Objects.equals(title, "")) {
            throw new ModelNotValidException(R.string.error_event_title_empty);
        }
        if (startDate.after(endDate)) {
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

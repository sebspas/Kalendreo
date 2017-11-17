package com.example.sebsp.kalendreo.model.pojo;

import com.example.sebsp.kalendreo.model.Event;

/**
 * Created by Gaetan on 16/11/2017.
 * Plain Old Java Object for an event
 * Needed to be stored easily in Firebase
 */
public class EventPOJO {

    public String id;
    public String userId;
    public String title;
    public String category;
    public long startDate;
    public long endDate;

    public EventPOJO() {};

    public EventPOJO(Event event) {
        this.title = event.getTitle();
        this.startDate = event.getStartDate().getTimeInMillis();
        this.endDate = event.getEndDate().getTimeInMillis();
        this.id = event.getId();
        this.category = event.getCategory();
        this.userId = event.getUserId();
    }
}

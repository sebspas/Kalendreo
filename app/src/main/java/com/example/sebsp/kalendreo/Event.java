package com.example.sebsp.kalendreo;

public class Event {
    public String title;

    public String dateDeb;
    public String dateFin;

    public String startHour;
    public String endHour;

    public Event(String title, String dateDeb, String dateFin, String startHour, String endHour) {
        this.title = title;
        this.dateDeb = dateDeb;
        this.dateFin = dateFin;
        this.startHour = startHour;
        this.endHour = endHour;
    }
}

package com.example.sebsp.kalendreo.model;

public class Event {
    public String title;

    public String dateDeb;
    public String dateFin;

    public String startHour;
    public String endHour;

    public String categorie;

    public Event(String title, String dateDeb, String dateFin, String startHour, String endHour, String categorie) {
        this.title = title;
        this.dateDeb = dateDeb;
        this.dateFin = dateFin;
        this.startHour = startHour;
        this.endHour = endHour;
        this.categorie = categorie;
    }

    @Override
    public String toString(){
        return "\n" + this.title + "\nstarts on " + this.dateDeb + ", ends on "
                + this.dateFin + "\nfrom " + this.startHour + " to " + this.endHour + "\n";
    }
}

package com.example.sebsp.kalendreo.model;

import java.util.Objects;

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

    // USED TO need to override equals() and hashCode() for the contains() in AllEvents class
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Event)) {
            return false;
        }

        Event e = (Event) o;

        return this.title.equals(e.title)
                && this.dateDeb.equals(e.dateDeb)
                && this.dateFin.equals(e.dateFin)
                && this.startHour.equals(e.startHour)
                && this.endHour.equals(e.endHour);
    }

    // JDK 7 and above
    @Override
    public int hashCode() {
        return Objects.hash(title, dateDeb, dateFin, startHour, endHour, categorie);
    }

    /* Classic way
    //Idea from effective Java : Item 9
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + title.hashCode();
        result = 31 * result + dateDeb.hashCode();
        result = 31 * result + dateFin.hashCode();
        result = 31 * result + startHour.hashCode();
        result = 31 * result + endHour.hashCode();
        result = 31 * result + categorie.hashCode();
        return result;
    }
    */
}

package com.example.fypyau.resisafe;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nick Yau on 2/9/2017.
 */

public class Event implements Serializable{
    private String eventname;
    private String eventdate;
    private String eventlocation;
    private String eventdescription;
    private String eventstartdate;
    private String eventenddate;
    private String eventstarttime;
    private String eventendtime;
    private int eventID;
    private int ruserID;

    public Event() {
    }

    public Event(String name,String eventdate, String eventlocation,
                 String eventdescription, int eventID, String eventstartdate, String eventenddate, String eventstarttime, String eventendtime, int ruserID) {
        this.eventname = name;
        this.eventdate = eventdate;
        this.eventlocation = eventlocation;
        this.eventdescription = eventdescription;
        this.eventID = eventID;
        this.eventstartdate = eventstartdate;
        this.eventenddate = eventenddate;
        this.eventstarttime = eventstarttime;
        this.eventendtime = eventendtime;
        this.ruserID = ruserID;
    }

    public int getRuserID() {
        return ruserID;
    }

    public void setRuserID(int ruserID) {
        this.ruserID = ruserID;
    }

    public String getEventstartdate() {
        return eventstartdate;
    }

    public void setEventstartdate(String eventstartdate) {
        this.eventstartdate = eventstartdate;
    }

    public String getEventenddate() {
        return eventenddate;
    }

    public void setEventenddate(String eventenddate) {
        this.eventenddate = eventenddate;
    }

    public String getEventstarttime() {
        return eventstarttime;
    }

    public void setEventstarttime(String eventstarttime) {
        this.eventstarttime = eventstarttime;
    }

    public String getEventendtime() {
        return eventendtime;
    }

    public void setEventendtime(String eventendtime) {
        this.eventendtime = eventendtime;
    }

    public String getEventName() {
        return eventname;
    }

    public void setEventName(String name) {
        this.eventname = name;
    }

    public int getEventID() {
        return eventID;
    }

    public String getEventIDString() {
        return Integer.toString(eventID);
    }

    public void setEventID(int id) {
        this.eventID = id;
    }

    public String getEventDate() {
        return eventdate;
    }

    public void setEventDate(String eventdate) {
        this.eventdate = eventdate;
    }

    public String getEventLocation() {
        return eventlocation;
    }

    public void setEventLocation(String eventlocation) {
        this.eventlocation = eventlocation;
    }

    public String getEventDescription() {
        return eventdescription;
    }

    public void setEventDescription(String eventdescription) {
        this.eventdescription = eventdescription;
    }
}

package com.example.fypyau.resisafe;

import java.io.Serializable;

/**
 * Created by Nick Yau on 3/10/2017.
 */

public class Qrcode implements Serializable{

    String eventname;
    String eventstartdate;
    String eventenddate;
    String eventstarttime;
    String eventendtime;
    String eventcode;
    String eventlocation;
    String qrcode;

    public Qrcode(String eventname, String eventstartdate, String eventenddate, String eventstarttime, String eventendtime,  String eventcode, String eventlocation, String qrcode) {
        this.eventname = eventname;
        this.eventstartdate = eventstartdate;
        this.eventenddate = eventenddate;
        this.eventstarttime = eventstarttime;
        this.eventendtime = eventendtime;
        this.eventcode = eventcode;
        this.eventlocation = eventlocation;
        this.qrcode = qrcode;
    }

    public Qrcode(){}

    public String getEventenddate() {
        return eventenddate;
    }

    public void setEventenddate(String eventenddate) {
        this.eventenddate = eventenddate;
    }

    public String getEventendtime() {
        return eventendtime;
    }

    public void setEventendtime(String eventendtime) {
        this.eventendtime = eventendtime;
    }

    public String getEventlocation() {
        return eventlocation;
    }

    public void setEventlocation(String eventlocation) {
        this.eventlocation = eventlocation;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getEventstartdate() {
        return eventstartdate;
    }

    public void setEventstartdate(String eventstartdate) {
        this.eventstartdate = eventstartdate;
    }

    public String getEventstarttime() {
        return eventstarttime;
    }

    public void setEventstarttime(String eventstarttime) {
        this.eventstarttime = eventstarttime;
    }

    public String getEventcode() {
        return eventcode;
    }

    public void setEventcode(String eventcode) {
        this.eventcode = eventcode;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }
}

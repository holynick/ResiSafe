package com.example.fypyau.resisafe;

import java.io.Serializable;

/**
 * Created by Nick Yau on 2/25/2017.
 */

public class VisitingData implements Serializable {

    int visitingid;
    String visitingtype;
    String visitingtime;
    String visitingdate;
    String eventcode;
    String vuserid;
    String vusername;
    String vusercarplatenum;
    String qrcode;

    public VisitingData(int visitingid, String visitingtype, String visitingtime, String visitingdate, String eventcode, String vuserid, String vusername, String vusercarplatenum, String qrcode) {
        this.visitingid = visitingid;
        this.visitingtype = visitingtype;
        this.visitingtime = visitingtime;
        this.visitingdate = visitingdate;
        this.eventcode = eventcode;
        this.vuserid = vuserid;
        this.vusername = vusername;
        this.vusercarplatenum = vusercarplatenum;
        this.qrcode = qrcode;
    }

    public VisitingData(){

    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getVusername() {
        return vusername;
    }

    public void setVusername(String vusername) {
        this.vusername = vusername;
    }

    public String getVusercarplatenum() {
        return vusercarplatenum;
    }

    public void setVusercarplatenum(String vusercarplatenum) {
        this.vusercarplatenum = vusercarplatenum;
    }

    public int getVisitingid() {
        return visitingid;
    }

    public void setVisitingid(int visitingid) {
        this.visitingid = visitingid;
    }

    public String getVisitingtype() {
        return visitingtype;
    }

    public void setVisitingtype(String visitingtype) {
        this.visitingtype = visitingtype;
    }

    public String getVisitingtime() {
        return visitingtime;
    }

    public void setVisitingtime(String visitingtime) {
        this.visitingtime = visitingtime;
    }

    public String getVisitingdate() {
        return visitingdate;
    }

    public void setVisitingdate(String visitingdate) {
        this.visitingdate = visitingdate;
    }

    public String getEventcode() {
        return eventcode;
    }

    public void setEventcode(String eventcode) {
        this.eventcode = eventcode;
    }

    public String getVuserid() {
        return vuserid;
    }

    public void setVuserid(String vuserid) {
        this.vuserid = vuserid;
    }
}

package com.example.fypyau.resisafe;

import java.io.Serializable;

/**
 * Created by Nick Yau on 2/18/2017.
 */

public class VisitorUser implements Serializable{

    private String name;
    private int id;
    private String nationality;
    private String carplatenum;
    private String lastcheckindate;
    private String profileimage;


    public VisitorUser(String name, String nationality, String carplatenum, int id, String lastcheckindate, String profileimage) {
        this.name = name;
        this.id = id;
        this.nationality = nationality;
        this.carplatenum = carplatenum;
        this.lastcheckindate = lastcheckindate;
        this.profileimage = profileimage;
    }

    public String getLastcheckindate() {
        return lastcheckindate;
    }

    public void setLastcheckindate(String lastcheckindate) {
        this.lastcheckindate = lastcheckindate;
    }

    public VisitorUser(){

    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarplatenum() {
        return carplatenum;
    }

    public void setCarplatenum(String carplatenum) {
        this.carplatenum = carplatenum;
    }

}

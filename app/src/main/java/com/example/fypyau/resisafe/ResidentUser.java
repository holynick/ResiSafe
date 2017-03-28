package com.example.fypyau.resisafe;

import java.io.Serializable;

/**
 * Created by Nick Yau on 3/5/2017.
 */

public class ResidentUser implements Serializable{

    String name;
    String address;
    String lasteventdate;
    String profileimage;
    int id;

    public ResidentUser(String name, String address, String lasteventdate, String profileimage, int id) {
        this.name = name;
        this.address = address;
        this.lasteventdate = lasteventdate;
        this.profileimage = profileimage;
        this.id = id;
    }

    public ResidentUser(){}

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLasteventdate() {
        return lasteventdate;
    }

    public void setLasteventdate(String lasteventdate) {
        this.lasteventdate = lasteventdate;
    }
}

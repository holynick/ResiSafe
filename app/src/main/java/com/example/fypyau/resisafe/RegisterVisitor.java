package com.example.fypyau.resisafe;

import java.io.Serializable;

/**
 * Created by Nick Yau on 3/8/2017.
 */

public class RegisterVisitor implements Serializable {
    private String fullname;
    private String username;
    private String password;
    private String icnum;
    private String nationality;
    private String carplatenum;
    private String contactnum;

    public RegisterVisitor(String fullname, String username, String password, String icnum, String carplatenum, String nationality, String contactnum) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.icnum = icnum;
        this.carplatenum = carplatenum;
        this.nationality = nationality;
        this.contactnum = contactnum;
    }

    public RegisterVisitor(){}

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIcnum() {
        return icnum;
    }

    public void setIcnum(String icnum) {
        this.icnum = icnum;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCarplatenum() {
        return carplatenum;
    }

    public void setCarplatenum(String carplatenum) {
        this.carplatenum = carplatenum;
    }

    public String getContactnum() {
        return contactnum;
    }

    public void setContactnum(String contactnum) {
        this.contactnum = contactnum;
    }
}

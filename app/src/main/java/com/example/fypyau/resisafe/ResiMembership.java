package com.example.fypyau.resisafe;

import java.io.Serializable;

/**
 * Created by Nick Yau on 3/10/2017.
 */

public class ResiMembership implements Serializable {

    String ruserid;
    String membershipstatus;
    String nextpayment;
    String lastpayment;
    String amount;

    public ResiMembership(String ruserid, String membershipstatus, String nextpayment, String lastpayment, String amount) {
        this.ruserid = ruserid;
        this.membershipstatus = membershipstatus;
        this.nextpayment = nextpayment;
        this.lastpayment = lastpayment;
        this.amount = amount;
    }

    public ResiMembership(){}

    public String getRuserid() {
        return ruserid;
    }

    public void setRuserid(String ruserid) {
        this.ruserid = ruserid;
    }

    public String getMembershipstatus() {
        return membershipstatus;
    }

    public void setMembershipstatus(String membershipstatus) {
        this.membershipstatus = membershipstatus;
    }

    public String getNextpayment() {
        return nextpayment;
    }

    public void setNextpayment(String nextpayment) {
        this.nextpayment = nextpayment;
    }

    public String getLastpayment() {
        return lastpayment;
    }

    public void setLastpayment(String lastpayment) {
        this.lastpayment = lastpayment;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}

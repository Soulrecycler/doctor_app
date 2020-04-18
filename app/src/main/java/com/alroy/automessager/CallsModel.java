package com.alroy.automessager;

public class CallsModel {

    private String number ,date,typeOfCall;
    private int callTypeLogo;

    public CallsModel(String number, String date, String typeOfCall,int callTypeLogo) {
        this.number = number;
        this.date = date;
        this.typeOfCall = typeOfCall;
        this.callTypeLogo = callTypeLogo;

    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTypeOfCall() {
        return typeOfCall;
    }

    public void setTypeOfCall(String typeOfCall) {
        this.typeOfCall = typeOfCall;
    }

    public int getCallTypeLogo() {
        return callTypeLogo;
    }

    public void setCallTypeLogo(int callTypeLogo) {
        this.callTypeLogo = callTypeLogo;
    }
}

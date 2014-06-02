package com.example.userrequests;

public class Spravochnik {

    public int id;
    public String spravochnicValue;
    public String spravochnicType;

    public Spravochnik(){
        this.id = -1;
        this.spravochnicValue = "";
        this.spravochnicType = "";
    }

    public Spravochnik(int id, String spravochnicValue, String spravochnicType) {
        this.id = id;
        this.spravochnicValue = spravochnicValue;
        this.spravochnicType = spravochnicType;
    }
}

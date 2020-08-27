package com.example.intershala.Model;

import android.widget.ProgressBar;

import java.io.Serializable;

public class Userimfo implements Serializable {
    String ClientNumber;
    String Property;
    String Area;
    String OwenerName="N/A";
    String Preferred="N/A";
    public Userimfo(){

    }
    public Userimfo(String a,String b,String c,String d,String e){
        ClientNumber=a;
        Property=b;
        Area=c;
        if(d!=null)
        OwenerName=d;
        if(e!=null)
            Preferred=d;


    }

    public String getClientNumber() {
        return ClientNumber;
    }

    public void setClientNumber(String clientNumber) {
        ClientNumber = clientNumber;
    }

    public String getProperty() {
        return Property;
    }

    public void setProperty(String property) {
        Property = property;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getOwenerName() {
        return OwenerName;
    }

    public void setOwenerName(String owenerName) {
        OwenerName = owenerName;
    }

    public String getPreferred() {
        return Preferred;
    }

    public void setPreferred(String preferred) {
        Preferred = preferred;
    }
}

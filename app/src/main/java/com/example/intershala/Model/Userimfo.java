package com.example.intershala.Model;

import android.widget.ProgressBar;

import java.io.Serializable;

import kotlinx.android.parcel.Parcelize;

public class Userimfo implements Serializable {
    public String clientNumber;
    public String property;
    public String location;
    public String area;
    public String owenerName="N/A";
    public String preferredLanguage="N/A";
    public String userNumber;
    public String status;
    public Userimfo(){

    }
    public Userimfo(String clientNumber,String property,String area,String owenerName,
                    String preferredLanguage,String userNumber,String status,String location){
       this.clientNumber=clientNumber;
        this.property=property;
        this.area=area;
        if(owenerName!=null)
        this.owenerName=owenerName;
        if(preferredLanguage!=null)
            this.preferredLanguage=preferredLanguage;

        this.userNumber=userNumber;
        this.status=status;
        this.location=location;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getOwenerName() {
        return owenerName;
    }

    public void setOwenerName(String owenerName) {
        this.owenerName = owenerName;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

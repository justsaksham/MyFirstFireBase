package com.example.intershala.Model;

import java.io.Serializable;

public class USERFORM implements Serializable {
    public int ApplicationSubmitted;
    public int PendendingApproval;
    public int ApplicationRejected;
    public int ApplicationApproved;
    public int LeadGenerated;
    public USERFORM(){

    }
    public USERFORM(int ApplicationSubmitted,int ApplicationApproved,int PendendingApproval,int ApplicationRejected,int LeadGenerated){
        this.ApplicationApproved=ApplicationApproved;
        this.ApplicationSubmitted=ApplicationSubmitted;
        this.PendendingApproval=PendendingApproval;
        this.ApplicationRejected=ApplicationRejected;
        this.LeadGenerated=LeadGenerated;

    }



}

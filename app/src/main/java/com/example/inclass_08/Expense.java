package com.example.inclass_08;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Expense implements Serializable {
    public String expName,expCat,expAmount,expDate;
    public String obj_key="key";

    public Expense() {
    }
    public Expense(String expName,String expCat,String expAmount,String expDate){
        this.expName = expName;
        this.expCat = expCat;
        this.expAmount = expAmount;
        this.expDate = expDate;
    }


}

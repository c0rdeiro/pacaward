package com.europeia.pacaward;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

    private static final String TAG = "Transaction";
    private String brandName;
    private String logoUrl;
    private String location;
    private String date;
    private String amountSaved;
    private String amountSpent;
    private String currency;

    public Transaction(String brandName, String logoUrl, String location, String date, String amountSpent, String currency) {
        this.brandName = brandName;
        this.logoUrl = logoUrl;
        this.location = location;
        this.date = date;
        this.amountSpent = amountSpent;
        this.currency = currency;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        String dateDate = this.date.substring(0,10);
        String dateTime = this.date.substring(11,16);

        return String.format("%s %s",dateDate,dateTime);
    }

    public String getAmountSpent() {
        return String.format("%s€",amountSpent);
    }

}

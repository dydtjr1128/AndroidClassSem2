package org.androidtown.myapplication;

/**
 * Created by CYSN on 2017-10-10.
 */


import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class Contact extends AppCompatActivity {

    private double longitude;
    private double latitude;
    private String address;

    public Contact(double longitude, double latitude, String adress ) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = adress;
    }

    public void setAddress(String adress){
        this.address = adress;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public double getLatitude(){
        return latitude;
    }

    public String getAddress(){
        return address;
    }
    public static ArrayList<Contact> createContactsList(int numContacts) {
        ArrayList<Contact> contacts = new ArrayList<Contact>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new Contact(0.1, 0.2, "temp"));
        }

        return contacts;
    }

}
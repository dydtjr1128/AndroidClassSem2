package org.androidtown.myapplication;

/**
 * Created by CYSN on 2017-10-12.
 */

public class MyRecyclerItem {
    private double latitude;//위도
    private double longitude;//경도
    private String address;

    public MyRecyclerItem(double latitude, double longitude, String address){
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

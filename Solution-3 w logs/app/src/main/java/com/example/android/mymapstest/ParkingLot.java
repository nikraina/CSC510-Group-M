package com.example.android.mymapstest;

/**
 * Created by krishnaagarwala on 04/03/16.
 */
public class ParkingLot {
    int id;
    String name;
    Double latitude, longitude;
    Integer total,available;

    //Dont need it for the time being
    /*
    public void setId(int id){ this.id = id; }
    public int getId(int id){ return this.id;}
    */

    public void setName(String name){ this.name = name; }
    public String getName(){ return this.name; }

    public void setLatitude(Double latitude){ this.latitude = latitude; }
    public Double getLatitude(){ return this.latitude; }

    public void setLongitude(Double longitude){ this.longitude = longitude; }
    public Double getLongitude(){ return this.longitude; }

    public void setTotal(Integer total){ this.total = total; }
    public Integer getTotal(){
        return this.total;
    }

    public void setAvailable(Integer available){ this.available = available; }
    public Integer getAvailable(){
        return this.available;
    }
}

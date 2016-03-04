package com.example.android.mymaps1;

/**
 * Created by krishnaagarwala on 03/03/16.
 */
public class UserLocLog {
    int id;
    String email;
    Double latitude, longitude;
    Integer frequency;

    //Dont need it for the time being
    /*
    public void setId(int id){ this.id = id; }
    public int getId(int id){ return this.id;}
    */

    public void setEmail(String email){ this.email = email; }
    public String getEmail(){
        return this.email;
    }

    public void setLatitude(Double latitude){
        this.latitude = latitude;
    }
    public Double getLatitude(){
        return this.latitude;
    }

    public void setLongitude(Double longitude){ this.longitude = longitude; }
    public Double getLongitude(){
        return this.longitude;
    }

    public void setFrequency(Integer frequency){ this.frequency = frequency; }
    public Integer getFrequency(){
        return this.frequency;
    }
}

package com.example.android.mymaps;

/**
 * Created by nikhil on 3/8/16.
 */
public class RegisteredUserVehicle {

    int id;
    String uname, vid;

    public void setUname(String uname){
        this.uname = uname;
    }
    public String getUname(){
        return this.uname;
    }

    public void setVid(String vid){
        this.vid = vid;
    }
    public String getVid(){
        return this.vid;
    }
}

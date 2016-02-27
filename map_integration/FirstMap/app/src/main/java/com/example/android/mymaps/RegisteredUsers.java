package com.example.android.mymaps;

/**
 * Created by nikhil on 2/26/16.
 */
public class RegisteredUsers {

    int id;
    String name, uname, password;

    /*public void setId(int id){                    //Dont need it for the time being
        this.id = id;
    }
    public int getId(int id){
        return this.id;
    }*/

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public void setUname(String uname){
        this.uname = uname;
    }
    public String getUname(){
        return this.uname;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }


}

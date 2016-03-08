package com.example.android.mymaps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nikhil on 2/26/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "users.db";
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_UNAME = "uname";
    private static final String COLUMN_PASSWORD = "password";


    //Database for vehicle registeration
    private static final String TABLE_NAME_UV = "user_vehicle";
    //private static final String COLUMN_ID = "id";         Constant can be reused from above
    private static final String COLUMN_VID = "vid";
    //private static final String COLUMN_UNAME = "uname";       Constant can be reused from above

    SQLiteDatabase db;

    private static final String TABLE_CREATE = "create table users (id integer primary key not null, "
            + "name text not null, uname text not null, password text not null);";
    private static final String TABLE_CREATE_UV = "create table user_vehicle (id integer primary key not null, "
            + "uname text not null, vid text not null);";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void insertUser(RegisteredUsers user){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        int size = cursor.getCount();

        values.put(COLUMN_ID,size);
        values.put(COLUMN_NAME,user.getName());
        values.put(COLUMN_UNAME,user.getUname());
        values.put(COLUMN_PASSWORD,user.getPassword());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public String userAuth(String uname){
        db = this.getReadableDatabase();
        String query = "select uname, password from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        String temp_uname, password;
        password = "null";
        if(cursor.moveToFirst()){
            do {
                temp_uname = cursor.getString(0);
                if(temp_uname.equals(uname)){
                    password = cursor.getString(1);
                }
            }
            while(cursor.moveToNext());
        }
        return password;
    }

    /*public String getName(String username){
        db = this.getReadableDatabase();
        String query = "select name from " + TABLE_NAME + "where uname = username";
        Cursor cursor = db.rawQuery(query,null);

        return cursor.getString(0);
    }*/


    //BEGIN :: methods for user vehicle registeration Db
    public void insertUserVehicle(RegisteredUserVehicle userVehicle){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from " + TABLE_NAME_UV;
        Cursor cursor = db.rawQuery(query,null);
        int size = cursor.getCount();

        values.put(COLUMN_ID,size);
        values.put(COLUMN_UNAME,userVehicle.getUname());
        values.put(COLUMN_VID,userVehicle.getVid());
        db.insert(TABLE_NAME_UV, null, values);
        db.close();
    }

    public String getVid(String username){
        db = this.getReadableDatabase();
        String query = "select uname, vid from " + TABLE_NAME_UV;
        Cursor cursor = db.rawQuery(query,null);
        String vid,temp_uname;
        vid = "null";
        if(cursor.moveToFirst()){
            do {
                temp_uname = cursor.getString(0);
                if(temp_uname.equals(username)){
                    vid = cursor.getString(1);
                }
            }
            while(cursor.moveToNext());
        }
        return vid;
    }

    public void updateUserVehicle(String vid){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select id, uname, vid from " + TABLE_NAME_UV;// + "where uname = ?" + new String[] {Global.username};
        Cursor cursor = db.rawQuery(query,null);
        String temp_uname;
        String index = "-1";
        if(cursor.moveToFirst()){
            do {
                temp_uname = cursor.getString(1);
                if(temp_uname.equals(Global.username)){
                   index = cursor.getString(0);//getLong(cursor.getColumnIndex("_id"));
                }
            }
            while(cursor.moveToNext());
        }

        String strFilter = "id=" + index;
        values.put(COLUMN_ID,index);
        values.put(COLUMN_UNAME,Global.username);
        values.put(COLUMN_VID,vid);
        db.update(TABLE_NAME_UV, values,strFilter, null);
        db.close();
    }
    //END :: methods for user vehicle registeration Db



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_CREATE_UV);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(drop_query);
        String drop_query_uv = "DROP TABLE IF EXISTS " + TABLE_NAME_UV;
        db.execSQL(drop_query_uv);
        this.onCreate(db);
    }
}

package com.example.android.mymapstest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nikhil on 2/26/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "users.db";
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_UNAME = "uname";
    private static final String COLUMN_PASSWORD = "password";

    private static final String TABLE_NAME2 = "parkinglots";
    private static final String COLUMN_ID2 = "id";
    private static final String COLUMN_NAME2 = "name";
    private static final String COLUMN_LATITUDE2 = "latitude";
    private static final String COLUMN_LONGITUDE2 = "longitude";
    private static final String COLUMN_TOTAL2 = "total";
    private static final String COLUMN_AVAILABLE2 = "available";
    //private static final String COLUMN_ATTRIBUTE21 = "attribute1";
    //private static final String COLUMN_ATTRIBUTE22 = "attribute2";


    SQLiteDatabase db;
    private static final String TABLE_CREATE = "create table users (id integer primary key not null, "
            + "name text not null, uname text not null, password text not null);";

    private static final String TABLE_CREATE2 = "create table parkinglots (id integer primary key not null, "
            + "name text not null, latitude real not null, longitude real not null, total int, available int);";

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

    /*public void addLotsOnMap(){
        db = this.getReadableDatabase();
        String query = "select name, latitude, longitude, total, available from " + TABLE_NAME2;
        Cursor cursor = db.rawQuery(query,null);
        String temp_name;
        Double temp_lat, temp_lon;
        Integer temp_total, temp_avl;
        Marker marker;

        if(cursor.moveToFirst()){
            do {
                temp_name = cursor.getString(0);
                temp_lat = cursor.getDouble(1);
                temp_lon = cursor.getDouble(2);
                temp_total = cursor.getInt(3);
                temp_avl = cursor.getInt(4);

                Marker temp =

            }
            while(cursor.moveToNext());
        }
    }*/
    public Cursor getLots() {
        db = this.getReadableDatabase();
        String query = "select name, latitude, longitude, total, available from " + TABLE_NAME2;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public void insertParkingLot(ParkingLot lot){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from " + TABLE_NAME2;
        Cursor cursor = db.rawQuery(query,null);
        int size = cursor.getCount();

        values.put(COLUMN_ID2,size);
        values.put(COLUMN_NAME2, lot.getName());
        values.put(COLUMN_LATITUDE2, lot.getLatitude());
        values.put(COLUMN_LONGITUDE2, lot.getLongitude());
        values.put(COLUMN_TOTAL2, lot.getTotal());
        values.put(COLUMN_AVAILABLE2, lot.getAvailable());
        db.insert(TABLE_NAME2, null, values);

        cursor.close();
        db.close();
    }

    public void insertParkingLot2(SQLiteDatabase dbpass, String name, Double lat, Double lon, Integer total, Integer available){
        //db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from " + TABLE_NAME2;
        Cursor cursor = dbpass.rawQuery(query,null);
        int size = cursor.getCount();

        values.put(COLUMN_ID2,size);
        values.put(COLUMN_NAME2, name);
        values.put(COLUMN_LATITUDE2, lat);
        values.put(COLUMN_LONGITUDE2, lon);
        values.put(COLUMN_TOTAL2, total);
        values.put(COLUMN_AVAILABLE2, available);
        dbpass.insert(TABLE_NAME2, null, values);

        cursor.close();
        //db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_CREATE2);
        this.db = db;

        insertParkingLot2(db, "Centennial Deck", 35.769342, -78.673975, 100, 80);
        insertParkingLot2(db, "Dan Allen", 35.787470, -78.675503, 100, 75);
        insertParkingLot2(db, "Varsity Drive", 35.779665, -78.681602, 100, 70);
        insertParkingLot2(db, "Reynolds Coliseum", 35.784475, -78.668372, 100, 75);
        insertParkingLot2(db, "Capability Drive", 35.770630, -78.681957, 100, 75);
        insertParkingLot2(db, "Coliseum Deck", 35.782441,-78.668237, 100, 75);
        insertParkingLot2(db, "Bell Tower", 35.785917, -78.663330, 100, 75);
        insertParkingLot2(db, "Poultan Deck", 35.769582, -78.677181, 100, 75);
        insertParkingLot2(db, "Partner's Way Deck", 35.774125, -78.674249, 100, 75);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(drop_query);
        String drop_query2 = "DROP TABLE IF EXISTS " + TABLE_NAME2;
        db.execSQL(drop_query2);
        this.onCreate(db);
    }
}

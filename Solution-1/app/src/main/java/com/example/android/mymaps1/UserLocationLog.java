package com.example.android.mymaps1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by krishnaagarwala on 03/03/16.
 */
public class UserLocationLog extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "log.db";
    private static final String TABLE_NAME = "userlocationlog";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_FREQUENCY = "frequency";
    SQLiteDatabase db;
    private static final String TABLE_CREATE = "create table userlocationlog (id integer primary key not null, "
            + "email text, latitude real not null, longitude real not null, frequency int);";

    public UserLocationLog(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void insertLog(UserLocLog log){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        int size = cursor.getCount();

        values.put(COLUMN_ID, size);
        values.put(COLUMN_EMAIL,log.getEmail());
        values.put(COLUMN_LATITUDE,log.getLatitude());
        values.put(COLUMN_LONGITUDE,log.getLongitude());
        values.put(COLUMN_FREQUENCY,log.getFrequency());
        db.insert(TABLE_NAME, null, values);
        cursor.close();
        db.close();
    }
    public void insertUpdateLog(UserLocLog log){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_EMAIL,log.getEmail());
        values.put(COLUMN_LATITUDE,log.getLatitude());
        values.put(COLUMN_LONGITUDE,log.getLongitude());

        String query = "select id, latitude, longitude, frequency from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        int size = cursor.getCount();

        Double temp_lat, temp_long;
        Integer temp_freq = 0, temp_id = 0;
        Integer flag = 0;
        if(cursor.moveToFirst()){
            do {
                temp_id = cursor.getInt(0);
                temp_lat = cursor.getDouble(1);
                temp_long = cursor.getDouble(2);
                temp_freq = cursor.getInt(3);
                if((temp_lat.equals(log.getLatitude()))&&(temp_long.equals(log.getLongitude()))) {
                    flag = 1;
                    break;
                }
            }
            while(cursor.moveToNext());
        }
        if (flag==1) {
            values.put(COLUMN_ID, temp_id);
            values.put(COLUMN_FREQUENCY,temp_freq+1);

            db.update(TABLE_NAME, values, "id = ?", new String[] {temp_id.toString()});
        } else {
            values.put(COLUMN_ID, size);
            values.put(COLUMN_FREQUENCY, 1);
            db.insert(TABLE_NAME, null, values);
        }

        cursor.close();
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(drop_query);
        this.onCreate(db);
    }
}

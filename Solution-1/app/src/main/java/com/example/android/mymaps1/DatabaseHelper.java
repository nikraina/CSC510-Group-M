package com.example.android.mymaps1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by krishnaagarwala on 04/03/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "users.db";
    private static final String TABLE_NAME2 = "parkinglots";
    private static final String COLUMN_ID2 = "id";
    private static final String COLUMN_NAME2 = "name";
    private static final String COLUMN_LATITUDE2 = "latitude";
    private static final String COLUMN_LONGITUDE2 = "longitude";
    private static final String COLUMN_TOTAL2 = "total";
    private static final String COLUMN_AVAILABLE2 = "available";

    SQLiteDatabase db;

    private static final String TABLE_CREATE2 = "create table parkinglots (id integer primary key not null, "
            + "name text not null, latitude real not null, longitude real not null, total int, available int);";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }


    public Cursor getLots() {
        db = this.getReadableDatabase();
        String query = "select name, latitude, longitude, total, available from " + TABLE_NAME2;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
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
        String drop_query2 = "DROP TABLE IF EXISTS " + TABLE_NAME2;
        db.execSQL(drop_query2);
        this.onCreate(db);
    }
}

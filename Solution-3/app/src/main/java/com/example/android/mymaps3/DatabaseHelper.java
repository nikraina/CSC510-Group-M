package com.example.android.mymaps3;

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
    SQLiteDatabase db;
    private static final String TABLE_CREATE = "create table users (id integer primary key not null, "
            + "name text not null, uname text not null, password text not null);";

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

    public String getName(String username){
        db = this.getReadableDatabase();
        String query = "select name from " + TABLE_NAME + "where uname = username";
        Cursor cursor = db.rawQuery(query,null);

        return cursor.getString(0);
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

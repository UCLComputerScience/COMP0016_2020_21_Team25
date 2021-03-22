package com.example.fisev2concierge.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AdminDbHelper extends SQLiteOpenHelper {

    public AdminDbHelper(Context context){
        super(context, "Admin", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + "Admin" + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "userID" + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "Admin");
        onCreate(db);
    }

    public boolean addID(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userID", id);
        long result = db.insert("Admin", null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM Admin", null);
        return data;
    }

    public void updateID(String ID, String newID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE Admin SET userID ='" + newID + "' WHERE " + "ID = '" + ID + "'";
        db.execSQL(query);
    }
}

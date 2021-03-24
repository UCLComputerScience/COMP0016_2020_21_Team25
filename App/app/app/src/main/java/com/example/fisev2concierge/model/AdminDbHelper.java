package com.example.fisev2concierge.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        if (hasUserID()){
            updateID(getIdOfUserId(), id);
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("userID", id);
            long result = db.insert("Admin", null, contentValues);
            return result != -1;
        }
        return true;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM Admin", null);
    }

    public void updateID(String ID, String newID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE Admin SET userID ='" + newID + "' WHERE " + "ID = '" + ID + "'";
        db.execSQL(query);
    }

    public boolean hasUserID(){
        SQLiteDatabase db = this.getWritableDatabase();
        try (Cursor cursor = db.rawQuery("SELECT * FROM Admin", null)) {
            return cursor.getCount() > 0;
        }
    }

    public String getIdOfUserId(){
        SQLiteDatabase db = this.getWritableDatabase();
        try (Cursor data = db.rawQuery("SELECT * FROM Admin", null)) {
            data.moveToFirst();
            return data.getString(0);
        }
    }
}

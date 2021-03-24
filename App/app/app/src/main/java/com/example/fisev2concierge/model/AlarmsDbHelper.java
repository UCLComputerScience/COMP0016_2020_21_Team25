package com.example.fisev2concierge.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlarmsDbHelper extends SQLiteOpenHelper {

    public AlarmsDbHelper(Context context){
        super(context, "Alarms", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + "Alarms" + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "Message" + " TEXT, " + "Date" + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "Alarms");
        onCreate(db);
    }

    public Cursor getRecent(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM Alarms ORDER BY ID DESC LIMIT 1", null);
    }

    public boolean addData(String message, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Message", message);
        contentValues.put("Date", date);
        long result = db.insert("Alarms", null, contentValues);
        return result != -1;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM Alarms", null);
    }

    public void updateAlarm(int ID, String newAlarm, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE Alarms SET Message ='" + newAlarm + "', Date ='" + date +  "' WHERE " + "ID = '" + ID + "'";
        db.execSQL(query);
    }

    public void deleteAlarm(int ID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM Alarms WHERE ID = '" + ID + "'";
        db.execSQL(query);
    }
}

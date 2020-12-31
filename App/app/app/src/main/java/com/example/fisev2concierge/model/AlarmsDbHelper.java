package com.example.fisev2concierge.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
        Cursor data = db.rawQuery("SELECT * FROM Alarms ORDER BY ID DESC LIMIT 1", null);
        return data;
    }

    public boolean addData(String message, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Message", message);
        contentValues.put("Date", date);
        Log.d("AlarmsDbHelper", "addData: Adding " + message + " " + date + "to " + "Alarms");

        long result = db.insert("Alarms", null, contentValues);

        if (result == -1){
            return false;
        }
        return true;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM Alarms", null);
        return data;
    }
    //may have to change this
    public Cursor getItemID(String getID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + "ID " + "FROM " + "Alarms " + "WHERE ID = '" + getID  + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateAlarm(String alarm, int ID, String newAlarm, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE Alarms SET Message ='" + newAlarm + "', Date ='" + date +  "' WHERE " + "ID = '" + ID + "'";
        Log.d("AlarmsDbHelper", "updateReminder: query " + query);
        Log.d("AlarmsDbHelper", "updateReminder: Setting reminder to " + newAlarm);
        db.execSQL(query);
    }

    public void deleteAlarm(int ID, String alarm){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM Alarms WHERE ID = '" + ID + "'";
        Log.d("AlarmsDbHelper", "deleteAlarm query : " + query);
        Log.d("AlarmsDbHelper", "Deleting " + alarm + " from database");
        db.execSQL(query);
    }
}

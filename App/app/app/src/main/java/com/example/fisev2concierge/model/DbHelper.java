package com.example.fisev2concierge.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context){
        super(context, "Reminders", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + "Reminders" + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "Message" + " TEXT, " + "Date" + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "Reminders");
        onCreate(db);
    }

    public Cursor getRecent(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM Reminders ORDER BY ID DESC LIMIT 1", null);
        return data;
    }

    public boolean addData(String message, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Message", message);
        contentValues.put("Date", date);
        Log.d("DbHelper", "addData: Adding " + message + " " + date + "to " + "Reminders");

        long result = db.insert("Reminders", null, contentValues);

        if (result == -1){
            return false;
        }
        return true;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM Reminders", null);
        return data;
    }
    //may have to change this
    public Cursor getItemID(String getID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + "ID " + "FROM " + "Reminders " + "WHERE ID = '" + getID  + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateReminder(String reminder, int ID, String newReminder, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE Reminders SET Message ='" + newReminder + "', Date ='" + date +  "' WHERE " + "ID = '" + ID + "'";
        Log.d("DbHelper", "updateReminder: query " + query);
        Log.d("DbHelper", "updateReminder: Setting reminder to " + newReminder);
        db.execSQL(query);
    }

    public void deleteReminder(int ID, String reminder){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM Reminders WHERE ID = '" + ID + "'";
        Log.d("DbHelper", "deleteReminder query : " + query);
        Log.d("DbHelper", "Deleting " + reminder + " from database");
        db.execSQL(query);
    }
}

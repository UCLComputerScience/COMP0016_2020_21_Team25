package com.example.fisev2concierge.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RemindersDbHelper extends SQLiteOpenHelper {

    public RemindersDbHelper(Context context){
        super(context, "Reminders", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + "Reminders" + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "Message" + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "Reminders");
        onCreate(db);
    }

    public boolean addData(String message){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Message", message);

        long result = db.insert("Reminders", null, contentValues);

        return result != -1;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM Reminders", null);
    }

    public void updateReminder(int ID, String newReminder){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE Reminders SET Message ='" + newReminder + "' WHERE " + "ID = '" + ID + "'";
        db.execSQL(query);
    }

    public void deleteReminder(int ID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM Reminders WHERE ID = '" + ID + "'";
        db.execSQL(query);
    }
}

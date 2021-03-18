package com.example.fisev2concierge.functionalityClasses;

import android.content.Context;
import android.database.Cursor;

import com.example.fisev2concierge.model.RemindersDbHelper;

public class RemindersFunctionality {

    private RemindersDbHelper dbHelper;

    public RemindersFunctionality(Context context){
        this.dbHelper = new RemindersDbHelper(context);
    }

    public Cursor getReminders(){
        Cursor cursor = dbHelper.getData();
        return cursor;
    }

    public boolean addReminder(String reminder){
        boolean insertData = dbHelper.addData(reminder);
        return insertData;
    }

    public void updateReminder(String selectedReminder, int selectedID, String item){
        dbHelper.updateReminder(selectedReminder, selectedID, item);
    }

    public void deleteReminder(int selectedID, String selectedReminder){
        dbHelper.deleteReminder(selectedID, selectedReminder);
    }
}

package com.example.fisev2concierge.functionalityClasses;

import android.content.Context;
import android.database.Cursor;

import com.example.fisev2concierge.model.RemindersDbHelper;

public class RemindersFunctionality {

    private final RemindersDbHelper dbHelper;

    public RemindersFunctionality(Context context){
        this.dbHelper = new RemindersDbHelper(context);
    }

    public Cursor getReminders(){
        return dbHelper.getData();
    }

    public boolean addReminder(String reminder){
        return dbHelper.addData(reminder);
    }

    public void updateReminder(int selectedID, String item){
        dbHelper.updateReminder(selectedID, item);
    }

    public void deleteReminder(int selectedID){
        dbHelper.deleteReminder(selectedID);
    }
}

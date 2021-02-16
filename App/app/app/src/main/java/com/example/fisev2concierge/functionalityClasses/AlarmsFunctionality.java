package com.example.fisev2concierge.functionalityClasses;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.helperClasses.AlertReceiver;
import com.example.fisev2concierge.model.AlarmsDbHelper;

import java.util.Calendar;

public class AlarmsFunctionality {

    AlarmsDbHelper dbHelper;

    public AlarmsFunctionality(Context context){
        this.dbHelper = new AlarmsDbHelper(context);
    }

    public Cursor getAlarms(){
        Cursor cursor = dbHelper.getData();
        return cursor;
    }

    public boolean addAlarm(String newEntry, String date){
        boolean dataInserted = dbHelper.addData(newEntry, date);
        return dataInserted;
    }

    public void updateAlarm(String selectedAlarm, int selectedID, String item, String date){
        dbHelper.updateAlarm(selectedAlarm, selectedID, item, date);
    }

    public void deleteAlarm(int selectedID, String selectedAlarm){
        dbHelper.deleteAlarm(selectedID, selectedAlarm);
    }

    public Cursor getRecent(){
        Cursor cursor = dbHelper.getRecent();
        return cursor;
    }

    public void startAlarm(AppCompatActivity appCompatActivity, Context context, String id, Calendar c){
        AlarmManager alarmManager = (AlarmManager) appCompatActivity.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(id), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        }
    }

    public void stopAlarm(AppCompatActivity appCompatActivity, Context context, int id){
        Intent intent = new Intent(appCompatActivity.getApplicationContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) appCompatActivity.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}

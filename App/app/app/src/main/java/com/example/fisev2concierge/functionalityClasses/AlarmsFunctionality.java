package com.example.fisev2concierge.functionalityClasses;

import android.app.AlarmManager;
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

    private final AlarmsDbHelper dbHelper;

    public AlarmsFunctionality(Context context){
        this.dbHelper = new AlarmsDbHelper(context);
    }

    public Cursor getAlarms(){
        return dbHelper.getData();
    }

    public boolean addAlarm(String newEntry, String date){
        return dbHelper.addData(newEntry, date);
    }

    public void updateAlarm(int selectedID, String item, String date){
        dbHelper.updateAlarm(selectedID, item, date);
    }

    public void deleteAlarm(int selectedID){
        dbHelper.deleteAlarm(selectedID);
    }

    public Cursor getRecent(){
        return dbHelper.getRecent();
    }

    public void startAlarm(AppCompatActivity appCompatActivity, Context context, String id, Calendar c, String message){
        AlarmManager alarmManager = (AlarmManager) appCompatActivity.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class).putExtra("message", message);
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

package com.example.fisev2concierge.functionalityClasses;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.HelpView;
import com.example.fisev2concierge.HistoryView;
import com.example.fisev2concierge.MainActivity;
import com.example.fisev2concierge.TimersView;
import com.example.fisev2concierge.ViewAlarmsView;
import com.example.fisev2concierge.ViewRemindersView;

public class OpenActivity {
    AppCompatActivity appCompatActivity;
    Context context;

    public OpenActivity(AppCompatActivity appCompatActivity, Context context){
        this.appCompatActivity = appCompatActivity;
        this.context = context;
    }

    public void openActivity(String activityName){
        Intent intent = null;
        switch (activityName){
            case "reminders":
                intent = new Intent(context, ViewRemindersView.class);
                break;
            case "alarms":
                intent = new Intent(context, ViewAlarmsView.class);
                break;
            case "timers":
                intent = new Intent(context, TimersView.class);
                break;
            case "instructions":
                intent = new Intent(context, HelpView.class);
                break;
            case "history":
                intent = new Intent(context, HistoryView.class);
                break;
        }
        appCompatActivity.startActivity(intent);
    }
}

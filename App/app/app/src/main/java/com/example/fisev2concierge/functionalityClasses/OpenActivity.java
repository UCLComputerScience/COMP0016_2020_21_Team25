package com.example.fisev2concierge.functionalityClasses;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.views.InstructionActivity;
import com.example.fisev2concierge.views.HistoryActivity;
import com.example.fisev2concierge.views.MainActivity;
import com.example.fisev2concierge.views.TimersActivity;
import com.example.fisev2concierge.views.ViewAlarmsActivity;
import com.example.fisev2concierge.views.ViewRemindersActivity;

public class OpenActivity {
    private final AppCompatActivity appCompatActivity;
    private final Context context;

    public OpenActivity(AppCompatActivity appCompatActivity, Context context){
        this.appCompatActivity = appCompatActivity;
        this.context = context;
    }

    public void openActivity(String activityName){
        Intent intent;
        switch (activityName){
            case "reminders":
                intent = new Intent(context, ViewRemindersActivity.class);
                break;
            case "alarms":
                intent = new Intent(context, ViewAlarmsActivity.class);
                break;
            case "timers":
                intent = new Intent(context, TimersActivity.class);
                break;
            case "instructions":
                intent = new Intent(context, InstructionActivity.class);
                break;
            case "history":
                intent = new Intent(context, HistoryActivity.class);
                break;
            default:
                intent = new Intent(context, MainActivity.class);
        }
        appCompatActivity.startActivity(intent);
    }
}

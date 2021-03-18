package com.example.fisev2concierge.functionalityClasses;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.UI.InstructionActivity;
import com.example.fisev2concierge.UI.HistoryActivity;
import com.example.fisev2concierge.UI.MainActivity;
import com.example.fisev2concierge.UI.TimersActivity;
import com.example.fisev2concierge.UI.ViewAlarmsActivity;
import com.example.fisev2concierge.UI.ViewRemindersActivity;

public class OpenActivity {
    private AppCompatActivity appCompatActivity;
    private Context context;

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

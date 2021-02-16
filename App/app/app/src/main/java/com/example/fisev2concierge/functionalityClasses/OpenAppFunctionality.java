package com.example.fisev2concierge.functionalityClasses;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OpenAppFunctionality {

    AppCompatActivity appCompatActivity;
    Context context;

    public OpenAppFunctionality(AppCompatActivity appCompatActivity, Context context){
        this.appCompatActivity = appCompatActivity;
        this.context = context;
    }

    public void openApp(String packageName){
        Intent launchIntent = appCompatActivity.getPackageManager().getLaunchIntentForPackage(packageName);
        if (launchIntent != null){
            appCompatActivity.startActivity(launchIntent);
        } else {
            Toast.makeText(context, "Could not open", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.example.fisev2concierge.functionalityClasses;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class OpenAppFunctionality {

    AppCompatActivity appCompatActivity;
    Context context;
    private HashMap appPackageNames = new HashMap();

    public OpenAppFunctionality(AppCompatActivity appCompatActivity, Context context){
        this.appCompatActivity = appCompatActivity;
        this.context = context;
        appPackageNames.put("facebook", "com.facebook.katana");
        appPackageNames.put("snapchat", "com.snapchat.android");
        appPackageNames.put("twitter", "com.twitter.android");
        appPackageNames.put("instagram", "com.instagram.android");
        appPackageNames.put("whatsapp", "com.whatsapp");
        appPackageNames.put("gumtree", "com.gumtree.android");
        appPackageNames.put("yell", "com.yell.launcher2");
        appPackageNames.put("settings", "com.android.settings");
    }

    public void openApp(String appName){
        String packageName = (String) appPackageNames.get(appName);
        Intent launchIntent = appCompatActivity.getPackageManager().getLaunchIntentForPackage(packageName);
        if (launchIntent != null){
            appCompatActivity.startActivity(launchIntent);
        } else {
            Toast.makeText(context, "Could not open", Toast.LENGTH_SHORT).show();
        }
    }
}

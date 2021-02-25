package com.example.fisev2concierge.functionalityClasses;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        appPackageNames.put("messages", "com.google.android.apps.messaging");
        appPackageNames.put("chrome", "com.android.chrome");
        appPackageNames.put("youtube", "com.google.android.youtube");
    }

    public void openApp(String appName){
        String packageName = (String) appPackageNames.get(appName);
        Intent launchIntent = appCompatActivity.getPackageManager().getLaunchIntentForPackage(packageName);
        if (launchIntent != null){
            appCompatActivity.startActivity(launchIntent);
        } else {
            Toast.makeText(context, "Could not open", Toast.LENGTH_SHORT).show();
            launchIntent = new Intent(Intent.ACTION_VIEW);
            launchIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
            appCompatActivity.startActivity(launchIntent);
        }
    }
}

package com.example.fisev2concierge.helperClasses;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppPackageNameLookup {
    private HashMap appPackageNames = new HashMap();
    private AppCompatActivity appCompatActivity;

    public AppPackageNameLookup(AppCompatActivity appCompatActivity){
        this.appCompatActivity = appCompatActivity;
        appPackageNames.put("facebook", "com.facebook.katana");
        appPackageNames.put("snapchat", "com.snapchat.android");
        appPackageNames.put("twitter", "com.twitter.android");
        appPackageNames.put("instagram", "com.instagram.android");
        appPackageNames.put("whatsapp", "com.whatsapp");
        appPackageNames.put("gumtree", "com.gumtree.android");
        appPackageNames.put("yell", "com.yell.launcher2");
//        appPackageNames.put("settings", "com.android.settings");
//        appPackageNames.put("messages", "com.google.android.apps.messaging");
        appPackageNames.put("chrome", "com.android.chrome");
        appPackageNames.put("youtube", "com.google.android.youtube");
//        appPackageNames.put("calculator", "com.android.calculator2");
        //need to expand these to comman apps preinstalled on the phone such as calc and notepad
        //need to also expand to apps old people might use
        final PackageManager pm = appCompatActivity.getPackageManager();
        List<PackageInfo> packs = pm.getInstalledPackages(0);
        for (PackageInfo pi : packs) {
            if (!appPackageNames.containsKey(pi.applicationInfo.loadLabel(pm).toString().toLowerCase())){
                //only insert if it's not there already, prevent conflicts with hardcoded package names
                System.out.println("-------------------");
                System.out.println("App Name: " + pi.applicationInfo.loadLabel(pm).toString().toLowerCase());
                System.out.println("Package Name: " + pi.packageName);
                System.out.println("-------------------");
                appPackageNames.put(pi.applicationInfo.loadLabel(pm).toString().toLowerCase(), pi.packageName);
            }
        }
    }

    public String search(String appName) {
        return (String) appPackageNames.get(appName);
    }
}

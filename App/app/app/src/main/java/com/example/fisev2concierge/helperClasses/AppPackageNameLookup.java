package com.example.fisev2concierge.helperClasses;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;

public class AppPackageNameLookup {
    private final HashMap<String, String> appPackageNames = new HashMap<>();

    public AppPackageNameLookup(AppCompatActivity appCompatActivity){
        appPackageNames.put("facebook", "com.facebook.katana");
        appPackageNames.put("snapchat", "com.snapchat.android");
        appPackageNames.put("twitter", "com.twitter.android");
        appPackageNames.put("instagram", "com.instagram.android");
        appPackageNames.put("whatsapp", "com.whatsapp");
        appPackageNames.put("gumtree", "com.gumtree.android");
        appPackageNames.put("yell", "com.yell.launcher2");
        appPackageNames.put("fise v2 concierge", "com.example.fisev2concierge");
        appPackageNames.put("chrome", "com.android.chrome");
        appPackageNames.put("youtube", "com.google.android.youtube");
        final PackageManager pm = appCompatActivity.getPackageManager();
        List<PackageInfo> packs = pm.getInstalledPackages(0);
        for (PackageInfo pi : packs) {
            if (!appPackageNames.containsKey(pi.applicationInfo.loadLabel(pm).toString().toLowerCase())){
                appPackageNames.put(pi.applicationInfo.loadLabel(pm).toString().toLowerCase(), pi.packageName);
            }
        }
    }

    public String search(String appName) {
        return appPackageNames.get(appName);
    }
}

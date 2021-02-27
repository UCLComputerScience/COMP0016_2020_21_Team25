package com.example.fisev2concierge.helperClasses;

import java.util.HashMap;

public class AppPackageNameLookup {
    private HashMap appPackageNames = new HashMap();

    public AppPackageNameLookup(){
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
        //need to expand these to comman apps preinstalled on the phone such as calc and notepad
        //need to also expand to apps old people might use
    }

    public String search(String appName) {
        return (String) appPackageNames.get(appName);
    }
}

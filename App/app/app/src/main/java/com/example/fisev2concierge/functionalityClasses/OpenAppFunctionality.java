package com.example.fisev2concierge.functionalityClasses;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.controllers.MainController;
import com.example.fisev2concierge.helperClasses.AppPackageNameLookup;
import com.example.fisev2concierge.helperClasses.SearchUrlLookup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OpenAppFunctionality {

    AppCompatActivity appCompatActivity;
    private MainController mainController = new MainController();
    Context context;

    public OpenAppFunctionality(AppCompatActivity appCompatActivity, Context context){
        this.appCompatActivity = appCompatActivity;
        this.context = context;
    }

    public void openApp(String appName){
        String packageName = mainController.packageNameLookup(appCompatActivity, appName);
        if (packageName == null){
            System.out.println("Hooray! Package name is null");
        }
        openPackage(appName, packageName);
    }

    public void openPackage(String appName, String packageName){
        System.out.println("openPackage: " + appName);
        if (packageName == null ){
            Toast.makeText(context, "Could not open: " + appName, Toast.LENGTH_SHORT).show();
            //package name not found -> open playstore
            System.out.println("Searching playstore");
            searchPlayStore(appName);
        } else {
            //package name found
            Intent launchIntent = appCompatActivity.getPackageManager().getLaunchIntentForPackage(packageName);
            if (launchIntent != null) {
                appCompatActivity.startActivity(launchIntent);
            } else {
                //app is not installed, open on playstore
                Toast.makeText(context, "Could not open: " + appName, Toast.LENGTH_SHORT).show();
                openAppInPlayStore(packageName);
            }
        }
    }

    public void searchPlayStore(String appName){
        Intent launchIntent = new Intent(Intent.ACTION_VIEW);
        appName = appName.replace(" ", "+");
        System.out.println("searchAppName: " + appName);
        String playStoreUrl = mainController.searchUrlLookup("playstoreSearchApp");
        System.out.println("SearchPlayStore Url: " + playStoreUrl+appName);
        launchIntent.setData(Uri.parse(playStoreUrl + appName));
        appCompatActivity.startActivity(launchIntent);
    }

    public void openAppInPlayStore(String packageName){
        Intent launchIntent = new Intent(Intent.ACTION_VIEW);
        String playStoreUrl = mainController.searchUrlLookup("playstoreOpenApp");
        launchIntent.setData(Uri.parse(playStoreUrl + packageName));
        appCompatActivity.startActivity(launchIntent);
    }

    public void showAllApps(){
        //interesting code that may be able to open all apps installed
        final PackageManager pm = appCompatActivity.getPackageManager();
        List<PackageInfo> packs = pm.getInstalledPackages(0);
        for (PackageInfo pi : packs) {
            System.out.println("-------------------");
            System.out.println("App Name: " + pi.applicationInfo.loadLabel(pm).toString().toLowerCase());
            System.out.println("Package Name: " + pi.packageName);
            System.out.println("-------------------");
        }
        //user stories

        //alternative code
//        final PackageManager pm = getPackageManager();
////get a list of installed apps.
//        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
//
//        for (ApplicationInfo packageInfo : packages) {
//            Log.d(TAG, "Installed package :" +packageInfo.name+":"+ packageInfo.packageName+":"+pm.getApplicationLabel(packageInfo));
//        }
    }
}

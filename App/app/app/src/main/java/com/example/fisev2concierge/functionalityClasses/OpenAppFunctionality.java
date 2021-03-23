package com.example.fisev2concierge.functionalityClasses;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.controllers.MainController;

public class OpenAppFunctionality {

    private final AppCompatActivity appCompatActivity;
    private final Context context;
    private final MainController mainController = new MainController();

    public OpenAppFunctionality(AppCompatActivity appCompatActivity, Context context){
        this.appCompatActivity = appCompatActivity;
        this.context = context;
    }

    public void openApp(String appName){
        String packageName = mainController.packageNameLookup(appCompatActivity, appName);
        openPackage(appName, packageName);
    }

    public void openPackage(String appName, String packageName){
        if (packageName == null ){
            Toast.makeText(context, "Could not open: " + appName, Toast.LENGTH_SHORT).show();
            //package name not found -> search playstore
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
        String playStoreUrl = mainController.searchUrlLookup("playstoreSearchApp");
        launchIntent.setData(Uri.parse(playStoreUrl + appName));
        appCompatActivity.startActivity(launchIntent);
    }

    public void openAppInPlayStore(String packageName){
        Intent launchIntent = new Intent(Intent.ACTION_VIEW);
        String playStoreUrl = mainController.searchUrlLookup("playstoreOpenApp");
        launchIntent.setData(Uri.parse(playStoreUrl + packageName));
        appCompatActivity.startActivity(launchIntent);
    }

}

package com.example.fisev2concierge.functionalityClasses;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.controllers.MainController;
import com.example.fisev2concierge.helperClasses.AppPackageNameLookup;
import com.example.fisev2concierge.helperClasses.SearchUrlLookup;

import java.util.HashMap;

public class OpenAppFunctionality {

    AppCompatActivity appCompatActivity;
    private MainController mainController = new MainController();
    Context context;

    public OpenAppFunctionality(AppCompatActivity appCompatActivity, Context context){
        this.appCompatActivity = appCompatActivity;
        this.context = context;
    }

    public void openApp(String appName){
        String packageName = mainController.packageNameLookup(appName);
        if (packageName.equals(null)){
            System.out.println("Hooray! Package name is null");
        }
        openPackage(appName, packageName);
    }

    public void openPackage(String appName, String packageName){
        Intent launchIntent = appCompatActivity.getPackageManager().getLaunchIntentForPackage(packageName);
        if (launchIntent != null){
            appCompatActivity.startActivity(launchIntent);
        } else {
            Toast.makeText(context, "Could not open: " + appName, Toast.LENGTH_SHORT).show();
            if (packageName.equals(null)){
                searchPlayStore(appName);
            } else {
                openAppInPlayStore(packageName);
            }
        }
    }

    public void searchPlayStore(String appName){
        Intent launchIntent = new Intent(Intent.ACTION_VIEW);
        appName = appName.replace(" ", "+");
        System.out.println("searchAppName: " + appName);
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

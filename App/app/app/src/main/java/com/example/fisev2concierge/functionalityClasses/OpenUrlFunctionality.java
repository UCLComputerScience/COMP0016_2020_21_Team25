package com.example.fisev2concierge.functionalityClasses;

import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.controllers.MainController;
import com.example.fisev2concierge.helperClasses.SearchUrlLookup;
import com.example.fisev2concierge.helperClasses.WebsiteUrlLookup;

import java.util.HashMap;

public class OpenUrlFunctionality {

    private AppCompatActivity appCompatActivity;
    private MainController mainController = new MainController();

    public OpenUrlFunctionality(AppCompatActivity appCompatActivity){
        this.appCompatActivity = appCompatActivity;
    }

    public void openWeb(String websiteName){
        String url = mainController.websiteUrlLookup(websiteName);
        if (url == null){
            //url was not found, do google search
            websiteName = websiteName.replace(" ", "+");
            url = mainController.searchUrlLookup("google") + websiteName;
        }
        openUrl(url);
    }

    public void searchWeb(String websiteName, HashMap searchItems){
        String url = mainController.searchUrlLookup(websiteName);
        String searchItem = (String) searchItems.get("Application");
        if (url == null){
            //search url was not found, do google search
            websiteName = websiteName.replace(" ", "+");
            searchItem = searchItem.replace(" ", "+");
            url = mainController.searchUrlLookup("google") + websiteName + "+" + searchItem;
        } else {
            switch (websiteName){
                case "amazon":
                    //only require keywords
                    searchItem = searchItem.replace(" ", "+");
                    url = url + searchItem;
                    break;
                case "yell":
                    //require keywords and location
                    //have had to simply search urls such as remove 'searchSeed'
                    String location = (String) searchItems.get("location");
                    searchItem = searchItem.replace(" ", "+");
                    location = location.replace(" ", "+");
                    url = url.replace("{keywords}", searchItem);
                    url = url.replace("{location}", location);
                    break;
                case "google":
                    searchItem = searchItem.replace(" ", "+");
                    url = url + searchItem;
                    break;
            }
        }
        openUrl(url);
    }

    public void openUrl(String url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        appCompatActivity.startActivity(browserIntent);
    }
}

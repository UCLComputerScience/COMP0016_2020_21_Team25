package com.example.fisev2concierge.functionalityClasses;

import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.controllers.MainController;

import java.util.HashMap;

public class OpenUrlFunctionality {

    private final AppCompatActivity appCompatActivity;
    private final MainController mainController = new MainController();

    public OpenUrlFunctionality(AppCompatActivity appCompatActivity){
        this.appCompatActivity = appCompatActivity;
    }

    public void openWeb(String websiteName){
        String url = mainController.websiteUrlLookup(websiteName);
        if (url == null){
            websiteName = websiteName.replace(" ", "+");
            url = mainController.searchUrlLookup("google") + websiteName;
        }
        openUrl(url);
    }

    public void searchWeb(String websiteName, HashMap<String, String> searchItems){
        String url = mainController.searchUrlLookup(websiteName);
        String searchItem = (String) searchItems.get("Application");
        if (url == null){
            unknownSearchWebsite(websiteName, searchItem);
        } else {
            switch (websiteName){
                case "amazon":
                    searchAmazon(url, searchItem);
                    break;
                case "yell":
                    searchYell(url, searchItem, searchItems);
                    break;
                case "google":
                    searchGoogle(url, searchItem);
                    break;
            }
        }
    }

    private void unknownSearchWebsite(String websiteName, String searchItem){
        websiteName = websiteName.replace(" ", "+");
        searchItem = searchItem.replace(" ", "+");
        String url = mainController.searchUrlLookup("google") + websiteName + "+" + searchItem;
        openUrl(url);
    }

    private void searchAmazon(String url, String searchItem){
        searchItem = searchItem.replace(" ", "+");
        url = url + searchItem;
        openUrl(url);
    }

    private void searchYell(String url, String searchItem, HashMap<String, String> searchItems){
        String location = (String) searchItems.get("location");
        searchItem = searchItem.replace(" ", "+");
        assert location != null;
        location = location.replace(" ", "+");
        url = url.replace("{keywords}", searchItem);
        url = url.replace("{location}", location);
        openUrl(url);
    }

    private void searchGoogle(String url, String searchItem){
        searchItem = searchItem.replace(" ", "+");
        url = url + searchItem;
        openUrl(url);
    }

    public void openUrl(String url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        appCompatActivity.startActivity(browserIntent);
    }
}

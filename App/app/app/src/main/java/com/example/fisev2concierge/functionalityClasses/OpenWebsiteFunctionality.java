package com.example.fisev2concierge.functionalityClasses;

import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

public class OpenWebsiteFunctionality {

    AppCompatActivity appCompatActivity;

    public OpenWebsiteFunctionality(AppCompatActivity appCompatActivity){
        this.appCompatActivity = appCompatActivity;
    }

    public void openWeb(String website){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
        appCompatActivity.startActivity(browserIntent);
    }
}

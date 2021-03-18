package com.example.fisev2concierge.functionalityClasses;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.fisev2concierge.controllers.MainController;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchContacts {

    private AppCompatActivity appCompatActivity;
    private Context context;
    private Activity activity;
    private static final int REQUEST_CONTACTS = 5;

    public SearchContacts(AppCompatActivity appCompatActivity, Context context, Activity activity){
        this.appCompatActivity = appCompatActivity;
        this.context = context;
        this.activity = activity;
    }

    public String searchContacts(String searchName){
        searchName = searchName.toLowerCase();
        searchName = searchName.substring(0, 1).toUpperCase() + searchName.substring(1);
        System.out.println("searchName: " + searchName);
        if (searchName.equals("Gp") || searchName.equals("Dentist") || searchName.equals("Optometrist")){
            MainController mainController = new MainController();
            if (!mainController.hasUserID(context)){
                Toast.makeText(context, "Not connected to an admin", Toast.LENGTH_SHORT).show();
            } else {
                String userID = mainController.getUserID(context);
                ArrayList<String> queryResult = mainController.backendServices("getEmergencyContacts", userID);
                String json = queryResult.get(0);
                System.out.println("json: " + json);
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    JSONObject newJsonObject = jsonObject.getJSONObject("emergency_contacts");
                    System.out.println(searchName + " number is " + newJsonObject.getString(searchName));
                    return newJsonObject.getString(searchName);
                } catch (Exception e){
                    Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.READ_CONTACTS}, REQUEST_CONTACTS);
            } else {
                Cursor cursor = appCompatActivity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE}, "DISPLAY_NAME = '" + searchName + "'", null, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    return cursor.getString(0);
                } else {
                    Toast.makeText(context, "No such contact: " + searchName, Toast.LENGTH_SHORT).show();
                }
            }
        }
        return "-1";
    }

}

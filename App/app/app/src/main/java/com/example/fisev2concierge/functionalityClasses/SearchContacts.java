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

public class SearchContacts {

    AppCompatActivity appCompatActivity;
    Context context;
    Activity activity;
    private static final int REQUEST_CONTACTS = 5;

    public SearchContacts(AppCompatActivity appCompatActivity, Context context, Activity activity){
        this.appCompatActivity = appCompatActivity;
        this.context = context;
        this.activity = activity;
    }

    public String searchContacts(String searchName){
        //search first and last name?
        //what happens if two contacts have the same name?
        //add case for searching contacts that may be provided through admin site
        //what if no match is found?
        if (searchName.equals("gp")){
            //query backend
        } else {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.READ_CONTACTS}, REQUEST_CONTACTS);
                searchContacts(searchName);
            } else {
                Cursor cursor = appCompatActivity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE}, "DISPLAY_NAME = '" + searchName + "'", null, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    String number = cursor.getString(0);
                    System.out.println("Contact: " + searchName + " Number: " + number);
                    return number;
                } else {
                    System.out.println("No such contact");
                    Toast.makeText(context, "No such contact: " + searchName, Toast.LENGTH_SHORT).show();
                }
            }
        }
        return "-1";
    }

    public void addContacts(String name, String number){
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, number);
        appCompatActivity.startActivity(intent);
    }
}

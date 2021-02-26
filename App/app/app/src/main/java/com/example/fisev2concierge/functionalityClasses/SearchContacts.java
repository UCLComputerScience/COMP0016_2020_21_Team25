package com.example.fisev2concierge.functionalityClasses;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class SearchContacts {

    AppCompatActivity appCompatActivity;
    Context context;
    Activity activity;

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
//        ArrayList<AndroidContact> androidContacts = new ArrayList<>();
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CONTACTS}, 4);
//        } else {
//            ContentResolver contentResolver = appCompatActivity.getContentResolver();
//            Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
//
//            if (cursor.getCount() > 0) {
//                while (cursor.moveToNext()) {
//                    String ID = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
//                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                    String number = "";
//                    int hasNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
//                    if (hasNumber > 0) {
//                        System.out.println("ID: " + ID);
//                        Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " - ?", new String[]{ID}, null);
//                        while (phoneCursor.moveToNext()) {
//                            //Expand this to save all numbers?
//                            //Currently it gets the first number stored and breaks
//                            number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                            System.out.println("Number: " + number);
////                            break;
//                        }
//                        phoneCursor.close();
//                    } else {
//                        System.out.println("Error: This contact does not have a number saved");
//                    }
//                    AndroidContact contact = new AndroidContact(name, number, Integer.parseInt(ID));
//                    androidContacts.add(contact);
//                    }
//                }
//            }
//        for (AndroidContact androidContact : androidContacts){
//            System.out.println("Contact Name: " + androidContact.getName() + " | Contact Number: " + androidContact.getNumber() + " | ID: " + androidContact.getID());
//            if (androidContact.getName().toLowerCase().equals(searchName.toLowerCase())){
//                System.out.println("CONTACT MATCHED!");
//                //return this contacts number
//            }
//        }
        return "012345678910";
    }

    public void addContacts(String name, String number){
        // Creates a new Intent to insert a contact
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        // Sets the MIME type to match the Contacts Provider
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, number);
        appCompatActivity.startActivity(intent);
    }

    public class AndroidContact{
        private String name = "";
        private String number = "";
        private int ID = 0;

        public AndroidContact(String name, String number, int ID){
            this.name = name;
            this.number = number;
            this.ID = ID;
        }

        public String getName() {
            return name;
        }

        public String getNumber() {
            return number;
        }

        public int getID() {
            return ID;
        }
    }
}

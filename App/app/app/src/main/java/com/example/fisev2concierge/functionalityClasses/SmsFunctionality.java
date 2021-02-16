package com.example.fisev2concierge.functionalityClasses;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SmsFunctionality extends AppCompatActivity {

    private static final int REQUEST_MSG = 4;
    private Activity activity;
    private Context context;
    private String number, message;

    public SmsFunctionality(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }

    public int getRequestMsg(){
        return REQUEST_MSG;
    }

    public void sendSMS(String number, String message){
        this.number = number;
        this.message = message;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.SEND_SMS}, REQUEST_MSG);
        } else {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(number, null, message, null, null);
                Toast.makeText(context, "Text sent successfully", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(context, "Error sending text", Toast.LENGTH_SHORT).show();
            }
        }
    }

}

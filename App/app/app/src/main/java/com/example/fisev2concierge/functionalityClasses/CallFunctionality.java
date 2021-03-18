package com.example.fisev2concierge.functionalityClasses;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CallFunctionality extends AppCompatActivity {

    private static final int REQUEST_CALL = 3;
    private Activity activity;
    private Context context;
    private String number;

    public CallFunctionality(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }

    public int getREQUESTCALL(){
        return REQUEST_CALL;
    }

    public void makePhoneCall(String number){
        this.number = number;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            String dial = "tel:" + number;
            activity.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }
}

package com.example.fisev2concierge.helperClasses;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.fisev2concierge.controllers.MainController;
import com.example.fisev2concierge.speech.SpeechSynthesis;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static com.example.fisev2concierge.speech.SpeechRecognition.RecordAudioRequestCode;

public class GetLatLon{

    private Double lat;
    private Double lon;
    private Context context;
    private final int REQUEST_LOCATION = 6;
    private Activity activity;
    private HashMap askBobResponse;
    private AppCompatActivity appCompatActivity;
    private SpeechSynthesis speechSynthesis;

    public GetLatLon(Context context, Activity activity, HashMap askBobResponse, AppCompatActivity appCompatActivity, SpeechSynthesis speechSynthesis){
        this.context = context;
        this.activity = activity;
        this.askBobResponse = askBobResponse;
        this.appCompatActivity = appCompatActivity;
        this.speechSynthesis = speechSynthesis;
    }

    public void searchLatLon(){
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        } else {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    System.out.println("fusedLocationProviderClient onSuccess");
                    if (location != null) {
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                        MainController mainController = new MainController();
                        String postcode = mainController.getLocation(context, activity, lat, lon);
                        askBobResponse.put("location", postcode);
                        mainController.askBobController(askBobResponse, context, activity, appCompatActivity, speechSynthesis);
                    } else {
                        askBobResponse.put("location", "london");
                        System.out.println("location was null!!!");
                    }
                }
            });
        }
    }
}

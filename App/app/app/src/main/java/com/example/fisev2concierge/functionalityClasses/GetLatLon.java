package com.example.fisev2concierge.functionalityClasses;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static com.example.fisev2concierge.speech.SpeechRecognition.RecordAudioRequestCode;

public class GetLatLon{

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
                    MainController mainController = new MainController();
                    if (location != null) {
                        if (askBobResponse.get("Service").equals("Yell Search")) {
                            String postcode = mainController.getLocation(context, activity, location.getLatitude(), location.getLongitude());
                            askBobResponse.put("location", postcode);
                            mainController.askBobController(askBobResponse, context, activity, appCompatActivity, speechSynthesis);
                            return;
                        } else {
                            String url = mainController.searchUrlLookup("maps_transport");
                            url = url.replace("{lat}", String.valueOf(location.getLatitude()));
                            url = url.replace("{lon}", String.valueOf(location.getLongitude()));
                            TransportApiResponse transportApiResponse = new TransportApiResponse();
                            url = transportApiResponse.searchForTransport(askBobResponse.get("Transport Type").toString() ,url);
                            speechSynthesis.runTts(askBobResponse.get("Response").toString());
                            mainController.openUrl(appCompatActivity, url);
                            if (mainController.hasUserID(context)) {
                                mainController.backendServices("addHistory", askBobResponse.get("Service").toString() + "&user_id=" + mainController.getUserID(context));
                            }
                        }
                    } else {
                        if (askBobResponse.get("Service").equals("Yell Search")) {
                            askBobResponse.put("location", "london");
                            mainController.askBobController(askBobResponse, context, activity, appCompatActivity, speechSynthesis);
                            return;
                        } else {
                            Toast.makeText(context, "Location unavailable", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (askBobResponse.get("Service").equals("Yell Search")) {
                        askBobResponse.put("location", "london");
                        new MainController().askBobController(askBobResponse, context, activity, appCompatActivity, speechSynthesis);
                        return;
                    } else {
                        Toast.makeText(context, "Location unavailable", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}

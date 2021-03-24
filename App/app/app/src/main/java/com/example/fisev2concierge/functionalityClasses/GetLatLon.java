package com.example.fisev2concierge.functionalityClasses;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.fisev2concierge.controllers.MainController;
import com.example.fisev2concierge.helperClasses.TransportApiResponse;
import com.example.fisev2concierge.speech.SpeechSynthesis;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.HashMap;
import java.util.Objects;

public class GetLatLon{

    private final Context context;
    private final Activity activity;
    private final HashMap<String, String> askBobResponse;
    private final AppCompatActivity appCompatActivity;
    private final SpeechSynthesis speechSynthesis;

    public GetLatLon(Context context, Activity activity, HashMap<String, String> askBobResponse, AppCompatActivity appCompatActivity, SpeechSynthesis speechSynthesis){
        this.context = context;
        this.activity = activity;
        this.askBobResponse = askBobResponse;
        this.appCompatActivity = appCompatActivity;
        this.speechSynthesis = speechSynthesis;
    }

    public void searchLatLon(){
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            int REQUEST_LOCATION = 6;
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        } else {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this::handleLocationSuccess).addOnFailureListener(e -> handleLocationFailure());
        }
    }

    private void handleLocationSuccess(Location location){
        //note that for some reason we are unable to return the location safely -> threads, conditional synchronization etc were tried but unsuccessful
        //all services using location therefore must be called directly from within the fusedLocationProviderClient.getLastLocation() success and failure methods
        MainController mainController = new MainController();
        if (location != null) {
            if (askBobResponse.get("Service").equals("Yell Search")) {
                handleYellSearchWithLocation(location.getLatitude(), location.getLongitude());
            } else {
                handleTransportApiWithLocation(location.getLatitude(), location.getLongitude());
            }
        } else {
            if (askBobResponse.get("Service").equals("Yell Search")) {
                askBobResponse.put("location", "london");
                mainController.askBobController(askBobResponse, context, activity, appCompatActivity, speechSynthesis);
            } else {
                Toast.makeText(context, "Location unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void handleLocationFailure(){
        if (askBobResponse.get("Service").equals("Yell Search")) {
            askBobResponse.put("location", "london");
            new MainController().askBobController(askBobResponse, context, activity, appCompatActivity, speechSynthesis);
        } else {
            Toast.makeText(context, "Location unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleYellSearchWithLocation(Double lat, Double lon){
        MainController mainController = new MainController();
        String postcode = mainController.getLocation(lat, lon);
        askBobResponse.put("location", postcode);
        mainController.askBobController(askBobResponse, context, activity, appCompatActivity, speechSynthesis);
    }

    private void handleTransportApiWithLocation(Double lat, Double lon){
        MainController mainController = new MainController();
        String url = mainController.searchUrlLookup("maps_transport");
        url = url.replace("{lat}", String.valueOf(lat));
        url = url.replace("{lon}", String.valueOf(lon));
        TransportApiResponse transportApiResponse = new TransportApiResponse();
        url = transportApiResponse.searchForTransport(Objects.requireNonNull(askBobResponse.get("Transport Type")),url);
        speechSynthesis.runTts(askBobResponse.get("Response"));
        mainController.openUrl(appCompatActivity, url);
        if (mainController.hasUserID(context)) {
            mainController.backendServices("addHistory", askBobResponse.get("Service") + "&user_id=" + mainController.getUserID(context), appCompatActivity);
        }
    }
}

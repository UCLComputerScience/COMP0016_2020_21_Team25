package com.example.fisev2concierge.helperClasses;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class GetLatLon{

    private volatile Double lat = null;
    private volatile Double lon = null;
    private Context context;
    private ArrayList<Double> latlon = new ArrayList<>();
    private final int REQUEST_LOCATION = 6;
    private Activity activity;

    public GetLatLon(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
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
                        latlon.add(lat);
                        latlon.add(lon);
                        printLatLon();
                    } else {
                        System.out.println("location was null!!!");
                    }
                }
            });
        }
    }

    public void printLatLon(){
        System.out.println("lat: " + lat);
        System.out.println("lon: " + lon);
        System.out.println("lat and lon obtained ");
    }
}

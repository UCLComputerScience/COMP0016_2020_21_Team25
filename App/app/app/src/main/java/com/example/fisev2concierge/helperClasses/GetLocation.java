package com.example.fisev2concierge.helperClasses;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.json.*;

public class GetLocation implements Runnable{

    Context context;
    Activity activity;
    FusedLocationProviderClient fusedLocationProviderClient;
    private volatile String postcode;
    private volatile boolean ready = false;
    private final int REQUEST_LOCATION = 6;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private double lat;
    private double lon;

    public GetLocation(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public synchronized String getPostcode(){
        while (!ready){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return postcode;
    }

    @Override
    public synchronized void run(){
        getLocation(getLatLon());
        notifyAll();
    }

    public ArrayList<Double> getLatLon(){
        ArrayList<Double> arrayList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
//            getLocation();
        } else {
            locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(20 * 1000);
            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null) {
                        return;
                    }
                    for (Location location : locationResult.getLocations()) {
                        if (location != null) {
                            Double wayLatitude = location.getLatitude();
                            Double wayLongitude = location.getLongitude();
                            System.out.println(String.format(Locale.US, "%s -- %s", wayLatitude, wayLongitude));
                        }
                    }
                }
            };
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
            final Boolean[] ready = {false};
            System.out.println("Getting location");
            postcode = makeApiRequest(51.5661867, -0.279656);
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    System.out.println("fusedLocationProviderClient onSuccess");
                    if (location != null) {
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                        ready[0] = true;
                        arrayList.add(lat);
                        arrayList.add(lon);
                        System.out.println("lat: " + lat);
                        System.out.println("lon: " + lon);
                        System.out.println("lat and lon obtained ");
                    } else{
                        System.out.println("location was null!!!");
                    }
                }
            });
            System.out.println("Helllooooooooooooo");
            if (ready[0] == true){
                System.out.println("yayyyyyyyyyyy");
            }
        }
        return arrayList;
    }

    public void getLocation(ArrayList<Double> arrayList) {
        System.out.println("about to obtain postcode");
        System.out.println("lat, lon : " + lat + lon );
        postcode = makeApiRequest(arrayList.get(0), arrayList.get(1));
        System.out.println("postcode: " + postcode);
        ready = true;
    }

    public String makeApiRequest(Double lat, Double lon){
        System.out.println("making api request");
        //refactor
        try {
            String urlString = "https://api.postcodes.io/postcodes?lon={lon}&lat={lat}";
            urlString = urlString.replace("{lat}", lat.toString());
            urlString = urlString.replace("{lon}", lon.toString());
            ArrayList<String> result = new ArrayList<>();
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine = "";
                while ((inputLine = in.readLine()) != null) {
                    result.add(inputLine);
                }
                in.close();
                connection.disconnect();
            } catch (Exception e) {
                System.out.println("first exception");
                e.printStackTrace();
                result.add("500");
            }
            String jsonString = result.get(0);
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            String postcode = jsonArray.getJSONObject(0).getString("postcode");
            System.out.println("apiCall, postcode: " + postcode);
            return postcode;
        } catch (Exception e){
            System.out.println("exception");
            e.printStackTrace();
        }
        return "Error: 500";
    }
}

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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.*;

public class GetLocation implements Runnable{

    Context context;
    Activity activity;
    FusedLocationProviderClient fusedLocationProviderClient;
    private String postcode;
    private boolean ready = false;
    private final int REQUEST_LOCATION = 6;

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
    public void run(){
        getLocation();
        notifyAll();
    }

    public void getLocation() {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            getLocation();
        } else {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        Double lat = location.getLatitude();
                        Double lon = location.getLongitude();
                        postcode = makeApiRequest(lat, lon);
                    }
                }
            });
        }
        ready = true;
    }

    public String makeApiRequest(Double lat, Double lon){
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
                e.printStackTrace();
                result.add("500");
            }
            String jsonString = result.get(0);
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            String postcode = jsonArray.getJSONObject(0).getString("postcode");
            return postcode;
        } catch (Exception e){
            e.printStackTrace();
        }
        return "Error: 500";
    }
}

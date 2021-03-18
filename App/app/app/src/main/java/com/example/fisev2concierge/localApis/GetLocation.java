package com.example.fisev2concierge.localApis;

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

    private Context context;
    private Activity activity;
    private volatile String postcode;
    private volatile boolean ready = false;
    private double lat;
    private double lon;

    public GetLocation(Context context, Activity activity, Double lat, Double lon){
        this.context = context;
        this.activity = activity;
        this.lat = lat;
        this.lon = lon;
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
        getLocation(lat, lon);
        notifyAll();
    }

    public void getLocation(Double lat, Double lon) {
        postcode = makeApiRequest(lat, lon);
        ready = true;
    }

    public String makeApiRequest(Double lat, Double lon){
        String baseUrl = "https://api.postcodes.io/";
        String path = "postcodes?lon={lon}&lat={lat}";
        path = path.replace("{lat}", lat.toString());
        path = path.replace("{lon}", lon.toString());
        GetRequestFramework getRequestFramework = new GetRequestFramework(baseUrl);
        ArrayList<String> result = getRequestFramework.makeRequest(path);
        if (!result.isEmpty()) {
            String jsonString = result.get(0);
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                String postcode = jsonArray.getJSONObject(0).getString("postcode");
                return postcode;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return "london";
    }
}

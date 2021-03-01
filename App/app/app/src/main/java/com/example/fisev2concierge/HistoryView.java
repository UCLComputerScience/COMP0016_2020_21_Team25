package com.example.fisev2concierge;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.fisev2concierge.controllers.MainController;
import com.example.fisev2concierge.functionalityClasses.OpenAppFunctionality;
import com.example.fisev2concierge.functionalityClasses.SearchContacts;
import com.example.fisev2concierge.helperClasses.GetLatLon;
import com.example.fisev2concierge.helperClasses.GetLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryView extends AppCompatActivity {
    public Button button;
    private static final int REQUEST_CALL = 3;
    private static final int REQUEST_MSG = 4;
    MainController mainController = new MainController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_view);


        Button msgButton = findViewById(R.id.msgButton);
        msgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainController.sendText(HistoryView.this, HistoryView.this, "012345678910", "Test");
            }
        });

        Button appButton = findViewById(R.id.openInstalledApp);
        appButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mainController.openApp(HistoryView.this, HistoryView.this, "youtube");
                mainController.openApp(HistoryView.this, HistoryView.this, "settings");
//                mainController.openApp(HistoryView.this, HistoryView.this, "settings");
            }
        });

        Button searchPlaystore = findViewById(R.id.searchPlaystore);
        searchPlaystore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainController.openApp(HistoryView.this, HistoryView.this, "candy crush");
            }
        });

        Button openPlaystore = findViewById(R.id.openPlaystore);
        openPlaystore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainController.openApp(HistoryView.this, HistoryView.this, "snapchat");
            }
        });

        Button searchSite = findViewById(R.id.searchSite);
        searchSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetLatLon getLatLon = new GetLatLon(HistoryView.this, HistoryView.this);
                getLatLon.searchLatLon();
//                FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(HistoryView.this);
//                if (ContextCompat.checkSelfPermission(HistoryView.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HistoryView.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(HistoryView.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 6);
//                } else {
//                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//                        @Override
//                        public void onSuccess(Location location) {
//                            System.out.println("fusedLocationProviderClient onSuccess");
//                            if (location != null) {
//                                Double lat = location.getLatitude();
//                                Double lon = location.getLongitude();
//                                System.out.println("lat: " + lat);
//                                System.out.println("lon: " + lon);
//                                System.out.println("lat and lon obtained ");
//                            } else {
//                                System.out.println("First location was null");
//                                LocationRequest locationRequest = LocationRequest.create();
//                                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//                                locationRequest.setInterval(20 * 1000);
//                                LocationCallback locationCallback = new LocationCallback() {
//                                    @Override
//                                    public void onLocationResult(LocationResult locationResult) {
//                                        if (locationResult == null) {
//                                            System.out.println("Second location also null");
//                                            return;
//                                        }
//                                        for (Location location : locationResult.getLocations()) {
//                                            if (location != null) {
//                                                System.out.println("Lat: " + location.getLatitude());
//                                                System.out.println("Lon: " + location.getLongitude());
//                                            }
//                                        }
//                                    }
//                                };
//                                if (fusedLocationProviderClient != null) {
//                                    fusedLocationProviderClient.removeLocationUpdates(locationCallback);
//                                }
//                            }
//                        }
//                    });
//                    System.out.println("Success in else");
//                }
//                System.out.println("Code was completed");
            }
        });

        Button searchGoogle = findViewById(R.id.searchGoogle);
        searchGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //test yale as well
                HashMap hashMap = new HashMap();
                hashMap.put("searchItem", "ipad");
                mainController.searchSite(HistoryView.this, "ebay", hashMap);
            }
        });

        Button askBobOpenApp = findViewById(R.id.askBobOpenApp);
        askBobOpenApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap askBobResponse = mainController.askBobRequest("random ting");
//                mainController.askBobController(askBobResponse, HistoryView.this, HistoryView.this, HistoryView.this);
                if (askBobResponse.get("Service_Type").equals("ERROR")){
                    System.out.println("Command not understood: " + askBobResponse.get("text").toString());
                }
            }
        });

        Button listAppNames = findViewById(R.id.listAppNames);
        listAppNames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenAppFunctionality openAppFunctionality = new OpenAppFunctionality(HistoryView.this, HistoryView.this);
                openAppFunctionality.showAllApps();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mainController.makeCall(HistoryView.this, HistoryView.this, "012345678910");
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == REQUEST_MSG){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mainController.sendText(HistoryView.this, HistoryView.this, "012345678910", "Test");
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
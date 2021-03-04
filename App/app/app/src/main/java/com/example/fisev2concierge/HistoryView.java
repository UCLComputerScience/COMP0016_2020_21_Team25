package com.example.fisev2concierge;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
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
import com.example.fisev2concierge.speech.SpeechSynthesis;
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
                mainController.openApp(HistoryView.this, HistoryView.this, "calculator");
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
                mainController.handleUserRequest(new String[] {"can you find me a plumber"}, null, HistoryView.this, HistoryView.this, HistoryView.this, null);
//            mainController.test(HistoryView.this, HistoryView.this, null, HistoryView.this, null);
            }
        });

        Button searchGoogle = findViewById(R.id.searchGoogle);
        searchGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap hashMap = new HashMap();
                hashMap.put("searchItem", "ipad");
                mainController.searchSite(HistoryView.this, "ebay", hashMap);
            }
        });

        Button askBobOpenApp = findViewById(R.id.askBobOpenApp);
        askBobOpenApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
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
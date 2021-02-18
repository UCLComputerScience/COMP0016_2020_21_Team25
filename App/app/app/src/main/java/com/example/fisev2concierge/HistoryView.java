package com.example.fisev2concierge;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.backendConnectivity.Backend;
import com.example.fisev2concierge.controller.MainController;

public class HistoryView extends AppCompatActivity {
    public Button button;
    private static final int REQUEST_CALL = 3;
    private static final int REQUEST_MSG = 4;
    MainController mainController = new MainController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_view);

        Button callButton = findViewById(R.id.callButton);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainController.makeCall(HistoryView.this, HistoryView.this, "012345678910");
            }
        });

        Button msgButton = findViewById(R.id.msgButton);
        msgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainController.sendText(HistoryView.this, HistoryView.this, "012345678910", "Test");
            }
        });

        Button appButton = findViewById(R.id.openApp);
        appButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainController.openApp(HistoryView.this, HistoryView.this, "com.android.settings");
            }
        });

        Button webButton = findViewById(R.id.openWeb);
        webButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainController.openWebsite(HistoryView.this, "http://10.0.2.2:8080/servicedata?id=28");
            }
        });
        
        Button localhostButton = findViewById(R.id.localhostButton);
        localhostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("MainControllerResult : " + mainController.backendServices("servicedata", "28").toString());
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
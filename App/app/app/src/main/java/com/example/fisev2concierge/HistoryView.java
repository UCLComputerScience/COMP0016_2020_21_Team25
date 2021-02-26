package com.example.fisev2concierge;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.controllers.MainController;
import com.example.fisev2concierge.functionalityClasses.SearchContacts;

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
                mainController.openApp(HistoryView.this, HistoryView.this, "youtube");
            }
        });

        Button webButton = findViewById(R.id.openWeb);
        webButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainController.openWebsite(HistoryView.this, "https://www.amazon.co.uk/");
            }
        });
        
        Button localhostButton = findViewById(R.id.localhostButton);
        localhostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("MainControllerResult : " + mainController.backendServices("servicedata", "28").toString());
            }
        });

        Button askBobButton = findViewById(R.id.askBobButton);
        askBobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> result = mainController.askBobServices("query", "message=\"tell me a joke\"&sender=\"user\"");
                System.out.println("AskBobResult: " + result.toString());
            }
        });

        Button addContact = findViewById(R.id.addContactButton);
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchContacts searchContacts = new SearchContacts(HistoryView.this, HistoryView.this, HistoryView.this);
                searchContacts.addContacts("Bob 1", "05688797897");
            }
        });

        Button viewContacts = findViewById(R.id.viewContactsButton);
        viewContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchContacts searchContacts = new SearchContacts(HistoryView.this, HistoryView.this, HistoryView.this);
                searchContacts.searchContacts("Bob 1");
            }
        });

        Button askBobOpenApp = findViewById(R.id.askBobOpenApp);
        askBobOpenApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap askBobResponse = mainController.askBobRequest("open settings");
                mainController.askBobController(askBobResponse, HistoryView.this, HistoryView.this, HistoryView.this);
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
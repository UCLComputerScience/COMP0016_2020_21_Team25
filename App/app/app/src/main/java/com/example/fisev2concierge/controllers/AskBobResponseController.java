package com.example.fisev2concierge.controllers;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.MainActivity;
import com.example.fisev2concierge.askBobConnectivity.AskBobResponseParser;
import com.example.fisev2concierge.functionalityClasses.CallFunctionality;
import com.example.fisev2concierge.functionalityClasses.OpenAppFunctionality;
import com.example.fisev2concierge.functionalityClasses.SearchContacts;
import com.example.fisev2concierge.functionalityClasses.SmsFunctionality;
import com.example.fisev2concierge.helperClasses.GetLocation;
import com.example.fisev2concierge.speech.SpeechSynthesis;

import java.util.ArrayList;
import java.util.HashMap;

public class AskBobResponseController {

    private AskBobResponseParser askBobResponseParser = new AskBobResponseParser();
    private MainController mainController = new MainController();

    public void responseController(HashMap parsedResponse, Context context, Activity activity, AppCompatActivity appCompatActivity, SpeechSynthesis speechSynthesis){

        String Service_Type = (String) parsedResponse.get("Service_Type");

        switch (Service_Type){
            case "API_CALL":
                speechSynthesis.runTts((String) parsedResponse.get("Response"));
                break;
            case "CALL_CONTACT":
                String callContact = (String) parsedResponse.get("Contact");
                String callNumber = mainController.searchContact(callContact, appCompatActivity, context, activity);
                if (!callNumber.equals("-1")) {
                    mainController.makeCall(context, activity, callNumber);
                }
                break;
            case "SMS_CONTACT":
                String smsContact = (String) parsedResponse.get("Contact");
                String smsNumber = mainController.searchContact(smsContact, appCompatActivity, context, activity);
                if (!smsNumber.equals("-1")) {
                    mainController.sendText(context, activity, smsNumber, "test");
                    mainController.openApp(appCompatActivity, context, "messaging");
                }
                break;
            case "OPEN_APP":
                String appName = (String) parsedResponse.get("Application");
                mainController.openApp(appCompatActivity, context, appName.toLowerCase());
                break;
            case "SEARCH_SITE":
                String postcode = mainController.getLocation(context, activity);
                HashMap hashMap = new HashMap();
                hashMap.put("location", postcode);
            case "ERROR":
                speechSynthesis.runTts((String) parsedResponse.get("text"));
                Toast.makeText(context, "Command not understood", Toast.LENGTH_SHORT).show();
                break;
            default:
                //Improve error dealing
                Toast.makeText(context, "Error with parsing", Toast.LENGTH_SHORT).show();
                System.out.println("Error with parsing");
        }
    }
}

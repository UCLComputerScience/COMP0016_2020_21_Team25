package com.example.fisev2concierge.controllers;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.localApis.askBobConnectivity.AskBobResponseParser;
import com.example.fisev2concierge.speech.SpeechSynthesis;

import java.util.HashMap;

public class AskBobResponseController {

    private AskBobResponseParser askBobResponseParser = new AskBobResponseParser();
    private MainController mainController = new MainController();

    public void responseController(HashMap parsedResponse, Context context, Activity activity, AppCompatActivity appCompatActivity, SpeechSynthesis speechSynthesis){

        String service_type = (String) parsedResponse.get("Service_Type");
        if (service_type.equals("API_CALL")){
            if (parsedResponse.get("Service").toString().equals("Transport")){
                String url = mainController.searchUrlLookup("maps_location");
                url = url.replace("{lat}", parsedResponse.get("lat").toString());
                url = url.replace("{lon}", parsedResponse.get("lon").toString());
                speechSynthesis.runTts((String) parsedResponse.get("Message"));
                mainController.openUrl(appCompatActivity, url);
            } else {
                speechSynthesis.runTts((String) parsedResponse.get("Response"));
            }
        } else {
            switch (parsedResponse.get("Service").toString()) {
                case "Call Contact":
                    String callContact = (String) parsedResponse.get("Contact");
                    String callNumber = mainController.searchContact(callContact, appCompatActivity, context, activity);
                    if (!callNumber.equals("-1")) {
                        speechSynthesis.runTts((String) parsedResponse.get("Response"));
                        mainController.makeCall(context, activity, callNumber);
                    }
                    break;
                case "SMS Contact":
                    String smsContact = (String) parsedResponse.get("Contact");
                    String smsNumber = mainController.searchContact(smsContact, appCompatActivity, context, activity);
                    if (!smsNumber.equals("-1")) {
                        speechSynthesis.runTts((String) parsedResponse.get("Response"));
                        mainController.sendText(context, activity, smsNumber, "test");
                        mainController.openApp(appCompatActivity, context, "messaging");
                    }
                    break;
                case "Open App":
                    String appName = (String) parsedResponse.get("Application");
                    System.out.println("Controller: appName: " + appName);
                    mainController.openApp(appCompatActivity, context, appName.toLowerCase());
                    break;
                case "Shop Search":
                    String websiteName = (String) parsedResponse.get("Shop");
                    websiteName = websiteName.toLowerCase();
                    speechSynthesis.runTts((String) parsedResponse.get("Response"));
                    mainController.searchSite(appCompatActivity, websiteName, parsedResponse);
                    break;
                case "Yell Search":
                    websiteName = "yell";
                    speechSynthesis.runTts((String) parsedResponse.get("Response"));
                    mainController.searchSite(appCompatActivity, websiteName, parsedResponse);
                    break;
                case "Navigate App":
                    String activityName = (String) parsedResponse.get("Application");
                    mainController.openActivity(appCompatActivity, context, activityName);
                    break;
                case "ERROR":
                    speechSynthesis.runTts((String) parsedResponse.get("text"));
                    Toast.makeText(context, "Command not understood", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    //Improve error dealing
                    Toast.makeText(context, "Error: unknown command: " + parsedResponse.get("Service").toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}

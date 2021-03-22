package com.example.fisev2concierge.controllers;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.localApis.askBobConnectivity.AskBobResponseParser;
import com.example.fisev2concierge.speech.SpeechSynthesis;

import org.json.JSONArray;

import java.net.URL;
import java.util.HashMap;

public class AskBobResponseController {

    private MainController mainController = new MainController();

    public void responseController(HashMap parsedResponse, Context context, Activity activity, AppCompatActivity appCompatActivity, SpeechSynthesis speechSynthesis){

        String service_type = (String) parsedResponse.get("Service_Type");
        if (service_type.equals("API_CALL")){
            handleApiResponse(parsedResponse, speechSynthesis, appCompatActivity);
        } else {
            switch (parsedResponse.get("Service").toString()) {
                case "Call Contact":
                    handleCallResponse(parsedResponse, appCompatActivity, context, activity, speechSynthesis);
                    break;
                case "SMS Contact":
                    handleMessageResponse(parsedResponse, appCompatActivity, context, activity, speechSynthesis);
                    break;
                case "Open App":
                    handleOpenAppResponse(parsedResponse, appCompatActivity, context);
                    break;
                case "Shop Search":
                    handleShopSearchResponse(parsedResponse, appCompatActivity, speechSynthesis);
                    break;
                case "Yell Search":
                    handleYellSearchResponse(parsedResponse, appCompatActivity, speechSynthesis);
                    break;
                case "Navigate App":
                    handleNavigateAppResponse(parsedResponse, appCompatActivity, context);
                    break;
                case "ERROR":
                    handleErrorResponse(parsedResponse, context, speechSynthesis);
                    break;
                default:
                    //Improve error dealing
                    Toast.makeText(context, "Error: unknown command: " + parsedResponse.get("Service").toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void handleApiResponse(HashMap parsedResponse, SpeechSynthesis speechSynthesis, AppCompatActivity appCompatActivity){
        if (parsedResponse.get("Service").toString().equals("Transport") && parsedResponse.containsKey("Message")){
            handleApiTransportResponse(parsedResponse, speechSynthesis, appCompatActivity);
        } else if (parsedResponse.get("Service").toString().equals("Recipes") && parsedResponse.containsKey("Steps")) {
            handleApiRecipesWithStepsResponse(parsedResponse, speechSynthesis, appCompatActivity);
        } else if (parsedResponse.get("Service").toString().equals("Books")) {
            handleApiBooksResponse(parsedResponse, speechSynthesis, appCompatActivity);
        } else {
            speechSynthesis.runTts((String) parsedResponse.get("Response"));
        }
    }

    private void handleApiTransportResponse(HashMap parsedResponse, SpeechSynthesis speechSynthesis, AppCompatActivity appCompatActivity){
        String url = mainController.searchUrlLookup("maps_location");
        url = url.replace("{lat}", parsedResponse.get("lat").toString());
        url = url.replace("{lon}", parsedResponse.get("lon").toString());
        speechSynthesis.runTts((String) parsedResponse.get("Message"));
        mainController.openUrl(appCompatActivity, url);
    }

    private void handleApiRecipesWithStepsResponse(HashMap parsedResponse, SpeechSynthesis speechSynthesis, AppCompatActivity appCompatActivity){
        speechSynthesis.runTts((String) parsedResponse.get("Response"));
        speechSynthesis.runTts(parsedResponse.get("Steps").toString());
    }

    private void handleApiBooksResponse(HashMap parsedResponse, SpeechSynthesis speechSynthesis, AppCompatActivity appCompatActivity){
        try {
            URL url = new URL(parsedResponse.get("Response").toString());
            mainController.openUrl(appCompatActivity, parsedResponse.get("Response").toString());
        } catch (Exception e){
            speechSynthesis.runTts((String) parsedResponse.get("Response"));
        }
    }

    private void handleCallResponse(HashMap parsedResponse, AppCompatActivity appCompatActivity, Context context, Activity activity, SpeechSynthesis speechSynthesis){
        String callContact = (String) parsedResponse.get("Contact");
        String callNumber = mainController.searchContact(callContact, appCompatActivity, context, activity);
        if (!callNumber.equals("-1")) {
            speechSynthesis.runTts((String) parsedResponse.get("Response"));
            mainController.makeCall(context, activity, callNumber);
        } else {
            speechSynthesis.runTts("No such contact found");
        }
    }

    private void handleMessageResponse(HashMap parsedResponse, AppCompatActivity appCompatActivity, Context context, Activity activity, SpeechSynthesis speechSynthesis){
        String smsContact = (String) parsedResponse.get("Contact");
        String smsNumber = mainController.searchContact(smsContact, appCompatActivity, context, activity);
        if (!smsNumber.equals("-1")) {
            speechSynthesis.runTts((String) parsedResponse.get("Response"));
            mainController.sendText(context, activity, smsNumber, "Hello");
//            mainController.openApp(appCompatActivity, context, "messaging");
        } else {
            speechSynthesis.runTts("No such contact found");
        }
    }

    private void handleOpenAppResponse(HashMap parsedResponse, AppCompatActivity appCompatActivity, Context context){
        String appName = (String) parsedResponse.get("Application");
        mainController.openApp(appCompatActivity, context, appName.toLowerCase());
    }

    private void handleShopSearchResponse(HashMap parsedResponse, AppCompatActivity appCompatActivity, SpeechSynthesis speechSynthesis){
        String websiteName = (String) parsedResponse.get("Shop");
        websiteName = websiteName.toLowerCase();
        speechSynthesis.runTts((String) parsedResponse.get("Response"));
        mainController.searchSite(appCompatActivity, websiteName, parsedResponse);
    }

    private void handleYellSearchResponse(HashMap parsedResponse, AppCompatActivity appCompatActivity, SpeechSynthesis speechSynthesis){
        String websiteName = "yell";
        speechSynthesis.runTts((String) parsedResponse.get("Response"));
        mainController.searchSite(appCompatActivity, websiteName, parsedResponse);
    }

    private void handleNavigateAppResponse(HashMap parsedResponse, AppCompatActivity appCompatActivity, Context context){
        String activityName = (String) parsedResponse.get("Application");
        mainController.openActivity(appCompatActivity, context, activityName);
    }

    private void handleErrorResponse(HashMap parsedResponse, Context context, SpeechSynthesis speechSynthesis){
        speechSynthesis.runTts((String) parsedResponse.get("text"));
        Toast.makeText(context, "Command not understood", Toast.LENGTH_SHORT).show();
    }
}

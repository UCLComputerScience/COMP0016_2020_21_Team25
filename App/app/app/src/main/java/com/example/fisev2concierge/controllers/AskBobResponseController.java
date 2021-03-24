package com.example.fisev2concierge.controllers;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.speech.SpeechSynthesis;

import java.net.URL;
import java.util.HashMap;
import java.util.Objects;

public class AskBobResponseController {

    private final MainController mainController = new MainController();

    public void responseController(HashMap<String, String> parsedResponse, Context context, Activity activity, AppCompatActivity appCompatActivity, SpeechSynthesis speechSynthesis){

        String service_type = (String) parsedResponse.get("Service_Type");
        assert service_type != null;
        if (service_type.equals("API_CALL")){
            handleApiResponse(parsedResponse, speechSynthesis, appCompatActivity);
        } else {
            switch (Objects.requireNonNull(parsedResponse.get("Service"))) {
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
                    Toast.makeText(context, "Error: unknown command: " + parsedResponse.get("Service"), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void handleApiResponse(HashMap<String, String> parsedResponse, SpeechSynthesis speechSynthesis, AppCompatActivity appCompatActivity){
        if (parsedResponse.get("Service").equals("Transport") && parsedResponse.containsKey("Message")){
            handleApiTransportResponse(parsedResponse, speechSynthesis, appCompatActivity);
        } else if (parsedResponse.get("Service").equals("Recipes") && parsedResponse.containsKey("Steps")) {
            handleApiRecipesWithStepsResponse(parsedResponse, speechSynthesis);
        } else if (parsedResponse.get("Service").equals("Books")) {
            handleApiBooksResponse(parsedResponse, speechSynthesis, appCompatActivity);
        } else {
            speechSynthesis.runTts((String) parsedResponse.get("Response"));
        }
    }

    private void handleApiTransportResponse(HashMap<String, String> parsedResponse, SpeechSynthesis speechSynthesis, AppCompatActivity appCompatActivity){
        String url = mainController.searchUrlLookup("maps_location");
        url = url.replace("{lat}", Objects.requireNonNull(parsedResponse.get("lat")));
        url = url.replace("{lon}", Objects.requireNonNull(parsedResponse.get("lon")));
        speechSynthesis.runTts((String) parsedResponse.get("Message"));
        mainController.openUrl(appCompatActivity, url);
    }

    private void handleApiRecipesWithStepsResponse(HashMap<String, String> parsedResponse, SpeechSynthesis speechSynthesis){
        speechSynthesis.runTts((String) parsedResponse.get("Response"));
        speechSynthesis.runTts(parsedResponse.get("Steps"));
    }

    private void handleApiBooksResponse(HashMap<String, String> parsedResponse, SpeechSynthesis speechSynthesis, AppCompatActivity appCompatActivity){
        try {
            new URL(parsedResponse.get("Response"));
            mainController.openUrl(appCompatActivity, parsedResponse.get("Response"));
        } catch (Exception e){
            speechSynthesis.runTts((String) parsedResponse.get("Response"));
        }
    }

    private void handleCallResponse(HashMap<String, String> parsedResponse, AppCompatActivity appCompatActivity, Context context, Activity activity, SpeechSynthesis speechSynthesis){
        String callContact = (String) parsedResponse.get("Contact");
        String callNumber = mainController.searchContact(callContact, appCompatActivity, context, activity);
        if (!callNumber.equals("-1")) {
            speechSynthesis.runTts((String) parsedResponse.get("Response"));
            mainController.makeCall(context, activity, callNumber);
        } else {
            speechSynthesis.runTts("No such contact found");
        }
    }

    private void handleMessageResponse(HashMap<String, String> parsedResponse, AppCompatActivity appCompatActivity, Context context, Activity activity, SpeechSynthesis speechSynthesis){
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

    private void handleOpenAppResponse(HashMap<String, String> parsedResponse, AppCompatActivity appCompatActivity, Context context){
        String appName = (String) parsedResponse.get("Application");
        assert appName != null;
        mainController.openApp(appCompatActivity, context, appName.toLowerCase());
    }

    private void handleShopSearchResponse(HashMap<String, String> parsedResponse, AppCompatActivity appCompatActivity, SpeechSynthesis speechSynthesis){
        String websiteName = (String) parsedResponse.get("Shop");
        assert websiteName != null;
        websiteName = websiteName.toLowerCase();
        speechSynthesis.runTts((String) parsedResponse.get("Response"));
        mainController.searchSite(appCompatActivity, websiteName, parsedResponse);
    }

    private void handleYellSearchResponse(HashMap<String, String> parsedResponse, AppCompatActivity appCompatActivity, SpeechSynthesis speechSynthesis){
        String websiteName = "yell";
        speechSynthesis.runTts((String) parsedResponse.get("Response"));
        mainController.searchSite(appCompatActivity, websiteName, parsedResponse);
    }

    private void handleNavigateAppResponse(HashMap<String, String> parsedResponse, AppCompatActivity appCompatActivity, Context context){
        String activityName = (String) parsedResponse.get("Application");
        mainController.openActivity(appCompatActivity, context, activityName);
    }

    private void handleErrorResponse(HashMap<String, String> parsedResponse, Context context, SpeechSynthesis speechSynthesis){
        speechSynthesis.runTts((String) parsedResponse.get("text"));
        Toast.makeText(context, "Command not understood", Toast.LENGTH_SHORT).show();
    }
}

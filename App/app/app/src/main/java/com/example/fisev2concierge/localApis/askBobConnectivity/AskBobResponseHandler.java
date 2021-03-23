package com.example.fisev2concierge.localApis.askBobConnectivity;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.controllers.MainController;
import com.example.fisev2concierge.speech.SpeechSynthesis;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AskBobResponseHandler {
    private final HashMap<String, String> askBobResponse;
    private final AppCompatActivity appCompatActivity;
    private final Context context;
    private final Activity activity;
    private final SpeechSynthesis speechSynthesis;
    private final MainController mainController = new MainController();

    public AskBobResponseHandler(HashMap<String, String> askBobResponse, AppCompatActivity appCompatActivity, Context context, Activity activity, SpeechSynthesis speechSynthesis){
        this.askBobResponse = askBobResponse;
        this.appCompatActivity = appCompatActivity;
        this.context = context;
        this.activity = activity;
        this.speechSynthesis = speechSynthesis;
    }

    public void handleResponse(){
        if (askBobResponse.get("Service").equals("Yell Search")){
            mainController.getLatLon(context, activity, askBobResponse, appCompatActivity, speechSynthesis);
        } else {
            if (askBobResponse.get("Service_Type").equals("API_CALL")) {
                handleApiResponse(askBobResponse, appCompatActivity, context, activity, speechSynthesis);
            } else {
                mainController.askBobController(askBobResponse, context, activity, appCompatActivity, speechSynthesis);
            }
        }
    }

    private void handleApiResponse(HashMap<String, String> askBobResponse, AppCompatActivity appCompatActivity, Context context, Activity activity, SpeechSynthesis speechSynthesis){
        if (mainController.hasUserID(context)) {
            handleApiResponseIfUserIdExist(askBobResponse, appCompatActivity, context, activity, speechSynthesis);
        } else {
            handleApiResponseIfNoUserIdExists(askBobResponse, appCompatActivity, context, activity, speechSynthesis);
        }
    }

    private void handleApiResponseIfUserIdExist(HashMap<String, String> askBobResponse, AppCompatActivity appCompatActivity, Context context, Activity activity, SpeechSynthesis speechSynthesis){
        String service = askBobResponse.get("Service");
        ArrayList<String> services = mainController.backendServices("getServices", mainController.getUserID(context), appCompatActivity);
//                            ArrayList<String> services = backendServices("getServices", "101");
        String json = services.get(0);
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("services");
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.get(i).toString().equals(service)){
                    if (askBobResponse.get("Service").equals("Transport") && askBobResponse.containsKey("Transport Type")){
                        mainController.getLatLon(context, activity, askBobResponse, appCompatActivity, speechSynthesis);
                    } else {
                        mainController.askBobController(askBobResponse, context, activity, appCompatActivity, speechSynthesis);
                        mainController.backendServices("addHistory", service + "&user_id=" + mainController.getUserID(context), appCompatActivity);
                    }
                    return;
                }
            }
            Toast.makeText(context, "Service not added by admin", Toast.LENGTH_SHORT).show();
            speechSynthesis.runTts("Service " + service +  " not added by admin");
        } catch (Exception e) {
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void handleApiResponseIfNoUserIdExists(HashMap<String, String> askBobResponse, AppCompatActivity appCompatActivity, Context context, Activity activity, SpeechSynthesis speechSynthesis){
        if (askBobResponse.get("Service").equals("Transport") && askBobResponse.containsKey("Transport Type")) {
            mainController.getLatLon(context, activity, askBobResponse, appCompatActivity, speechSynthesis);
        } else {
            mainController.askBobController(askBobResponse, context, activity, appCompatActivity, speechSynthesis);
        }
    }
}

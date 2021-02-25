package com.example.fisev2concierge.controllers;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.MainActivity;
import com.example.fisev2concierge.askBobConnectivity.AskBobResponseParser;
import com.example.fisev2concierge.functionalityClasses.CallFunctionality;
import com.example.fisev2concierge.functionalityClasses.OpenAppFunctionality;
import com.example.fisev2concierge.functionalityClasses.SearchContacts;
import com.example.fisev2concierge.functionalityClasses.SmsFunctionality;

import java.util.ArrayList;
import java.util.HashMap;

public class AskBobResponseController {

    private AskBobResponseParser askBobResponseParser = new AskBobResponseParser();
    private MainController mainController = new MainController();

    public void responseController(HashMap parsedResponse, Context context, Activity activity, AppCompatActivity appCompatActivity){

        String Service_Type = (String) parsedResponse.get("Service_Type");
        SearchContacts searchContacts = new SearchContacts();

        switch (Service_Type){
            //Don't deal with API_CALL as this is managed directly by MainActivity
            case "CALL_CONTACT":
                String callContact = (String) parsedResponse.get("Contact");
                CallFunctionality callFunctionality = new CallFunctionality(context, activity);
                String callNumber = searchContacts.searchContacts(callContact);
                callFunctionality.makePhoneCall(callNumber);
                break;
            case "SMS_CONTACT":
//                String smsContact = (String) parsedResponse.get("Contact");
//                SmsFunctionality smsFunctionality = new SmsFunctionality(context, activity);
//                String smsNumber = searchContacts.searchContacts(smsContact);
//                smsFunctionality.sendSMS(smsNumber, "test");
                OpenAppFunctionality openAppFunctionality = new OpenAppFunctionality(appCompatActivity, context);
                openAppFunctionality.openApp("messages");
                break;
            case "OPEN_APP":
                //code for opening app
            default:
                //Improve error dealing
                System.out.println("Error");
        }
    }
}

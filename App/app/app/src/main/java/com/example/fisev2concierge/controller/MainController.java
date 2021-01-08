package com.example.fisev2concierge.controller;

import com.example.fisev2concierge.service.servicehandler.ServiceModel;

import java.util.HashMap;

public class MainController implements Runnable{

//    private volatile String result;

    public void run(){
        ServiceModel serviceModel = new ServiceModel();
        HashMap hashMap = new HashMap();
        hashMap.put("CITY_NAME", "London");
        hashMap.put("COUNTRY_CODE", "uk");
        hashMap.put("LANGUAGE", "en");
//        result = serviceModel.makeRequest("Weather", hashMap);
    }
//    public String getResult(){
//        return result;
//    }

    //Method for API Calls
    public String apiRequest(String apiName, HashMap param){
        String result;
        ServiceModel serviceModel = new ServiceModel();
        HashMap hashMap = new HashMap();
        hashMap.put("CITY_NAME", "London");
        hashMap.put("COUNTRY_CODE", "uk");
        hashMap.put("LANGUAGE", "en");
        result = serviceModel.makeRequest("Weather", hashMap);
//        result = serviceModel.makeRequest(apiName, param);
        return result;
    }

    //Method for Making reminders

    //Method for Making alarams

    //Method for setting timers

    //Method for adding requests to history

    //Method for calling NLP algorithm

    //There should be a method for running speech recognition and synthesis here but this doesn't work so keep it on the main page

}

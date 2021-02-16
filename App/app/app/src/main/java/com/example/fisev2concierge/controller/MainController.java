package com.example.fisev2concierge.controller;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.Cursor;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.HistoryView;
import com.example.fisev2concierge.functionalityClasses.AlarmsFunctionality;
import com.example.fisev2concierge.functionalityClasses.CallFunctionality;
import com.example.fisev2concierge.functionalityClasses.OpenAppFunctionality;
import com.example.fisev2concierge.functionalityClasses.OpenWebsiteFunctionality;
import com.example.fisev2concierge.functionalityClasses.RemindersFunctionality;
import com.example.fisev2concierge.functionalityClasses.SmsFunctionality;
import com.example.fisev2concierge.service.servicehandler.ServiceModel;

import java.util.Calendar;
import java.util.HashMap;

public class MainController implements Runnable{

//    private volatile String result;

    public void run(){
        ServiceModel serviceModel = new ServiceModel();
        HashMap hashMap = new HashMap();
        hashMap.put("CITY_NAME", "London");
        hashMap.put("COUNTRY_CODE", "uk");
        hashMap.put("LANGUAGE", "en");
        String result = serviceModel.makeRequest("Weather", hashMap);
        System.out.println(result);
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
    public Cursor getReminders(Context context){
        RemindersFunctionality remindersFunctionality = new RemindersFunctionality(context);
        Cursor cursor = remindersFunctionality.getReminders();
        return cursor;
    }
    public boolean addReminder(Context context, String reminder){
        RemindersFunctionality remindersFunctionality = new RemindersFunctionality(context);
        boolean dataInsert = remindersFunctionality.addReminder(reminder);
        return dataInsert;
    }

    public void updateReminder(Context context, String selectedReminder, int selectedID, String item){
        RemindersFunctionality remindersFunctionality = new RemindersFunctionality(context);
        remindersFunctionality.updateReminder(selectedReminder, selectedID, item);
    }

    public void deleteReminder(Context context, int selectedID, String selectedReminder){
        RemindersFunctionality remindersFunctionality = new RemindersFunctionality(context);
        remindersFunctionality.deleteReminder(selectedID, selectedReminder);
    }

    //Method for Making alarams
    public Cursor getAlarm(Context context){
        AlarmsFunctionality alarmsFunctionality = new AlarmsFunctionality(context);
        Cursor cursor = alarmsFunctionality.getAlarms();
        return cursor;
    }

    public boolean addAlarm(Context context, String newEntry, String date){
        AlarmsFunctionality alarmsFunctionality = new AlarmsFunctionality(context);
        boolean dataInserted = alarmsFunctionality.addAlarm(newEntry, date);
        return dataInserted;
    }

    public void updateAlarm(Context context, String selectedAlarm, int selectedID, String item, String date){
        AlarmsFunctionality alarmsFunctionality = new AlarmsFunctionality(context);
        alarmsFunctionality.updateAlarm(selectedAlarm, selectedID, item, date);
    }

    public void deleteAlarm(Context context, int selectedID, String selectedAlarm){
        AlarmsFunctionality alarmsFunctionality = new AlarmsFunctionality(context);
        alarmsFunctionality.deleteAlarm(selectedID, selectedAlarm);
    }

    public Cursor getRecentAlarm(Context context){
        AlarmsFunctionality alarmsFunctionality = new AlarmsFunctionality(context);
        Cursor cursor = alarmsFunctionality.getRecent();
        return cursor;
    }

    public void startAlarm(AppCompatActivity appCompatActivity, Context context, String id, Calendar c){
        AlarmsFunctionality alarmsFunctionality = new AlarmsFunctionality(context);
        alarmsFunctionality.startAlarm(appCompatActivity, context, id, c);
    }

    public void stopAlarm(AppCompatActivity appCompatActivity, Context context, int id){
        AlarmsFunctionality alarmsFunctionality = new AlarmsFunctionality(context);
        alarmsFunctionality.stopAlarm(appCompatActivity, context, id);
    }

    //Method for setting timers

    //Method for adding requests to history

    //Method for calling NLP algorithm

    //There should be a method for running speech recognition and synthesis here but this doesn't work so keep it on the main page

    //Method for making calls
    public void makeCall(Context context, Activity activity, String number){
        CallFunctionality callFunctionality = new CallFunctionality(context, activity);
        callFunctionality.makePhoneCall(number);
    }

    //Method for sending texts
    public void sendText(Context context, Activity activity, String number, String message){
        SmsFunctionality smsFunctionality = new SmsFunctionality(context, activity);
        smsFunctionality.sendSMS(number, message);
    }

    //Method for opening apps
    public void openApp(AppCompatActivity appCompatActivity, Context context, String app){
        OpenAppFunctionality openAppFunctionality = new OpenAppFunctionality(appCompatActivity, context);
        openAppFunctionality.openApp(app);
    }

    //Method for opening websites
    public void openWebsite(AppCompatActivity appCompatActivity, String website){
        OpenWebsiteFunctionality openWebsiteFunctionality = new OpenWebsiteFunctionality(appCompatActivity);
        openWebsiteFunctionality.openWeb(website);
    }

}

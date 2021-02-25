package com.example.fisev2concierge.controllers;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.askBobConnectivity.AskBob;
import com.example.fisev2concierge.askBobConnectivity.AskBobRequest;
import com.example.fisev2concierge.askBobConnectivity.AskBobResponseParser;
import com.example.fisev2concierge.backendConnectivity.Backend;
import com.example.fisev2concierge.functionalityClasses.AlarmsFunctionality;
import com.example.fisev2concierge.functionalityClasses.CallFunctionality;
import com.example.fisev2concierge.functionalityClasses.OpenAppFunctionality;
import com.example.fisev2concierge.functionalityClasses.OpenWebsiteFunctionality;
import com.example.fisev2concierge.functionalityClasses.RemindersFunctionality;
import com.example.fisev2concierge.functionalityClasses.SmsFunctionality;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MainController{

    public HashMap askBobRequest(String sTT){
        AskBobRequest askBobRequest = new AskBobRequest();
        return askBobRequest.makeRequest(sTT);
    }

    public HashMap parseAskBobResponse(ArrayList<String> response){
        AskBobResponseParser parser = new AskBobResponseParser();
        return parser.parse(response);
    }

    public void askBobController(HashMap parsedResponse, Context context, Activity activity, AppCompatActivity appCompatActivity){
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        askBobResponseController.responseController(parsedResponse, context, activity, appCompatActivity);
    }

    //BackendServices
    public ArrayList<String> backendServices(String method, String parameter){
        Backend backend = new Backend(method, parameter);
        Thread thread = new Thread(backend);
        thread.start();
        return backend.getResult();
    }

    //AskBob Services
    public ArrayList<String> askBobServices(String method, String parameters){
        AskBob askBob = new AskBob(method, parameters);
        Thread thread = new Thread(askBob);
        thread.start();
        return askBob.getResult();
    }

    //Reminders
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

    //Alarams
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

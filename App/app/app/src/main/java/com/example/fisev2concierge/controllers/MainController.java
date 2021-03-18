package com.example.fisev2concierge.controllers;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.localApis.askBobConnectivity.AskBob;
import com.example.fisev2concierge.localApis.askBobConnectivity.AskBobRequest;
import com.example.fisev2concierge.localApis.askBobConnectivity.AskBobResponseParser;
import com.example.fisev2concierge.localApis.backendConnectivity.Backend;
import com.example.fisev2concierge.functionalityClasses.AlarmsFunctionality;
import com.example.fisev2concierge.functionalityClasses.CallFunctionality;
import com.example.fisev2concierge.functionalityClasses.OpenActivity;
import com.example.fisev2concierge.functionalityClasses.OpenAppFunctionality;
import com.example.fisev2concierge.functionalityClasses.OpenUrlFunctionality;
import com.example.fisev2concierge.functionalityClasses.RemindersFunctionality;
import com.example.fisev2concierge.functionalityClasses.SearchContacts;
import com.example.fisev2concierge.functionalityClasses.SmsFunctionality;
import com.example.fisev2concierge.helperClasses.AppPackageNameLookup;
import com.example.fisev2concierge.functionalityClasses.GetLatLon;
import com.example.fisev2concierge.localApis.GetLocation;
import com.example.fisev2concierge.helperClasses.SearchUrlLookup;
import com.example.fisev2concierge.helperClasses.WebsiteUrlLookup;
import com.example.fisev2concierge.model.AdminDbHelper;
import com.example.fisev2concierge.speech.SpeechSynthesis;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MainController{

    public void openActivity(AppCompatActivity appCompatActivity, Context context, String activityName){
        OpenActivity openActivity = new OpenActivity(appCompatActivity, context);
        openActivity.openActivity(activityName);
    }

    //need to change this name
    public void test(Context context, Activity activity, HashMap parsedResponse, AppCompatActivity appCompatActivity, SpeechSynthesis speechSynthesis){
        GetLatLon test = new GetLatLon(context, activity, parsedResponse, appCompatActivity, speechSynthesis);
        test.searchLatLon();
    }

    public void handleUserRequest(String[] userRequest, SpeechSynthesis speechSynthesis, AppCompatActivity appCompatActivity, Context context, Activity activity, TextView conciergeStatusText){
        if (userRequest[0].length()>0) {
            conciergeStatusText.setText(userRequest[0]);
            HashMap askBobResponse = askBobRequest(userRequest[0]);
            //add a check here, if we require searching a site then call getLatLon which in turn should add location to the hashmap and then call askBobController itself
            if (askBobResponse.containsKey("Service")){
                if (askBobResponse.get("Service").equals("Yell Search")){
                    test(context, activity, askBobResponse, appCompatActivity, speechSynthesis);
                } else {
                    if (askBobResponse.get("Service_Type").equals("API_CALL")){
                        String service = askBobResponse.get("Service").toString();
                        System.out.println("service: " + service);
                        if (hasUserID(context)) {
                            ArrayList<String> services = backendServices("getServices", getUserID(context));
//                            ArrayList<String> services = backendServices("getServices", "101");
                            String json = services.get(0);
                            try {
                                JSONObject jsonObject = new JSONObject(json);
                                JSONArray jsonArray = jsonObject.getJSONArray("services");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    System.out.println("service: " + jsonArray.get(i).toString());
                                    if (jsonArray.get(i).toString().equals(service)){
                                        if (askBobResponse.get("Service").equals("Transport")){
                                            test(context, activity, askBobResponse, appCompatActivity, speechSynthesis);
                                        } else {
                                            askBobController(askBobResponse, context, activity, appCompatActivity, speechSynthesis);
                                            backendServices("addHistory", service + "&user_id=" + getUserID(context));
                                        }
                                        return;
                                    }
                                }
                                Toast.makeText(context, "Service not authorised by admin", Toast.LENGTH_SHORT).show();
                                speechSynthesis.runTts("Service " + service +  " not authorised by admin");
                            } catch (Exception e){
                                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            System.out.println("no user id");
                            if (askBobResponse.containsKey("Service")) {
                                if (askBobResponse.get("Service").equals("Transport")) {
                                    test(context, activity, askBobResponse, appCompatActivity, speechSynthesis);
                                } else {
                                    askBobController(askBobResponse, context, activity, appCompatActivity, speechSynthesis);
                                }
                            } else {
                                askBobController(askBobResponse, context, activity, appCompatActivity, speechSynthesis);
                            }
                        }
                    } else {
                        askBobController(askBobResponse, context, activity, appCompatActivity, speechSynthesis);
                    }
                }
            }
        } else {
            conciergeStatusText.setText("Concierge is off");
            Toast.makeText(context, "Empty input", Toast.LENGTH_SHORT).show();
        }
    }

    public String getLocation(Context context, Activity activity, Double lat, Double lon){
        GetLocation getLocation = new GetLocation(context, activity, lat, lon);
        Thread thread = new Thread(getLocation);
        thread.start();
        return getLocation.getPostcode();
    }

    public String searchContact(String name, AppCompatActivity appCompatActivity, Context context, Activity activity){
        SearchContacts searchContacts = new SearchContacts(appCompatActivity, context, activity);
        return searchContacts.searchContacts(name);
    }

    public HashMap askBobRequest(String sTT){
        AskBobRequest askBobRequest = new AskBobRequest();
        return askBobRequest.makeRequest(sTT);
    }

    public HashMap parseAskBobResponse(ArrayList<String> response){
        AskBobResponseParser parser = new AskBobResponseParser();
        return parser.parse(response);
    }

    public void askBobController(HashMap parsedResponse, Context context, Activity activity, AppCompatActivity appCompatActivity, SpeechSynthesis speechSynthesis){
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        askBobResponseController.responseController(parsedResponse, context, activity, appCompatActivity, speechSynthesis);
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

    public boolean addUserID(Context context, String id){
        AdminDbHelper adminDbHelper = new AdminDbHelper(context);
        return adminDbHelper.addID(id);
    }

    public String getUserID(Context context){
        AdminDbHelper adminDbHelper = new AdminDbHelper(context);
        Cursor cursor = adminDbHelper.getData();
        cursor.moveToFirst();
        return cursor.getString(1);
    }

    public boolean hasUserID(Context context){
        AdminDbHelper adminDbHelper = new AdminDbHelper(context);
        Cursor cursor = adminDbHelper.getData();
        if (cursor.getCount() > 0) { return true;}
        return false;
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

    public void makeCall(Context context, Activity activity, String number){
        CallFunctionality callFunctionality = new CallFunctionality(context, activity);
        callFunctionality.makePhoneCall(number);
    }

    public void sendText(Context context, Activity activity, String number, String message){
        SmsFunctionality smsFunctionality = new SmsFunctionality(context, activity);
        smsFunctionality.sendSMS(number, message);
    }

    public void openApp(AppCompatActivity appCompatActivity, Context context, String app){
        OpenAppFunctionality openAppFunctionality = new OpenAppFunctionality(appCompatActivity, context);
        openAppFunctionality.openApp(app);
    }

    public void openWebsite(AppCompatActivity appCompatActivity, String website){
        OpenUrlFunctionality openUrlFunctionality = new OpenUrlFunctionality(appCompatActivity);
        openUrlFunctionality.openWeb(website);
    }

    public void openUrl(AppCompatActivity appCompatActivity, String url){
        OpenUrlFunctionality openUrlFunctionality = new OpenUrlFunctionality(appCompatActivity);
        openUrlFunctionality.openUrl(url);
    }

    public void searchSite(AppCompatActivity appCompatActivity, String website, HashMap searchItems){
        OpenUrlFunctionality openUrlFunctionality = new OpenUrlFunctionality(appCompatActivity);
        openUrlFunctionality.searchWeb(website, searchItems);
    }

    public String websiteUrlLookup(String website){
        WebsiteUrlLookup websiteUrlLookup = new WebsiteUrlLookup();
        return websiteUrlLookup.search(website);
    }

    public String searchUrlLookup(String website){
        SearchUrlLookup searchUrlLookup = new SearchUrlLookup();
        return searchUrlLookup.search(website);
    }

    public String packageNameLookup(AppCompatActivity appCompatActivity, String appName){
        AppPackageNameLookup appPackageNameLookup = new AppPackageNameLookup(appCompatActivity);
        return appPackageNameLookup.search(appName);
    }
}

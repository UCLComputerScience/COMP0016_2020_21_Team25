package com.example.fisev2concierge.command;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import com.example.fisev2concierge.service.servicehandler.ServiceModel;
import com.example.fisev2concierge.speech.SpeechSynthesis;

import java.nio.channels.AsynchronousChannelGroup;
import java.util.HashMap;

public class Command implements Runnable{
    //Wizard of Oz Model

    ServiceModel serviceModel = new ServiceModel();
    HashMap hashMap = new HashMap();
    AppCompatActivity appCompatActivity;
    EditText editText;
    private volatile String result;

    public void config(AppCompatActivity appCompatActivity, EditText editText){
        this.appCompatActivity = appCompatActivity;
        this.editText = editText;
    }

    public void run(){
        hashMap.put("CITY_NAME", "London");
        hashMap.put("COUNTRY_CODE", "uk");
        hashMap.put("LANGUAGE", "en");
        //remove editText -> only needed to ensure api is working
        result = serviceModel.makeRequest("Weather", hashMap);

        

    }
    public String getResult(){
        return result;
    }

}
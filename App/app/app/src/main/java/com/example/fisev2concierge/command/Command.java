package com.example.fisev2concierge.command;

import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.service.servicehandler.ServiceModel;
import com.example.fisev2concierge.speech.SpeechSynthesis;

import java.util.HashMap;

public class Command {
    //Wizard of Oz Model

    ServiceModel serviceModel = new ServiceModel();
    HashMap hashMap = new HashMap();
    AppCompatActivity appCompatActivity;
    EditText editText;

    public void config(AppCompatActivity appCompatActivity, EditText editText){
        this.appCompatActivity = appCompatActivity;
        this.editText = editText;
    }
    public void run(){
        hashMap.put("CITY_NAME", "London");
        hashMap.put("COUNTRY_CODE", "uk");
        hashMap.put("LANGUAGE", "en");
        //remove editText -> only needed to ensure api is working
        String result = serviceModel.makeRequest("Weather", hashMap);
        editText.setText("");
        editText.setHint(result);
        editText.setText(result);
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(appCompatActivity);
        speechSynthesis.runTts(appCompatActivity, result);
    }

}

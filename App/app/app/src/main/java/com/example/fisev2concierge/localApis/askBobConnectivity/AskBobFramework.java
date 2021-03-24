package com.example.fisev2concierge.localApis.askBobConnectivity;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.controllers.MainController;
import com.example.fisev2concierge.localApis.PostRequestFramework;

import java.util.ArrayList;

public class AskBobFramework{
    //baseUrl for testing on emulator
    //private String baseUrl = "http://10.0.2.2:8000/";

    private final AppCompatActivity appCompatActivity;

    public AskBobFramework(AppCompatActivity appCompatActivity){
        this.appCompatActivity = appCompatActivity;
    }

    private String getIp(SharedPreferences sharedpreferences){
        String ip = new MainController().findServerIp();
        if (ip.startsWith("/")) {
            ip = ip.substring(1);
        }
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("server_ip", ip);
        editor.apply();
        return ip;
    }

    public ArrayList<String> request(String path, String parameters){
        String baseUrl;
        SharedPreferences sharedpreferences = appCompatActivity.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        if (!sharedpreferences.contains("server_ip")){
            baseUrl = getIp(sharedpreferences);
        } else {
            baseUrl = sharedpreferences.getString("server_ip", "");
        }
        baseUrl = "http://" + baseUrl + ":8000/";
        PostRequestFramework postRequestFramework = new PostRequestFramework(baseUrl);
        return postRequestFramework.request(path, parameters);
    }
}

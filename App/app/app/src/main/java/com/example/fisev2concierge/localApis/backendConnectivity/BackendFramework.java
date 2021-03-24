package com.example.fisev2concierge.localApis.backendConnectivity;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.controllers.MainController;
import com.example.fisev2concierge.localApis.GetRequestFramework;

import java.util.ArrayList;

public class BackendFramework {
    //baseUrl for running on emulator
    //private String baseUrl = "http://10.0.2.2:8080/";

    private final AppCompatActivity appCompatActivity;

    public BackendFramework(AppCompatActivity appCompatActivity){
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

    public ArrayList<String> request(String path){
        String baseUrl;
        SharedPreferences sharedpreferences = appCompatActivity.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        if (!sharedpreferences.contains("server_ip")){
            baseUrl = getIp(sharedpreferences);
        } else {
            baseUrl = sharedpreferences.getString("server_ip", "");
        }
        baseUrl = "http://" + baseUrl + ":8100/";
        GetRequestFramework getRequestFramework = new GetRequestFramework(baseUrl);
        return getRequestFramework.makeRequest(path);
    }
}

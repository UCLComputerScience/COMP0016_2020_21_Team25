package com.example.fisev2concierge.localApis.askBobConnectivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.UI.MainActivity;
import com.example.fisev2concierge.controllers.MainController;
import com.example.fisev2concierge.localApis.PostRequestFramework;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AskBobFramework{
    //baseUrl for testing on emulator
    //private String baseUrl = "http://10.0.2.2:8000/";

    private AppCompatActivity appCompatActivity;

    public AskBobFramework(AppCompatActivity appCompatActivity){
        this.appCompatActivity = appCompatActivity;
    }

    public ArrayList<String> request(String path, String parameters){
        String baseUrl = "";
        SharedPreferences sharedpreferences = appCompatActivity.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        if (!sharedpreferences.contains("server_ip")){
            String ip = new MainController().findServerIp();
            if (ip.startsWith("/")) {
                ip = ip.substring(1);
                baseUrl = ip;
            }
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("server_ip", ip);
            editor.apply();
        } else {
            baseUrl = sharedpreferences.getString("server_ip", "");
        }
        baseUrl = "http://" + baseUrl + ":8000/";
        PostRequestFramework postRequestFramework = new PostRequestFramework(baseUrl);
        return postRequestFramework.request(path, parameters);
    }
}

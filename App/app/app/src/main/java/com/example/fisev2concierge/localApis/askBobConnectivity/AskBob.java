package com.example.fisev2concierge.localApis.askBobConnectivity;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AskBob implements Runnable{

    private AskBobFramework askBobFramework;
    private String method;
    private String parameters;
    private volatile ArrayList<String> response;
    private volatile boolean ready = false;

    public AskBob(String method, String parameters, AppCompatActivity appCompatActivity){
        this.method = method;
        this.parameters = parameters;
        askBobFramework = new AskBobFramework(appCompatActivity);
    }

    public synchronized ArrayList<String> getResult() {
        while(!ready){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    public ArrayList<String> callMethod(){
        ArrayList<String> result = new ArrayList<>();
        switch (this.method){
            case("query"):
                result = query(this.parameters);
                break;
            default:
                result.add("Unknown endpoint: " + method);
                break;
        }
        ready = true;
        return result;
    }

    public ArrayList<String> query(String parameters){
//        return askBobFramework.request("query", parameters);
        parameters = parameters.replace(" ", "%20");
        ArrayList<String> result = new ArrayList<>();
        try {
            URL url = new URL("http://192.168.0.17:8100/askbob?"+parameters);
            System.out.println("fullUrl: " + url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine = "";
            while ((inputLine = in.readLine()) != null) {
                result.add(inputLine);
            }
            in.close();
            connection.disconnect();
        } catch (Exception e) {
            result.add("500");
        }
        System.out.println("result: " + result);
        return result;
    }

    @Override
    public synchronized void run() {
        response = callMethod();
        notifyAll();
    }
}

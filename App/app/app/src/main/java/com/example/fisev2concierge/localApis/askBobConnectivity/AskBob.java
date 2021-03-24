package com.example.fisev2concierge.localApis.askBobConnectivity;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AskBob implements Runnable{

    private final AskBobFramework askBobFramework;
    private final String method;
    private final String parameters;
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
        if ("query".equals(this.method)) {
            result = query(this.parameters);
        } else {
            result.add("Unknown endpoint: " + method);
        }
        ready = true;
        return result;
    }

    public ArrayList<String> query(String parameters){
        return askBobFramework.request("query", parameters);
    }

    @Override
    public synchronized void run() {
        response = callMethod();
        notifyAll();
    }
}

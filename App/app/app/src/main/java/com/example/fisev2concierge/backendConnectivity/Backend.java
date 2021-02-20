package com.example.fisev2concierge.backendConnectivity;

import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Backend implements Runnable{

    private BackendFramework backendFramework = new BackendFramework();
    private String method;
    private String parameter;
    private volatile ArrayList<String> result;
    private volatile boolean ready = false;

    public Backend(String method, String parameter){
        this.method = method;
        this.parameter = parameter;
    }

    public synchronized ArrayList<String> getResult() {
        while(!ready){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public ArrayList<String> callMethod(){
        ArrayList<String> result = new ArrayList<>();
        switch (this.method){
            case("servicedata"):
                result = servicedata(this.parameter);
        }
        ready = true;
        return result;
    }

    public ArrayList<String> servicedata(String id){
        return backendFramework.request("servicedata?id="+id);
    }

    @Override
    public synchronized void run() {
        result = callMethod();
        notifyAll();
    }
}

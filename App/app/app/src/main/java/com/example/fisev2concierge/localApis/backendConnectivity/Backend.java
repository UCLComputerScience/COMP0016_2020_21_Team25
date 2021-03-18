package com.example.fisev2concierge.localApis.backendConnectivity;

import android.renderscript.ScriptIntrinsicYuvToRGB;

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
                break;
            case "getEmergencyContacts":
                result = getEmergencyContacts(this.parameter);
                break;
            case "getHistory":
                result = getHistory(this.parameter);
                break;
            case "addHistory":
                result = addHistory(this.parameter);
                break;
            case "getServices":
                result = getServices(this.parameter);
                break;
                //methods for connecting app to admin site
            case "register":
                result = register(this.parameter);
                break;
            default:
                result.add("No endpoint exists for: " + method);
                break;
        }
        ready = true;
        return result;
    }

    public ArrayList<String> register(String words){
        return backendFramework.request("login-user?first_word="+words);
    }

    public ArrayList<String> servicedata(String id){
        return backendFramework.request("servicedata?id="+id);
    }

    public ArrayList<String> getEmergencyContacts(String id){
        return backendFramework.request("get-emergency-contacts?user_id="+id);
    }

    //which getHistory response do we want to use and why is addHistory not in the same file as memberHistory?
    public ArrayList<String> getHistory(String id){
        return backendFramework.request("member-history?user_id="+id);
    }

    public ArrayList<String> addHistory(String id){
//        backendFramework.request("add-history?service="+serviceName+"&user_id="+id);
        return backendFramework.request("add-history?service_name="+id);
    }

    public ArrayList<String> getServices(String id){
        return backendFramework.request("services?id="+id);
    }

    //backend for connecting user to admin

    @Override
    public synchronized void run() {
        result = callMethod();
        notifyAll();
    }
}

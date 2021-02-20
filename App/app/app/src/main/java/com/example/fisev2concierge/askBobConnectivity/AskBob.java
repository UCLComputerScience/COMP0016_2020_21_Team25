package com.example.fisev2concierge.askBobConnectivity;

import com.example.fisev2concierge.backendConnectivity.BackendFramework;

import java.util.ArrayList;

public class AskBob implements Runnable{

    private AskBobFramework askBobFramework = new AskBobFramework();
    private String method;
    private String parameters;
    private volatile ArrayList<String> response;
    private volatile boolean ready = false;

    public AskBob(String method, String parameters){
        this.method = method;
        this.parameters = parameters;
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

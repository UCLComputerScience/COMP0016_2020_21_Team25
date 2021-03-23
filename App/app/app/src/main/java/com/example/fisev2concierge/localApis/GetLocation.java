package com.example.fisev2concierge.localApis;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetLocation implements Runnable{

    private volatile String postcode;
    private volatile boolean ready = false;
    private final double lat;
    private final double lon;

    public GetLocation(Double lat, Double lon){
        this.lat = lat;
        this.lon = lon;
    }

    public synchronized String getPostcode(){
        while (!ready){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return postcode;
    }

    @Override
    public synchronized void run(){
        getLocation(lat, lon);
        notifyAll();
    }

    public void getLocation(Double lat, Double lon) {
        postcode = makeApiRequest(lat, lon);
        ready = true;
    }

    public String makeApiRequest(Double lat, Double lon){
        String baseUrl = "https://api.postcodes.io/";
        String path = "postcodes?lon={lon}&lat={lat}";
        path = path.replace("{lat}", lat.toString());
        path = path.replace("{lon}", lon.toString());
        GetRequestFramework getRequestFramework = new GetRequestFramework(baseUrl);
        ArrayList<String> result = getRequestFramework.makeRequest(path);
//        GetRequestFramework getRequestFrameworkTest = new GetRequestFramework("https://127.0.0.1:8080/");
//        ArrayList<String> resultTest = getRequestFramework.makeRequest("endpoint?parameter=value");
        if (!result.isEmpty()) {
            String jsonString = result.get(0);
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                return jsonArray.getJSONObject(0).getString("postcode");
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return "london";
    }
}

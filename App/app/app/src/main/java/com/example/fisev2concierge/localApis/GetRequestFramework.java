package com.example.fisev2concierge.localApis;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetRequestFramework {
    private final String baseUrl;

    public GetRequestFramework(String baseUrl){
        this.baseUrl = baseUrl;
    }

    public ArrayList<String> makeRequest(String path){
        ArrayList<String> result = new ArrayList<>();
        try {
            URL url = new URL(baseUrl + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                result.add(inputLine);
            }
            in.close();
            connection.disconnect();
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}

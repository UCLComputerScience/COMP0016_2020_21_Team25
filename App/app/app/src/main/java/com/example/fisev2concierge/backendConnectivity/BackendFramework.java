package com.example.fisev2concierge.backendConnectivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class BackendFramework {
//    private String baseUrl = "http://10.0.2.2:8080/";
    private String baseUrl = "http://localhost:8080/";
    public ArrayList<String> request(String path){
        ArrayList<String> result = new ArrayList<>();
        try {
            URL url = new URL(baseUrl + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine = "";
            while ((inputLine = in.readLine()) != null) {
                result.add(inputLine);
            }
            in.close();
            connection.disconnect();
        } catch (Exception e){
            result.add("500");
        }
        return result;
    }
}

package com.example.fisev2concierge.localApis;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PostRequestFramework {

    private String baseUrl;

    public PostRequestFramework(String baseUrl){
        this.baseUrl = baseUrl;
    }

    public ArrayList<String> request(String path, String parameters){
        //configured for POST Requests which take parameters through body
        ArrayList<String> response = new ArrayList<>();
        try {
            URL url = new URL(baseUrl + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            try( DataOutputStream wr = new DataOutputStream( connection.getOutputStream())) {
                wr.write(parameters.getBytes());
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.add(inputLine);
            }
            in.close();
            connection.disconnect();
        } catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
}

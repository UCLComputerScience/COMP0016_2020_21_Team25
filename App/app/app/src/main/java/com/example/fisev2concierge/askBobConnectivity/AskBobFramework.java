package com.example.fisev2concierge.askBobConnectivity;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AskBobFramework{

//    private String baseUrl = "http://localhost:8000/";
    private String baseUrl = "http://10.0.2.2:8000/";

    public ArrayList<String> request(String path, String parameters){
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
            response.add("500");
        }
        return response;
    }
}

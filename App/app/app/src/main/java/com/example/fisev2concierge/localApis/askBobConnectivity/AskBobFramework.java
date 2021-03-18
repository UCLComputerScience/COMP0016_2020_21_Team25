package com.example.fisev2concierge.localApis.askBobConnectivity;

import com.example.fisev2concierge.localApis.PostRequestFramework;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AskBobFramework{
    //baseUrl for testing on emulator
//    private String baseUrl = "http://10.0.2.2:8000/";
    private String baseUrl = "http://192.168.0.17:8000/";


    public ArrayList<String> request(String path, String parameters){
//        ArrayList<String> response = new ArrayList<>();
//        try {
//            URL url = new URL(baseUrl + path);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoOutput(true);
//            connection.setRequestMethod("POST");
//            try( DataOutputStream wr = new DataOutputStream( connection.getOutputStream())) {
//                wr.write(parameters.getBytes());
//            }
//            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
//                response.add(inputLine);
//            }
//            in.close();
//            connection.disconnect();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        return response;
        PostRequestFramework postRequestFramework = new PostRequestFramework(baseUrl);
        return postRequestFramework.request(path, parameters);
    }
}

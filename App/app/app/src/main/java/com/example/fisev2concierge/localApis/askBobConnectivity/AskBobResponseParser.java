package com.example.fisev2concierge.localApis.askBobConnectivity;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.*;

public class AskBobResponseParser {

    public HashMap parse(ArrayList<String> response){

        HashMap parsedResponse = new HashMap();
        try {
            String jsonString = response.get(0);
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("messages");
            JSONObject custom = (JSONObject) jsonArray.getJSONObject(0).get("custom");
            parsedResponse.put("Service_Type", custom.getString("Service_Type"));
            parsedResponse.put("Service", custom.getString("Service"));
            if (custom.getString("Service_Type").equals("API_CALL")){
                parsedResponse.put("Service", custom.getString("Service"));
                parsedResponse.put("Response", custom.getString("Response"));
                if (custom.getString("Service").equals("Transport")){
                    if (custom.has("Transport Type")) {
                        parsedResponse.put("Transport Type", custom.getString("Transport Type"));
                    } else {
                        try {
                            JSONObject responseObject = new JSONObject(custom.getString("Response"));
                            if (responseObject.has("Message")) {
                                String responseMessage = responseObject.getString("Message");
                                parsedResponse.put("Message", responseMessage);
                                parsedResponse.put("lat", responseObject.getString("Latitude"));
                                parsedResponse.put("lon", responseObject.getString("Longitude"));
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                } else if (custom.getString("Service").equals("Recipes")){
                    if (custom.has("Steps")){
                        try {
                            JSONArray stepsArray = custom.getJSONArray("Steps");
                            String steps = "";
                            for (int i = 0; i < stepsArray.length(); i++){
                                steps += stepsArray.getString(i);
                            }
                            System.out.println("steps: " + steps);
                            parsedResponse.put("Steps", steps);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                switch (custom.getString("Service")) {
                    case "Call Contact":
                        parsedResponse.put("Contact", custom.getString("Contact"));
                        parsedResponse.put("Response", custom.getString("Response"));
                        break;
                    case "SMS Contact":
                        parsedResponse.put("Contact", custom.getString("Contact"));
                        parsedResponse.put("Response", custom.getString("Response"));
                        //add message
                        break;
                    case "Open App":
                        parsedResponse.put("Application", custom.getString("Application").toLowerCase());
                        break;
                    case "Shop Search":
                        parsedResponse.put("Shop", custom.getString("Shop").toLowerCase());
                        parsedResponse.put("Application", custom.getString("Search Term").toLowerCase());
                        parsedResponse.put("Response", custom.getString("Response"));
                        break;
                    case "Yell Search":
                        parsedResponse.put("Application", custom.getString("Search Term").toLowerCase());
                        parsedResponse.put("Response", custom.getString("Response"));
                        break;
                    case "Navigate App":
                        parsedResponse.put("Application", custom.getString("Page").toLowerCase());
                        break;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            try{
                //Command may not be recognised
                String jsonString = response.get(0);
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("messages");
                String text = jsonArray.getJSONObject(0).getString("text");
                parsedResponse.put("Service_Type", "ERROR");
                parsedResponse.put("Service", "ERROR");
                parsedResponse.put("text", text);
            } catch (Exception e1){
                e1.printStackTrace();
            }
        }
        return parsedResponse;
    }
}

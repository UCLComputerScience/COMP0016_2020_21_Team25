package com.example.fisev2concierge.askBobConnectivity;

import com.example.fisev2concierge.controllers.MainController;
import com.example.fisev2concierge.helperClasses.GetLocation;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.*;

public class AskBobResponseParser {

    public HashMap parse(ArrayList<String> response){

        HashMap parsedResponse = new HashMap();
        try {
            //take 500 into account
            String jsonString = response.get(0);
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("messages");
            JSONObject custom = (JSONObject) jsonArray.getJSONObject(0).get("custom");
            parsedResponse.put("Service_Type", custom.getString("Service_Type"));
            switch(custom.getString("Service_Type")){
                case "API_CALL":
                    parsedResponse.put("Service", custom.getString("Service"));
                    parsedResponse.put("Response", custom.getString("Response"));
                    break;
                case "CALL_CONTACT":
                    parsedResponse.put("Contact", custom.getString("Contact"));
                    break;
                case "SMS_CONTACT":
                    parsedResponse.put("Contact", custom.getString("Contact"));
                    //add message
                    break;
                case "OPEN_APP":
                    parsedResponse.put("Application", custom.getString("Application"));
                    break;
                case "SEARCH_SITE":
                    //get location and add it to hash map before giving hashmap to openurl class
                    break;
            }
        } catch (Exception e){
            System.out.println("Error: exception");
            e.printStackTrace();
            try{
                //Command may not be recognised
                String jsonString = response.get(0);
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("messages");
                String text = jsonArray.getJSONObject(0).getString("text");
                parsedResponse.put("Service_Type", "ERROR");
                parsedResponse.put("text", text);
            } catch (Exception e1){
                System.out.println("Error: exception 2");
                e1.printStackTrace();
            }
        }
        return parsedResponse;
    }
}

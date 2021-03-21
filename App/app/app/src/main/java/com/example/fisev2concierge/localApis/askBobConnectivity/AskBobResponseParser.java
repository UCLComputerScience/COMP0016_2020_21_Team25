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
            String service_type = custom.getString("Service_Type");
            String service = custom.getString("Service");
            parsedResponse.put("Service_Type", service_type);
            parsedResponse.put("Service", service);
            if (service_type.equals("API_CALL")){
                parseApiResponse(parsedResponse, custom);
            } else {
                switch (custom.getString("Service")) {
                    case "Call Contact":
                        parseCallResponse(parsedResponse, custom);
                        break;
                    case "SMS Contact":
                        parseSmsResponse(parsedResponse, custom);
                        break;
                    case "Open App":
                        parseOpenAppResponse(parsedResponse, custom);
                        break;
                    case "Shop Search":
                        parseShopSearchResponse(parsedResponse, custom);
                        break;
                    case "Yell Search":
                        parseYellSearchResponse(parsedResponse, custom);
                        break;
                    case "Navigate App":
                        parseNavigateAppResponse(parsedResponse, custom);
                        break;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            try{
                //Command may not be recognised
                String jsonString = response.get(0);
                JSONObject jsonObject = new JSONObject(jsonString);
                parseErrorResponse(parsedResponse, jsonObject);
            } catch (Exception e1){
                e1.printStackTrace();
            }
        }
        return parsedResponse;
    }

    private void parseApiResponse(HashMap parsedResponse, JSONObject custom) throws Exception{
        parsedResponse.put("Service", custom.getString("Service"));
        parsedResponse.put("Response", custom.getString("Response"));
        if (custom.getString("Service").equals("Transport")) {
            parseTransportApiResponse(parsedResponse, custom);
        } else if (custom.getString("Service").equals("Recipes")) {
            parseRecipesApiResponse(parsedResponse, custom);
        }
    }

    private void parseTransportApiResponse(HashMap parsedResponse, JSONObject custom) throws Exception{
        if (custom.has("Transport Type")) {
            parsedResponse.put("Transport Type", custom.getString("Transport Type"));
        } else {
            JSONObject responseObject = new JSONObject(custom.getString("Response"));
            if (responseObject.has("Message")) {
                String responseMessage = responseObject.getString("Message");
                parsedResponse.put("Message", responseMessage);
                parsedResponse.put("lat", responseObject.getString("Latitude"));
                parsedResponse.put("lon", responseObject.getString("Longitude"));
            }
        }
    }

    private void parseRecipesApiResponse(HashMap parsedResponse, JSONObject custom) throws Exception{
        if (custom.has("Steps")) {
            JSONArray stepsArray = custom.getJSONArray("Steps");
            String steps = "";
            for (int i = 0; i < stepsArray.length(); i++) {
                steps += stepsArray.getString(i);
            }
            parsedResponse.put("Steps", steps);
        }
    }

    private void parseCallResponse(HashMap parsedResponse, JSONObject custom) throws Exception{
        parsedResponse.put("Contact", custom.getString("Contact"));
        parsedResponse.put("Response", custom.getString("Response"));
    }

    private void parseSmsResponse(HashMap parsedResponse, JSONObject custom) throws Exception{
        parsedResponse.put("Contact", custom.getString("Contact"));
        parsedResponse.put("Response", custom.getString("Response"));
        //add message once nlp can extract it
    }

    private void parseShopSearchResponse(HashMap parsedResponse, JSONObject custom) throws Exception{
        parsedResponse.put("Shop", custom.getString("Shop").toLowerCase());
        parsedResponse.put("Application", custom.getString("Search Term").toLowerCase());
        parsedResponse.put("Response", custom.getString("Response"));
    }

    private void parseYellSearchResponse(HashMap parsedResponse, JSONObject custom) throws Exception{
        parsedResponse.put("Application", custom.getString("Search Term").toLowerCase());
        parsedResponse.put("Response", custom.getString("Response"));
    }

    private void parseOpenAppResponse(HashMap parsedResponse, JSONObject custom) throws Exception{
        parsedResponse.put("Application", custom.getString("Application").toLowerCase());
    }

    private void parseNavigateAppResponse(HashMap parsedResponse, JSONObject custom) throws Exception{
        parsedResponse.put("Application", custom.getString("Page").toLowerCase());
    }

    private void parseErrorResponse(HashMap parsedResponse, JSONObject jsonObject) throws Exception{
        JSONArray jsonArray = jsonObject.getJSONArray("messages");
        String text = jsonArray.getJSONObject(0).getString("text");
        parsedResponse.put("Service_Type", "ERROR");
        parsedResponse.put("Service", "ERROR");
        parsedResponse.put("text", text);
    }
}

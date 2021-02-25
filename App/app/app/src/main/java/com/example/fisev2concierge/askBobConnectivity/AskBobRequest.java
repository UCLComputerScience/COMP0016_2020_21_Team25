package com.example.fisev2concierge.askBobConnectivity;

import com.example.fisev2concierge.controllers.MainController;

import java.util.ArrayList;
import java.util.HashMap;

public class AskBobRequest {

    private MainController mainController = new MainController();

    public HashMap makeRequest(String sTT){
        String parameters = "message=\"" + sTT +"\"&sender=\"user\"";
        ArrayList<String> response = mainController.askBobServices("query", parameters);
        HashMap parsedResponse = mainController.parseAskBobResponse(response);
        return parsedResponse;
    }
}

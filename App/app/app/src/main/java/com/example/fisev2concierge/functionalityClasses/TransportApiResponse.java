package com.example.fisev2concierge.functionalityClasses;

import com.example.fisev2concierge.controllers.MainController;

public class TransportApiResponse {

    public String searchForTransport(String type, String url){
        if (type.equals("train_station")){
//            String url = mainController.searchUrlLookup("maps_transport");
            url = url.replace("{query}", "nearest+train+station");
        } else if (type.equals("bus_stop")){
            url = url.replace("{query}", "nearest+bus+stop");
        } else {
            url = url.replace("{query}", "nearby amenities");
        }
        return url;
    }
}

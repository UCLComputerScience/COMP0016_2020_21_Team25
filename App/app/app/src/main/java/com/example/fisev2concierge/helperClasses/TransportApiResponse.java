package com.example.fisev2concierge.helperClasses;

import com.example.fisev2concierge.controllers.MainController;
import android.Manifest;

import com.example.fisev2concierge.UI.MainActivity;
import com.example.fisev2concierge.controllers.MainController;


public class TransportApiResponse {

    public String searchForTransport(String type, String url){
        if (type.equals("train_station")){
            url = url.replace("{query}", "nearest+train+station");
        } else if (type.equals("bus_stop")){
            url = url.replace("{query}", "nearest+bus+stop");
        } else {
            url = url.replace("{query}", "nearby+amenities");
        }
        return url;
    }
}

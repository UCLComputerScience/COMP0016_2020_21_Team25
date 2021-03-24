package com.example.fisev2concierge.helperClasses;

import java.util.HashMap;

public class WebsiteUrlLookup {

    private final HashMap<String, String> websiteUrls = new HashMap<>();

    public WebsiteUrlLookup(){
        websiteUrls.put("amazon", "https://www.amazon.co.uk/");
        websiteUrls.put("yell", "https://www.yell.com/");
    }

    public String search(String websiteName){
        return (String) websiteUrls.get(websiteName);
    }
}

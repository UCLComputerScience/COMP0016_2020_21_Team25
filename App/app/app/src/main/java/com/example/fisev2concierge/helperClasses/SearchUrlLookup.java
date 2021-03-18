package com.example.fisev2concierge.helperClasses;

import java.util.HashMap;

public class SearchUrlLookup {

    private HashMap searchUrls = new HashMap();

    public SearchUrlLookup(){
        searchUrls.put("amazon", "https://www.amazon.co.uk/s?k=");
        searchUrls.put("yell", "https://www.yell.com/ucs/UcsSearchAction.do?keywords={keywords}&location={location}");
        searchUrls.put("playstoreSearchApp", "https://play.google.com/store/search?q=");
        searchUrls.put("playstoreOpenApp", "https://play.google.com/store/apps/details?id=");
        searchUrls.put("google", "https://www.google.com/search?q=");
        searchUrls.put("maps_transport", "https://www.google.com/maps/search/{query}/@{lat},{lon}");
    }

    public String search(String websiteName){
        return (String) searchUrls.get(websiteName);
    }
}

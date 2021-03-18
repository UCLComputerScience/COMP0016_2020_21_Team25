package com.example.fisev2concierge.localApis.askBobConnectivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class AskBobResponseParserTest {

    @Test
    public void parseServiceApiCommand(){
        AskBobResponseParser askBobResponseParser = new AskBobResponseParser();
        ArrayList<String> mockResponse = new ArrayList<>();
        mockResponse.add("{\"query\":\"whats the weather like\",\"messages\":[{\"custom\":{\"Service_Type\":\"API_CALL\",\"Service\":\"current_weather\",\"Response\":\"The weather in London today is broken clouds with the temperature being 9 degrees celsius but will probably feel like 6 degrees celsius. The high will be 10 degrees celsius and the low, 8 degrees celsius. Don't forget to dress warm today!\"}}]}");
        HashMap parsedResponse = askBobResponseParser.parse(mockResponse);
        assertEquals("API_CALL", parsedResponse.get("Service_Type"));
        assertEquals("current_weather", parsedResponse.get("Service"));
        assertEquals("The weather in London today is broken clouds with the temperature being 9 degrees celsius but will probably feel like 6 degrees celsius. The high will be 10 degrees celsius and the low, 8 degrees celsius. Don't forget to dress warm today!", parsedResponse.get("Response"));
    }

    @Test
    public void parseOpenAppCommand(){
        AskBobResponseParser askBobResponseParser = new AskBobResponseParser();
        ArrayList<String> mockResponse = new ArrayList<>();
        mockResponse.add("{\"query\":\"\\\"open snapchat\\\"\",\"messages\":[{\"custom\":{\"Service_Type\":\"OPEN_APP\",\"Application\":\"open snapchat\"}}]}");
        HashMap parsedResponse = askBobResponseParser.parse(mockResponse);
        assertEquals("OPEN_APP", parsedResponse.get("Service_Type"));
        assertEquals("open snapchat", parsedResponse.get("Application"));
    }

    @Test
    public void parseCallCommand(){
        AskBobResponseParser askBobResponseParser = new AskBobResponseParser();
        ArrayList<String> mockResponse = new ArrayList<>();
        mockResponse.add("{\"query\":\"\\\"call bob\\\"\",\"messages\":[{\"custom\":{\"Service_Type\":\"CALL_CONTACT\",\"Contact\":\"bob\"}}]}");
        HashMap parsedResponse = askBobResponseParser.parse(mockResponse);
        assertEquals("CALL_CONTACT", parsedResponse.get("Service_Type"));
        assertEquals("bob", parsedResponse.get("Contact"));
    }

    @Test
    public void parseMessageCommand(){
        AskBobResponseParser askBobResponseParser = new AskBobResponseParser();
        ArrayList<String> mockResponse = new ArrayList<>();
        mockResponse.add("{\"query\":\"\\\"message bob\\\"\",\"messages\":[{\"custom\":{\"Service_Type\":\"SMS_CONTACT\",\"Contact\":\"bob\"}}]}");
        HashMap parsedResponse = askBobResponseParser.parse(mockResponse);
        assertEquals("SMS_CONTACT", parsedResponse.get("Service_Type"));
        assertEquals("bob", parsedResponse.get("Contact"));
    }

    @Test
    public void parseShopCommand(){
        AskBobResponseParser askBobResponseParser = new AskBobResponseParser();
        ArrayList<String> mockResponse = new ArrayList<>();
        mockResponse.add("{\"query\":\"\\\"search amazon for cars\\\"\",\"messages\":[{\"custom\":{\"Service_Type\":\"SHOP_SEARCH\",\"Status\":\"ok\",\"Service\":\"Amazon\",\"Application\":\"search amazon for cars\"}}]}");
        HashMap parsedResponse = askBobResponseParser.parse(mockResponse);
        assertEquals("SHOP_SEARCH", parsedResponse.get("Service_Type"));
        assertEquals("Amazon", parsedResponse.get("Service"));
        assertEquals("search amazon for cars", parsedResponse.get("Application"));
    }

    @Test
    public void parseYellCommand(){
        AskBobResponseParser askBobResponseParser = new AskBobResponseParser();
        ArrayList<String> mockResponse = new ArrayList<>();
        mockResponse.add("{\"query\":\"\\\"find me a plumber\\\"\",\"messages\":[{\"custom\":{\"Service_Type\":\"YELL_SEARCH\",\"Status\":\"ok\",\"Application\":\"plumber\"}}]}");
        HashMap parsedResponse = askBobResponseParser.parse(mockResponse);
        assertEquals("YELL_SEARCH", parsedResponse.get("Service_Type"));
        assertEquals("plumber", parsedResponse.get("Application"));
    }

    @Test
    public void parseNavigateAppCommand(){
        AskBobResponseParser askBobResponseParser = new AskBobResponseParser();
        ArrayList<String> mockResponse = new ArrayList<>();
        mockResponse.add("{\"query\":\"\\\"go to reminders\\\"\",\"messages\":[{\"custom\":{\"Service_Type\":\"NAVIGATE_APP\",\"Application\":\"reminders\"}}]}");
        HashMap parsedResponse = askBobResponseParser.parse(mockResponse);
        assertEquals("NAVIGATE_APP", parsedResponse.get("Service_Type"));
        assertEquals("reminders", parsedResponse.get("Application"));
    }

    @Test
    public void parseError(){
        AskBobResponseParser askBobResponseParser = new AskBobResponseParser();
        ArrayList<String> mockResponse = new ArrayList<>();
        mockResponse.add("{\"query\":\"\\\"random\\\"\",\"messages\":[{\"text\":\"I didn't quite catch that! Please could you rephrase?\"}]}");
        HashMap parsedResponse = askBobResponseParser.parse(mockResponse);
        assertEquals("ERROR", parsedResponse.get("Service_Type"));
        assertEquals("I didn't quite catch that! Please could you rephrase?", parsedResponse.get("text"));
    }


}
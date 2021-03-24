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
    public void parseTransportNearestStationApiCommandTest(){
        AskBobResponseParser askBobResponseParser = new AskBobResponseParser();
        ArrayList<String> mockResponse = new ArrayList<>();
        mockResponse.add("{\"query\":\"\\\"\\\"tell me where the nearest train station is\\\"\\\"\",\"messages\":[{\"custom\":{\"Service_Type\":\"API_CALL\",\"Service\":\"Transport\",\"Response\":\"Finding nearest train station\",\"Transport Type\":\"train_station\"}}]}");
        HashMap parsedResponse = askBobResponseParser.parse(mockResponse);
        assertEquals("API_CALL", parsedResponse.get("Service_Type"));
        assertEquals("Transport", parsedResponse.get("Service"));
        assertEquals("Finding nearest train station", parsedResponse.get("Response"));
        assertEquals("train_station", parsedResponse.get("Transport Type"));
    }

    @Test
    public void parseTransportSpecificStationApiCommandTest(){
        AskBobResponseParser askBobResponseParser = new AskBobResponseParser();
        ArrayList<String> mockResponse = new ArrayList<>();
        mockResponse.add("{\"query\":\"tell me where euston train station is\",\"messages\":[{\"custom\":{\"Service_Type\":\"API_CALL\",\"Service\":\"Transport\",\"Response\":{\"Message\":\"Here's what I've found about euston.\",\"Latitude\":51.528135,\"Longitude\":-0.133924}}}]}");
        HashMap parsedResponse = askBobResponseParser.parse(mockResponse);
        assertEquals("API_CALL", parsedResponse.get("Service_Type"));
        assertEquals("Transport", parsedResponse.get("Service"));
        assertEquals("51.528135", parsedResponse.get("lat"));
        assertEquals("-0.133924", parsedResponse.get("lon"));
    }

    @Test
    public void parseRecipesWithStepsApiCommandTest(){
        AskBobResponseParser askBobResponseParser = new AskBobResponseParser();
        ArrayList<String> mockResponse = new ArrayList<>();
        mockResponse.add("{\"query\":\"read that recipe\",\"messages\":[{\"custom\":{\"Service_Type\":\"API_CALL\",\"Service\":\"Recipes\",\"Response\":\"Here's the recipe information.\",\"Steps\":[\"Step 1: Chop the mushrooms in half and place into a large bowl with all the ingredients for the marinade. Stir so everything is coated, then cover and place in the fridge for 1 hour, or up to overnight.\"]}}]}");
        HashMap parsedResponse = askBobResponseParser.parse(mockResponse);
        assertEquals("API_CALL", parsedResponse.get("Service_Type"));
        assertEquals("Recipes", parsedResponse.get("Service"));
        assertEquals("Step 1: Chop the mushrooms in half and place into a large bowl with all the ingredients for the marinade. Stir so everything is coated, then cover and place in the fridge for 1 hour, or up to overnight.", parsedResponse.get("Steps"));
    }

    @Test
    public void parseServiceApiCommandTest(){
        AskBobResponseParser askBobResponseParser = new AskBobResponseParser();
        ArrayList<String> mockResponse = new ArrayList<>();
        mockResponse.add("{\"query\":\"tell me a joke\",\"messages\":[{\"custom\":{\"Service_Type\":\"API_CALL\",\"Service\":\"Jokes\",\"Response\":\"what do you call a dog that can do magic tricks? a labracadabrador\"}}]}");
        HashMap parsedResponse = askBobResponseParser.parse(mockResponse);
        assertEquals("API_CALL", parsedResponse.get("Service_Type"));
        assertEquals("Jokes", parsedResponse.get("Service"));
        assertEquals("what do you call a dog that can do magic tricks? a labracadabrador", parsedResponse.get("Response"));
    }

    @Test
    public void parseOpenAppCommandTest(){
        AskBobResponseParser askBobResponseParser = new AskBobResponseParser();
        ArrayList<String> mockResponse = new ArrayList<>();
        mockResponse.add("{\"query\":\"open snapchat\",\"messages\":[{\"custom\":{\"Service_Type\":\"APP_SERVICE\",\"Service\":\"Open App\",\"Application\":\"SNAPCHAT\"}}]}");
        HashMap parsedResponse = askBobResponseParser.parse(mockResponse);
        assertEquals("Open App", parsedResponse.get("Service"));
        assertEquals("snapchat", parsedResponse.get("Application"));
    }

    @Test
    public void parseCallCommandTest(){
        AskBobResponseParser askBobResponseParser = new AskBobResponseParser();
        ArrayList<String> mockResponse = new ArrayList<>();
        mockResponse.add("{\"query\":\"call bob\",\"messages\":[{\"custom\":{\"Service_Type\":\"APP_SERVICE\",\"Service\":\"Call Contact\",\"Response\":\"Calling bob\",\"Contact\":\"bob\"}}]}");
        HashMap parsedResponse = askBobResponseParser.parse(mockResponse);
        assertEquals("Call Contact", parsedResponse.get("Service"));
        assertEquals("Calling bob", parsedResponse.get("Response"));
        assertEquals("bob", parsedResponse.get("Contact"));
    }

    @Test
    public void parseMessageCommandTest(){
        AskBobResponseParser askBobResponseParser = new AskBobResponseParser();
        ArrayList<String> mockResponse = new ArrayList<>();
        mockResponse.add("{\"query\":\"message bob\",\"messages\":[{\"custom\":{\"Service_Type\":\"APP_SERVICE\",\"Service\":\"SMS Contact\",\"Response\":\"Messaging bob\",\"Contact\":\"bob\"}}]}");
        HashMap parsedResponse = askBobResponseParser.parse(mockResponse);
        assertEquals("SMS Contact", parsedResponse.get("Service"));
        assertEquals("Messaging bob", parsedResponse.get("Response"));
        assertEquals("bob", parsedResponse.get("Contact"));
    }

    @Test
    public void parseShopCommandTest(){
        AskBobResponseParser askBobResponseParser = new AskBobResponseParser();
        ArrayList<String> mockResponse = new ArrayList<>();
        mockResponse.add("{\"query\":\"search amazon for baked beans\",\"messages\":[{\"custom\":{\"Service_Type\":\"LOOKUP\",\"Service\":\"Shop Search\",\"Response\":\"Searching AMAZON for BAKED BEANS\",\"Shop\":\"AMAZON\",\"Search Term\":\"BAKED BEANS\"}}]}");
        HashMap parsedResponse = askBobResponseParser.parse(mockResponse);
        assertEquals("Shop Search", parsedResponse.get("Service"));
        assertEquals("Searching AMAZON for BAKED BEANS", parsedResponse.get("Response"));
        assertEquals("amazon", parsedResponse.get("Shop"));
        assertEquals("baked beans", parsedResponse.get("Application"));
    }

    @Test
    public void parseYellCommandTest(){
        AskBobResponseParser askBobResponseParser = new AskBobResponseParser();
        ArrayList<String> mockResponse = new ArrayList<>();
        mockResponse.add("{\"query\":\"find me a plumber\",\"messages\":[{\"custom\":{\"Service_Type\":\"LOOKUP\",\"Service\":\"Yell Search\",\"Response\":\"Searching YELL for PLUMBER\",\"Search Term\":\"PLUMBER\"}}]}");
        HashMap parsedResponse = askBobResponseParser.parse(mockResponse);
        assertEquals("Yell Search", parsedResponse.get("Service"));
        assertEquals("Searching YELL for PLUMBER", parsedResponse.get("Response"));
        assertEquals("plumber", parsedResponse.get("Application"));
    }

    @Test
    public void parseNavigateAppCommandTest(){
        AskBobResponseParser askBobResponseParser = new AskBobResponseParser();
        ArrayList<String> mockResponse = new ArrayList<>();
        mockResponse.add("{\"query\":\"go to the history page\",\"messages\":[{\"custom\":{\"Service_Type\":\"APP_SERVICE\",\"Service\":\"Navigate App\",\"Page\":\"history\"}}]}");
        HashMap parsedResponse = askBobResponseParser.parse(mockResponse);
        assertEquals("Navigate App", parsedResponse.get("Service"));
        assertEquals("history", parsedResponse.get("Application"));
    }

    @Test
    public void parseErrorTest(){
        AskBobResponseParser askBobResponseParser = new AskBobResponseParser();
        ArrayList<String> mockResponse = new ArrayList<>();
        mockResponse.add("{\"query\":\"\\\"random\\\"\",\"messages\":[{\"text\":\"I didn't quite catch that! Please could you rephrase?\"}]}");
        HashMap parsedResponse = askBobResponseParser.parse(mockResponse);
        assertEquals("ERROR", parsedResponse.get("Service_Type"));
        assertEquals("ERROR", parsedResponse.get("Service"));
        assertEquals("I didn't quite catch that! Please could you rephrase?", parsedResponse.get("text"));
    }

    @Test
    public void parseSecondErrorTest(){
        AskBobResponseParser askBobResponseParser = new AskBobResponseParser();
        ArrayList<String> mockResponse = new ArrayList<>();
        HashMap parsedResponse = askBobResponseParser.parse(mockResponse);
    }


}
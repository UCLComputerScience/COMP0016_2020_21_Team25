package com.example.fisev2concierge.localApis.askBobConnectivity;

import com.example.fisev2concierge.UI.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.HashMap;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class AskBobRequestTest {

    @Test
    public void makeRequestTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        AskBobRequest askBobRequest = new AskBobRequest();
        HashMap parsedResponse = askBobRequest.makeRequest("open snapchat", activity);
        assertTrue(parsedResponse.containsKey("Service_Type"));
        assertTrue(parsedResponse.containsKey("Service"));
        assertTrue(parsedResponse.containsKey("Application"));
    }

}
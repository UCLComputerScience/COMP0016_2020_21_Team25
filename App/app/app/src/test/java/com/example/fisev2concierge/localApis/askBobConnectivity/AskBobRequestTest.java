package com.example.fisev2concierge.localApis.askBobConnectivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class AskBobRequestTest {
    //Adding tests for buttons on MainActivity
    @Test
    public void getREQUESTCALL(){
//        considering using mock api data here as we can't guarantee backend is going to respond
        AskBobRequest askBobRequest = new AskBobRequest();
        HashMap parsedResponse = askBobRequest.makeRequest("open snapchat");
        assertEquals("OPEN_APP", parsedResponse.get("Service_Type"));
        assertEquals("open snapchat", parsedResponse.get("Application"));
    }
}
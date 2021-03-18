package com.example.fisev2concierge.localApis.askBobConnectivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class AskBobTest {
    //Adding tests for buttons on MainActivity
    @Test
    public void getREQUESTCALL(){
//        considering using mock api data here as we can't guarantee backend is going to respond
        AskBob askBob = new AskBob("query", "message=\"open snapchat\"&sender=\"concierge\"");
        Thread thread = new Thread(askBob);
        thread.start();
        ArrayList<String> res =  askBob.getResult();
        assertEquals("{\"query\":\"\\\"open snapchat\\\"\",\"messages\":[{\"custom\":{\"Service_Type\":\"OPEN_APP\",\"Application\":\"open snapchat\"}}]}", res.get(0));
    }
}
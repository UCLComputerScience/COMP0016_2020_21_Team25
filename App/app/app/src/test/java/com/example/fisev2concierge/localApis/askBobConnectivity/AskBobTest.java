package com.example.fisev2concierge.localApis.askBobConnectivity;

import com.example.fisev2concierge.views.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class AskBobTest {

    @Test
    public void queryAskBobTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        AskBob askBob = new AskBob("query", "message=\"open snapchat\"&sender=\"concierge\"", activity);
        Thread thread = new Thread(askBob);
        thread.start();
        ArrayList<String> res =  askBob.getResult();
        assertEquals("{\"query\":\"\\\"open snapchat\\\"\",\"messages\":[{\"custom\":{\"Service_Type\":\"APP_SERVICE\",\"Service\":\"Open App\",\"Application\":\"SNAPCHAT\"}}]}", res.get(0));
    }

    @Test
    public void queryAskBobDefaultTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        AskBob askBob = new AskBob("random", "random", activity);
        Thread thread = new Thread(askBob);
        thread.start();
        ArrayList<String> res =  askBob.getResult();
        assertEquals("Unknown endpoint: random", res.get(0));
    }
}
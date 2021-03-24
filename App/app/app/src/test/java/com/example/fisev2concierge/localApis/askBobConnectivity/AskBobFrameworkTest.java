package com.example.fisev2concierge.localApis.askBobConnectivity;

import android.content.Context;

import com.example.fisev2concierge.views.HistoryActivity;
import com.example.fisev2concierge.views.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class AskBobFrameworkTest {

    @Test
    public void makeRequestNoIpStoredTest(){
        HistoryActivity activity = Robolectric.buildActivity(HistoryActivity.class).get();
        AskBobFramework askBobFramework = new AskBobFramework(activity);
        ArrayList<String> res = askBobFramework.request("query", "message=\"open snapchat\"&sender=\"concierge\"");
        assertEquals("{\"query\":\"\\\"open snapchat\\\"\",\"messages\":[{\"custom\":{\"Service_Type\":\"APP_SERVICE\",\"Service\":\"Open App\",\"Application\":\"SNAPCHAT\"}}]}", res.get(0));
        assertTrue(activity.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE).contains("server_ip"));
    }

    @Test
    public void makeRequestIpStoredTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        AskBobFramework askBobFramework = new AskBobFramework(activity);
        ArrayList<String> res = askBobFramework.request("query", "message=\"open snapchat\"&sender=\"concierge\"");
        assertEquals("{\"query\":\"\\\"open snapchat\\\"\",\"messages\":[{\"custom\":{\"Service_Type\":\"APP_SERVICE\",\"Service\":\"Open App\",\"Application\":\"SNAPCHAT\"}}]}", res.get(0));
    }
}
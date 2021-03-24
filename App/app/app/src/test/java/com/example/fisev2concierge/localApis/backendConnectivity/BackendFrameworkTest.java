package com.example.fisev2concierge.localApis.backendConnectivity;

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
public class BackendFrameworkTest {

    @Test
    public void makeRequestNoIpStoredTest(){
        HistoryActivity activity = Robolectric.buildActivity(HistoryActivity.class).get();
        BackendFramework backendFramework = new BackendFramework(activity);
        ArrayList<String> res = backendFramework.request("servicedata?id=0");
        assertEquals("{\"name\":\"\",\"category\":\"\",\"description\":\"\",\"code\":200}", res.get(0));
        assertTrue(activity.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE).contains("server_ip"));
    }

    @Test
    public void makeRequestIpStoredTest(){
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).get();
        //MainActivity automatically adds the IP to SharedPreferences
        BackendFramework backendFramework = new BackendFramework(mainActivity);
        ArrayList<String> res = backendFramework.request("servicedata?id=0");
        assertEquals("{\"name\":\"\",\"category\":\"\",\"description\":\"\",\"code\":200}", res.get(0));
    }
}
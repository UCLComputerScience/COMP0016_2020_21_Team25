package com.example.fisev2concierge.localApis.backendConnectivity;

import com.example.fisev2concierge.UI.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class BackendTest {
    //wanted to use mock requests rather than make actual requests but could not find a reliable way to do this

    @Test
    public void serviceDataTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        Backend backend = new Backend("servicedata", "0", activity);
        Thread thread = new Thread(backend);
        thread.start();
        ArrayList<String> res = backend.getResult();
        assertEquals("{\"name\":\"\",\"category\":\"\",\"description\":\"\",\"code\":200}", res.get(0));
    }

    @Test
    public void getEmergencyContactsTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        Backend backend = new Backend("getEmergencyContacts", "101", activity);
        Thread thread = new Thread(backend);
        thread.start();
        ArrayList<String> res = backend.getResult();
        assertEquals("{\"success\":true,\"message\":\"Ok\",\"code\":200,\"emergency_contacts\":{\"Dentist\":\"22222222222\",\"Gp\":\"11111111112\",\"Optometrist\":\"33333333333\"}}", res.get(0));
    }

    @Test
    public void getHistoryTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        Backend backend = new Backend("getHistory", "0", activity);
        Thread thread = new Thread(backend);
        thread.start();
        ArrayList<String> res = backend.getResult();
        assertEquals("{\"history\":[],\"code\":200}", res.get(0));
    }

    @Test
    public void addHistoryTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        Backend backend = new Backend("addHistory", "Weather&user_id=0", activity);
        Thread thread = new Thread(backend);
        thread.start();
        ArrayList<String> res = backend.getResult();
        assertEquals("{\"message\":\"Invalid USER_ID\",\"code\":200}", res.get(0));
    }

    @Test
    public void getServicesTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        Backend backend = new Backend("getServices", "0", activity);
        Thread thread = new Thread(backend);
        thread.start();
        ArrayList<String> res = backend.getResult();
        assertEquals("{\"services\":[],\"code\":200}", res.get(0));
    }

    @Test
    public void registerTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        Backend backend = new Backend("register", "hello&second_word=there&last_word=world", activity);
        Thread thread = new Thread(backend);
        thread.start();
        ArrayList<String> res = backend.getResult();
        assertEquals("{\"message\":\"OK\",\"userId\":\"91\",\"code\":200}", res.get(0));
    }

    @Test
    public void defaultTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        Backend backend = new Backend("random", "random", activity);
        Thread thread = new Thread(backend);
        thread.start();
        ArrayList<String> res = backend.getResult();
        assertEquals("No endpoint exists for: random", res.get(0));
    }


}
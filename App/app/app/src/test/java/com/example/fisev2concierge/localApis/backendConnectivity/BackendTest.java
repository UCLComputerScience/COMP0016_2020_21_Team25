package com.example.fisev2concierge.localApis.backendConnectivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class BackendTest {

    @Test
    public void serviceDataTest(){
//        considering using mock api data here as we can't guarantee backend is going to respond
        Backend backend = new Backend("servicedata", "0");
        Thread thread = new Thread(backend);
        thread.start();
        ArrayList<String> res = backend.getResult();
        assertEquals("{\"name\":\"\",\"category\":\"\",\"description\":\"\",\"code\":200}", res.get(0));
    }

    @Test
    public void getEmergencyContactsTest(){
        Backend backend = new Backend("getEmergencyContacts", "101");
        Thread thread = new Thread(backend);
        thread.start();
        ArrayList<String> res = backend.getResult();
        assertEquals("{\"success\":true,\"message\":\"Ok\",\"code\":200,\"emergency_contacts\":{\"Dentist\":\"22222222222\",\"Gp\":\"11111111112\",\"Optometrist\":\"33333333333\"}}", res.get(0));
    }

    @Test
    public void getHistoryTest(){
        Backend backend = new Backend("getHistory", "0");
        Thread thread = new Thread(backend);
        thread.start();
        ArrayList<String> res = backend.getResult();
        assertEquals("{\"success\":true,\"message\":\"Could not find any history for this user\",\"code\":200,\"history\":[]}", res.get(0));
    }

    @Test
    public void addHistoryTest(){
        Backend backend = new Backend("addHistory", "current_weather&user_id=0");
        Thread thread = new Thread(backend);
        thread.start();
        ArrayList<String> res = backend.getResult();
        assertEquals("{\"message\":\"Invalid USER_ID\",\"code\":200}", res.get(0));
    }

    @Test
    public void getServicesTest(){
        Backend backend = new Backend("getServices", "0");
        Thread thread = new Thread(backend);
        thread.start();
        ArrayList<String> res = backend.getResult();
        assertEquals("{\"services\":[],\"code\":200}", res.get(0));
    }

    @Test
    public void registerTest(){
        Backend backend = new Backend("register", "hello&second_word=there&last_word=world");
        Thread thread = new Thread(backend);
        thread.start();
        ArrayList<String> res = backend.getResult();
        assertEquals("{\"message\":\"OK\",\"userId\":\"91\",\"code\":200}", res.get(0));
    }

    @Test
    public void defaultTest(){
        Backend backend = new Backend("random", "random");
        Thread thread = new Thread(backend);
        thread.start();
        ArrayList<String> res = backend.getResult();
        assertEquals("No endpoint exists for: random", res.get(0));
    }


}
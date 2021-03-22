package com.example.fisev2concierge.localApis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class GetRequestFrameworkTest {

    @Test
    public void makeRequestTest(){
        GetRequestFramework getRequestFramework = new GetRequestFramework("https://api.postcodes.io/");
        ArrayList<String> responseArrayList = getRequestFramework.makeRequest("postcodes?lon=-0.141588&lat=51.501009");
        assertTrue(responseArrayList.size()>0);
    }

    @Test
    public void errorMakeRequestTest(){
        GetRequestFramework getRequestFramework = new GetRequestFramework("api.postcodes.io/");
        assertTrue(getRequestFramework.makeRequest("postcodes?lon=-0.141588&lat=51.501009").isEmpty());
    }

}
package com.example.fisev2concierge.localApis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class GetRequestFrameworkTest {

    @Test
    public void testApiCall(){
        GetRequestFramework getRequestFramework = new GetRequestFramework("https://api.postcodes.io/");
        ArrayList<String> responeArrayList = getRequestFramework.makeRequest("postcodes?lon=-0.141588&lat=51.501009");
        assertTrue(responeArrayList.size()>0);
    }

}
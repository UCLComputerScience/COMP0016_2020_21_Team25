package com.example.fisev2concierge.localApis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class GetLocationTest {

    @Test
    public void testApiCall(){
        GetLocation getLocation = new GetLocation(null, null, 51.501009, -0.141588);
        Thread thread = new Thread(getLocation);
        thread.start();
        String postcode = getLocation.getPostcode();
        assertEquals("SW1A 1AA", postcode);
    }

}
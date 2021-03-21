package com.example.fisev2concierge.functionalityClasses;

import com.example.fisev2concierge.helperClasses.TransportApiResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class TransportApiResponseTest {

    @Test
    public void searchForTransportTrainStationTest(){
        TransportApiResponse transportApiResponse = new TransportApiResponse();
        assertEquals("https://www.google.com/maps/search/nearest+train+station/@{lat},{lon}", transportApiResponse.searchForTransport("train_station", "https://www.google.com/maps/search/{query}/@{lat},{lon}"));
    }

    @Test
    public void searchForTransportBusStopTest(){
        TransportApiResponse transportApiResponse = new TransportApiResponse();
        assertEquals("https://www.google.com/maps/search/nearest+bus+stop/@{lat},{lon}", transportApiResponse.searchForTransport("bus_stop", "https://www.google.com/maps/search/{query}/@{lat},{lon}"));
    }

    @Test
    public void searchForTransportUknownTest(){
        TransportApiResponse transportApiResponse = new TransportApiResponse();
        assertEquals("https://www.google.com/maps/search/nearby+amenities/@{lat},{lon}", transportApiResponse.searchForTransport("random", "https://www.google.com/maps/search/{query}/@{lat},{lon}"));
    }


}
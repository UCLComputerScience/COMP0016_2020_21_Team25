package com.example.fisev2concierge.localApis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class FindServerIpAddressTest {

    @Test
    public void getIpTest(){
         FindServerIpAddress findServerIpAddress = new FindServerIpAddress();
         Thread thread = new Thread(findServerIpAddress);
         thread.start();
         //ip will be different for everyone who runs the test
         assertNotNull(findServerIpAddress.getIp());
     }
}
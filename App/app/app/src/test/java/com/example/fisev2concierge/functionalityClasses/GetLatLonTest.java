package com.example.fisev2concierge.functionalityClasses;

import android.Manifest;

import com.example.fisev2concierge.UI.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class GetLatLonTest {

    @Test
    public void requestPermissionsTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Service_Type", "LOOKUP");
        hashMap.put("Service", "Yell Search");
        hashMap.put("Application", "plumber");
        GetLatLon getLatLon = new GetLatLon(activity, activity, hashMap, activity, null);
        ShadowActivity shadowActivity = shadowOf(activity);
        getLatLon.searchLatLon();
        assertEquals("android.permission.ACCESS_FINE_LOCATION", shadowActivity.getLastRequestedPermission().requestedPermissions[0]);
    }

    @Test
    public void handleLocationFailureForYellSearchTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Service_Type", "LOOKUP");
        hashMap.put("Service", "Yell Search");
        hashMap.put("Application", "plumber");
        GetLatLon getLatLon = new GetLatLon(activity, activity, hashMap, activity, null);
        ShadowActivity shadowActivity = shadowOf(activity);
        shadowActivity.grantPermissions(Manifest.permission.ACCESS_FINE_LOCATION);
        shadowActivity.grantPermissions(Manifest.permission.ACCESS_COARSE_LOCATION);
        getLatLon.searchLatLon();
    }

    @Test
    public void handleLocationFailureForTransportTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Service_Type", "API_CALL");
        hashMap.put("Service", "Transport");
        hashMap.put("Transport Type", "train_station");
        GetLatLon getLatLon = new GetLatLon(activity, activity, hashMap, activity, null);
        ShadowActivity shadowActivity = shadowOf(activity);
        shadowActivity.grantPermissions(Manifest.permission.ACCESS_FINE_LOCATION);
        shadowActivity.grantPermissions(Manifest.permission.ACCESS_COARSE_LOCATION);
        getLatLon.searchLatLon();
    }

}
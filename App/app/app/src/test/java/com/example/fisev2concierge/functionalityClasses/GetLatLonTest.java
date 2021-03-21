package com.example.fisev2concierge.functionalityClasses;

import android.content.Intent;
import android.net.Uri;
import android.Manifest;

import com.example.fisev2concierge.UI.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.shadows.ShadowToast;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;
import static org.robolectric.shadow.api.Shadow.newInstanceOf;

@RunWith(RobolectricTestRunner.class)
public class GetLatLonTest {
    //Add test for getting permission

    @Test
    public void makingCall() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        HashMap hashMap = new HashMap();
        hashMap.put("Service_Type", "LOOKUP");
        hashMap.put("Service", "Yell Search");
        hashMap.put("Application", "plumber");
        GetLatLon getLatLon = new GetLatLon(activity, activity, hashMap, activity, null);
        getLatLon.searchLatLon();
        ShadowActivity shadowActivity = shadowOf(activity);
        shadowActivity.grantPermissions(Manifest.permission.ACCESS_FINE_LOCATION);
        shadowActivity.grantPermissions(Manifest.permission.ACCESS_COARSE_LOCATION);
        getLatLon.searchLatLon();
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.yell.com/ucs/UcsSearchAction.do?keywords=plumber&location=london"));
        Intent actual = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(actual);
        assertEquals(expectedIntent.getComponent().getClass(), shadowIntent.getIntentClass());
    }

    @Test
    public void testApiTransportOnLocationFailure(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        HashMap hashMap = new HashMap();
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
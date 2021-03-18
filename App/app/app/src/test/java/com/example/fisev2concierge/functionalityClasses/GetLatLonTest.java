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
        hashMap.put("Service_Type", "YELL_SEARCH");
        hashMap.put("Application", "plumber");
        GetLatLon getLatLon = new GetLatLon(activity, activity, hashMap, activity, null);
        getLatLon.searchLatLon();
        ShadowActivity shadowApplication = shadowOf(activity);
        shadowApplication.grantPermissions(Manifest.permission.ACCESS_FINE_LOCATION);
        shadowApplication.grantPermissions(Manifest.permission.ACCESS_COARSE_LOCATION);
        getLatLon.searchLatLon();
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.yell.com/ucs/UcsSearchAction.do?keywords=plumber&location=london"));
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

}
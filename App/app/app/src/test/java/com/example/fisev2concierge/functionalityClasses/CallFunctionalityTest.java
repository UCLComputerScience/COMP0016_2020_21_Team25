package com.example.fisev2concierge.functionalityClasses;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;

import com.example.fisev2concierge.UI.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class CallFunctionalityTest {
    //Adding tests for buttons on MainActivity
    @Test
    public void getREQUESTCALL(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        CallFunctionality callFunctionality = new CallFunctionality(activity, activity);
        assertEquals(3, callFunctionality.getREQUESTCALL());
    }

    @Test
    public void makingCall() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        CallFunctionality callFunctionality = new CallFunctionality(activity, activity);
        callFunctionality.makePhoneCall("012345678910");
        ShadowActivity shadowApplication = shadowOf(activity);
        shadowApplication.grantPermissions(Manifest.permission.CALL_PHONE);
        callFunctionality.makePhoneCall("012345678910");
        Intent expectedIntent = new Intent(Intent.ACTION_CALL, Uri.parse("012345678910"));
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

}
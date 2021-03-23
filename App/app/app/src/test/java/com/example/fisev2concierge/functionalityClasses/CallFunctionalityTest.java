package com.example.fisev2concierge.functionalityClasses;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;

import com.example.fisev2concierge.views.HistoryActivity;
import com.example.fisev2concierge.views.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class CallFunctionalityTest {
    //Adding tests for buttons on MainActivity
    @Test
    public void getREQUESTCALLTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        CallFunctionality callFunctionality = new CallFunctionality(activity, activity);
        assertEquals(3, callFunctionality.getREQUESTCALL());
    }

    @Test
    public void makePhoneCallWithoutPermissionTest() {
        HistoryActivity activity = Robolectric.buildActivity(HistoryActivity.class).get();
        ShadowActivity shadowActivity = shadowOf(activity);
        CallFunctionality callFunctionality = new CallFunctionality(activity, activity);
        callFunctionality.makePhoneCall("012345678910");
        assertEquals("android.permission.CALL_PHONE", shadowActivity.getLastRequestedPermission().requestedPermissions[0]);
    }

    @Test
    public void makePhoneCallWithPermissionTest(){
        HistoryActivity activity = Robolectric.buildActivity(HistoryActivity.class).get();
        ShadowActivity shadowActivity = shadowOf(activity);
        shadowActivity.grantPermissions(Manifest.permission.CALL_PHONE);
        CallFunctionality callFunctionality = new CallFunctionality(activity, activity);
        callFunctionality.makePhoneCall("012345678910");
        Intent expectedIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:012345678910"));
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getData(), actual.getData());
    }

}
package com.example.fisev2concierge.functionalityClasses;

import android.Manifest;
import android.widget.Toast;

import com.example.fisev2concierge.UI.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.robolectric.RuntimeEnvironment.application;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class SmsFunctionalityTest {
    //Adding tests for buttons on MainActivity

    @Test
    public void makingCall() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        SmsFunctionality smsFunctionality = new SmsFunctionality(activity, activity);
        smsFunctionality.sendSMS("012345678910", "test");
        ShadowActivity shadowApplication = shadowOf(activity);
        shadowApplication.grantPermissions(Manifest.permission.SEND_SMS);
        smsFunctionality.sendSMS("012345678910", "test");
        List<Toast> toasts = shadowOf(application).getShownToasts();
        assertEquals(toasts.toArray().length, 1);
//       can only check if toast was produced not if actual message was sent
    }

    @Test
    public void makingCallException() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        SmsFunctionality smsFunctionality = new SmsFunctionality(activity, activity);
        ShadowActivity shadowApplication = shadowOf(activity);
        shadowApplication.grantPermissions(Manifest.permission.SEND_SMS);
        smsFunctionality.sendSMS(null, null);
        List<Toast> toasts = shadowOf(application).getShownToasts();
        assertEquals(toasts.toArray().length, 1);
    }
}
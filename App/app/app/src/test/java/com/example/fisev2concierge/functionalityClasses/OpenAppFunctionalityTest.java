package com.example.fisev2concierge.functionalityClasses;

import android.content.Intent;
import android.net.Uri;

import com.example.fisev2concierge.UI.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class OpenAppFunctionalityTest {
    //Adding tests for buttons on MainActivity

    @Test
    public void openAppOnPhone() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        OpenAppFunctionality openAppFunctionality = new OpenAppFunctionality(activity, activity);
        openAppFunctionality.openApp("fise v2 concierge");
        Intent expectedIntent = activity.getPackageManager().getLaunchIntentForPackage("com.example.fisev2concierge");
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void openAppOnPlaystore() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        OpenAppFunctionality openAppFunctionality = new OpenAppFunctionality(activity, activity);
        openAppFunctionality.openApp("snapchat");
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW);
        expectedIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + "com.snapchat.android"));
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void searchAppOnPlaystore() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        OpenAppFunctionality openAppFunctionality = new OpenAppFunctionality(activity, activity);
        openAppFunctionality.openApp("candy crush");
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW);
        expectedIntent.setData(Uri.parse("https://play.google.com/store/search?q=" + "candy+crush"));
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}
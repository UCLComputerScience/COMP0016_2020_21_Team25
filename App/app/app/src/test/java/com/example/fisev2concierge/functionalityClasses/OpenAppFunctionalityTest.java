package com.example.fisev2concierge.functionalityClasses;

import android.content.Intent;
import android.net.Uri;

import com.example.fisev2concierge.UI.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class OpenAppFunctionalityTest {

    @Test
    public void openAppOnPhoneTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        OpenAppFunctionality openAppFunctionality = new OpenAppFunctionality(activity, activity);
        openAppFunctionality.openApp("fise v2 concierge");
        Intent expectedIntent = activity.getPackageManager().getLaunchIntentForPackage("com.example.fisev2concierge");
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void openAppOnPlaystoreTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        OpenAppFunctionality openAppFunctionality = new OpenAppFunctionality(activity, activity);
        openAppFunctionality.openApp("snapchat");
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.snapchat.android"));
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void searchAppOnPlaystoreTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        OpenAppFunctionality openAppFunctionality = new OpenAppFunctionality(activity, activity);
        openAppFunctionality.openApp("candy crush");
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/search?q=" + "candy+crush"));
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}
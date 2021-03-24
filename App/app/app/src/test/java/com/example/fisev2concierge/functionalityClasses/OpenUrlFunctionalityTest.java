package com.example.fisev2concierge.functionalityClasses;

import android.content.Intent;
import android.net.Uri;

import com.example.fisev2concierge.views.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class OpenUrlFunctionalityTest {

    @Test
    public void openWebTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        OpenUrlFunctionality openUrlFunctionality = new OpenUrlFunctionality(activity);
        openUrlFunctionality.openWeb("amazon");
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.co.uk/"));
        ShadowActivity shadowActivity = new ShadowActivity();
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getData(), actual.getData());
    }

    @Test
    public void openWebNotFoundTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        OpenUrlFunctionality openUrlFunctionality = new OpenUrlFunctionality(activity);
        openUrlFunctionality.openWeb("ebay");
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=ebay"));
        ShadowActivity shadowActivity = new ShadowActivity();
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getData(), actual.getData());
    }

    @Test
    public void searchWebAmazonTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        OpenUrlFunctionality openUrlFunctionality = new OpenUrlFunctionality(activity);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Application", "ipad");
        openUrlFunctionality.searchWeb("amazon", hashMap);
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.co.uk/s?k=ipad"));
        ShadowActivity shadowActivity = new ShadowActivity();
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getData(), actual.getData());
    }

    @Test
    public void searchWebYellTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        OpenUrlFunctionality openUrlFunctionality = new OpenUrlFunctionality(activity);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Application", "plumber");
        hashMap.put("location", "london");
        openUrlFunctionality.searchWeb("yell", hashMap);
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.yell.com/ucs/UcsSearchAction.do?keywords=plumber&location=london"));
        ShadowActivity shadowActivity = new ShadowActivity();
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getData(), actual.getData());
    }

    @Test
    public void searchWebGoogleTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        OpenUrlFunctionality openUrlFunctionality = new OpenUrlFunctionality(activity);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Application", "ipad");
        openUrlFunctionality.searchWeb("google", hashMap);
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=ipad"));
        ShadowActivity shadowActivity = new ShadowActivity();
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getData(), actual.getData());
    }

    @Test
    public void searchWebNotFoundTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        OpenUrlFunctionality openUrlFunctionality = new OpenUrlFunctionality(activity);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Application", "ipad");
        openUrlFunctionality.searchWeb("ebay", hashMap);
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=ebay+ipad"));
        ShadowActivity shadowActivity = new ShadowActivity();
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getData(), actual.getData());
    }

}
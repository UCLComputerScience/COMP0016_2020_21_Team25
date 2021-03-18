package com.example.fisev2concierge.functionalityClasses;

import android.content.Intent;
import android.net.Uri;

import com.example.fisev2concierge.UI.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class OpenUrlFunctionalityTest {
    //Adding tests for buttons on MainActivity

    @Test
    public void openWeb() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        OpenUrlFunctionality openUrlFunctionality = new OpenUrlFunctionality(activity);
        openUrlFunctionality.openWeb("amazon");
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.co.uk/"));
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void openWebNotFound() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        OpenUrlFunctionality openUrlFunctionality = new OpenUrlFunctionality(activity);
        openUrlFunctionality.openWeb("ebay");
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=ebay"));
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void searchWebAmazon() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        OpenUrlFunctionality openUrlFunctionality = new OpenUrlFunctionality(activity);
        HashMap hashMap = new HashMap();
        hashMap.put("Application", "ipad");
        openUrlFunctionality.searchWeb("amazon", hashMap);
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.co.uk/s?k=ipad"));
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void searchWebYell() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        OpenUrlFunctionality openUrlFunctionality = new OpenUrlFunctionality(activity);
        HashMap hashMap = new HashMap();
        hashMap.put("Application", "plumber");
        hashMap.put("location", "london");
        openUrlFunctionality.searchWeb("yell", hashMap);
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.yell.com/ucs/UcsSearchAction.do?keywords=plumber&location=london"));
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void searchWebGoogle() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        OpenUrlFunctionality openUrlFunctionality = new OpenUrlFunctionality(activity);
        HashMap hashMap = new HashMap();
        hashMap.put("Application", "ipad");
        openUrlFunctionality.searchWeb("google", hashMap);
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=ipad"));
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void searchWebNotFound() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        OpenUrlFunctionality openUrlFunctionality = new OpenUrlFunctionality(activity);
        HashMap hashMap = new HashMap();
        hashMap.put("Application", "ipad");
        openUrlFunctionality.searchWeb("ebay", hashMap);
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=ebay+ipad"));
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

}
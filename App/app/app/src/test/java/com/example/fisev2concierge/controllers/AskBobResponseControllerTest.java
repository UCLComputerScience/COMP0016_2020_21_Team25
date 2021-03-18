package com.example.fisev2concierge.controllers;

import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.example.fisev2concierge.UI.MainActivity;
import com.example.fisev2concierge.UI.ViewRemindersActivity;
import com.example.fisev2concierge.speech.SpeechSynthesis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;
import static org.robolectric.RuntimeEnvironment.application;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class AskBobResponseControllerTest {

    @Test
    public void testApiCall(){
        //Unable to check if desired output is produced from speech synthesis
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        HashMap hashMap = new HashMap();
        hashMap.put("Service_Type", "API_CALL");
        hashMap.put("Response", "response");
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        askBobResponseController.responseController(hashMap, activity, activity, activity, speechSynthesis);
    }

    @Test
    public void testContactCall(){
        //Unable to check if desired output is produced as unable to mock contacts
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        HashMap hashMap = new HashMap();
        hashMap.put("Service_Type", "CALL_CONTACT");
        hashMap.put("Contact", "contact");
        askBobResponseController.responseController(hashMap, activity, activity, activity, null);
    }

    @Test
    public void testContactMessage(){
        //Unable to check if desired output is produced as unable to mock contacts or check if sms was sent correctly
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        HashMap hashMap = new HashMap();
        hashMap.put("Service_Type", "SMS_CONTACT");
        hashMap.put("Contact", "contact");
        askBobResponseController.responseController(hashMap, activity, activity, activity, null);
    }

    @Test
    public void testOpenApp(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        HashMap hashMap = new HashMap();
        hashMap.put("Service_Type", "OPEN_APP");
        hashMap.put("Application", "snapchat");
        askBobResponseController.responseController(hashMap, activity, activity, activity, null);
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://play.google.com/store/apps/details?id=com.snapchat.android"));
        Intent actual = shadowOf(application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void testShopSearch(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        HashMap hashMap = new HashMap();
        hashMap.put("Service_Type", "SHOP_SEARCH");
        hashMap.put("Service", "amazon");
        hashMap.put("Application", "ipad");
        askBobResponseController.responseController(hashMap, activity, activity, activity, null);
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.co.uk/s?k=ipad"));
        Intent actual = shadowOf(application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void testYellSearch(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        HashMap hashMap = new HashMap();
        hashMap.put("Service_Type", "YELL_SEARCH");
        hashMap.put("Application", "plumber");
        hashMap.put("location", "london");
        askBobResponseController.responseController(hashMap, activity, activity, activity, null);
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.yell.com/ucs/UcsSearchAction.do?keywords=plumber&location=london"));
        Intent actual = shadowOf(application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void testOpenActivity(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        HashMap hashMap = new HashMap();
        hashMap.put("Service_Type", "NAVIGATE_APP");
        hashMap.put("Application", "reminders");
        askBobResponseController.responseController(hashMap, activity, activity, activity, null);
        Intent expectedIntent = new Intent(activity, ViewRemindersActivity.class);
        Intent actual = shadowOf(application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());

    }

    @Test
    public void testError(){
        //can only check if toast message is displayed in this case
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        HashMap hashMap = new HashMap();
        hashMap.put("Service_Type", "ERROR");
        hashMap.put("text", "error");
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        askBobResponseController.responseController(hashMap, activity, activity, activity, speechSynthesis);
        List<Toast> toasts = shadowOf(application).getShownToasts();
        assertEquals(toasts.toArray().length, 1);
    }

    @Test
    public void testDefault(){
        //can only check if toast message is displayed in this case
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        HashMap hashMap = new HashMap();
        hashMap.put("Service_Type", "UNKNOWN");
        askBobResponseController.responseController(hashMap, activity, activity, activity, null);
        List<Toast> toasts = shadowOf(application).getShownToasts();
        assertEquals(toasts.toArray().length, 1);
    }

}
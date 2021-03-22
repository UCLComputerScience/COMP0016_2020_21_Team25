package com.example.fisev2concierge.localApis.askBobConnectivity;

import android.content.Intent;
import android.net.Uri;

import com.example.fisev2concierge.UI.MainActivity;
import com.example.fisev2concierge.controllers.MainController;
import com.example.fisev2concierge.speech.SpeechSynthesis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowToast;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class AskBobResponseHandlerTest {

    @Test
    public void handleResponseYellSearchTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        MainController mainController = new MainController();
        mainController.addUserID(activity, "101");
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Service_Type", "LOOKUP");
        hashMap.put("Service", "Yell Search");
        hashMap.put("Application", "plumber");
        AskBobResponseHandler askBobResponseHandler = new AskBobResponseHandler(hashMap, activity, activity, activity, speechSynthesis);
        askBobResponseHandler.handleResponse();
    }

    @Test
    public void handleApiIfUserIdExistServiceAllowedTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        MainController mainController = new MainController();
        mainController.addUserID(activity, "101");
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Service_Type", "API_CALL");
        hashMap.put("Service", "Weather");
        hashMap.put("Response", "test");
        AskBobResponseHandler askBobResponseHandler = new AskBobResponseHandler(hashMap, activity, activity, activity, speechSynthesis);
        askBobResponseHandler.handleResponse();
    }

    @Test
    public void handleApiIfUserIdExistServiceNotAllowedTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        MainController mainController = new MainController();
        mainController.addUserID(activity, "101");
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Service_Type", "API_CALL");
        hashMap.put("Service", "News");
        hashMap.put("Response", "test");
        AskBobResponseHandler askBobResponseHandler = new AskBobResponseHandler(hashMap, activity, activity, activity, speechSynthesis);
        askBobResponseHandler.handleResponse();
        assertEquals("Service not added by admin", ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void handleApiIfNoUserIdExistsTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Service_Type", "API_CALL");
        hashMap.put("Service", "Weather");
        hashMap.put("Response", "test");
        AskBobResponseHandler askBobResponseHandler = new AskBobResponseHandler(hashMap, activity, activity, activity, speechSynthesis);
        askBobResponseHandler.handleResponse();
    }

    @Test
    public void handleApiTransportIfNoUserIdExistsTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Service_Type", "API_CALL");
        hashMap.put("Service", "Transport");
        hashMap.put("Transport Type", "train_station");
        AskBobResponseHandler askBobResponseHandler = new AskBobResponseHandler(hashMap, activity, activity, activity, speechSynthesis);
        askBobResponseHandler.handleResponse();
    }

    @Test
    public void handleNonApiCallTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Service_Type", "APP_SERVICE");
        hashMap.put("Service", "Open App");
        hashMap.put("Application", "snapchat");
        AskBobResponseHandler askBobResponseHandler = new AskBobResponseHandler(hashMap, activity, activity, activity, speechSynthesis);
        askBobResponseHandler.handleResponse();
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.snapchat.android"));
        ShadowActivity shadowActivity = new ShadowActivity();
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getData(), actual.getData());
    }
}
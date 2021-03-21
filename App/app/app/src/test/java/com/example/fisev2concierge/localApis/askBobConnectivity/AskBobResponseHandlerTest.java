package com.example.fisev2concierge.localApis.askBobConnectivity;

import static org.junit.Assert.*;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fisev2concierge.UI.MainActivity;
import com.example.fisev2concierge.R;
import com.example.fisev2concierge.UI.ViewRemindersActivity;
import com.example.fisev2concierge.controllers.MainController;
import com.example.fisev2concierge.helperClasses.NotificationHelper;
import com.example.fisev2concierge.speech.SpeechSynthesis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowToast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.robolectric.RuntimeEnvironment.application;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class AskBobResponseHandlerTest {

    @Test
    public void handleResponseYellSearchTest(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        MainController mainController = new MainController();
        mainController.addUserID(activity, "101");
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        HashMap hashMap = new HashMap();
        hashMap.put("Service_Type", "LOOKUP");
        hashMap.put("Service", "Yell Search");
        hashMap.put("Application", "plumber");
        AskBobResponseHandler askBobResponseHandler = new AskBobResponseHandler(hashMap, activity, activity, activity, speechSynthesis);
        askBobResponseHandler.handleResponse();
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.yell.com/ucs/UcsSearchAction.do?keywords=plumber&location=london"));
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void handleApiIfUserIdExistServiceAllowedTest(){
        //no way to test if this actually works
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        MainController mainController = new MainController();
        mainController.addUserID(activity, "101");
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        HashMap hashMap = new HashMap();
        hashMap.put("Service_Type", "API_CALL");
        hashMap.put("Service", "Weather");
        hashMap.put("Response", "test");
        AskBobResponseHandler askBobResponseHandler = new AskBobResponseHandler(hashMap, activity, activity, activity, speechSynthesis);
        askBobResponseHandler.handleResponse();
    }

    @Test
    public void handleApiIfUserIdExistServiceNotAllowedTest(){
        //no way to test if this actually works
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        MainController mainController = new MainController();
        mainController.addUserID(activity, "101");
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        HashMap hashMap = new HashMap();
        hashMap.put("Service_Type", "API_CALL");
        hashMap.put("Service", "News");
        hashMap.put("Response", "test");
        AskBobResponseHandler askBobResponseHandler = new AskBobResponseHandler(hashMap, activity, activity, activity, speechSynthesis);
        askBobResponseHandler.handleResponse();
        assertEquals("Service not authorised by admin", ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void handleApiIfNoUserIdExistsTest(){
        //no way to test if this actually works
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        MainController mainController = new MainController();
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        HashMap hashMap = new HashMap();
        hashMap.put("Service_Type", "API_CALL");
        hashMap.put("Service", "Weather");
        hashMap.put("Response", "test");
        AskBobResponseHandler askBobResponseHandler = new AskBobResponseHandler(hashMap, activity, activity, activity, speechSynthesis);
        askBobResponseHandler.handleResponse();
    }

    @Test
    public void handleApiTransportIfNoUserIdExistsTest(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        MainController mainController = new MainController();
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        HashMap hashMap = new HashMap();
        hashMap.put("Service_Type", "API_CALL");
        hashMap.put("Service", "Transport");
        hashMap.put("Transport Type", "train_station");
        AskBobResponseHandler askBobResponseHandler = new AskBobResponseHandler(hashMap, activity, activity, activity, speechSynthesis);
        askBobResponseHandler.handleResponse();
//        assertEquals("Location unavailable", ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void handleNonApiCallTest(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        MainController mainController = new MainController();
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        HashMap hashMap = new HashMap();
        hashMap.put("Service_Type", "APP_SERVICE");
        hashMap.put("Service", "Open App");
        hashMap.put("Application", "snapchat");
        AskBobResponseHandler askBobResponseHandler = new AskBobResponseHandler(hashMap, activity, activity, activity, speechSynthesis);
        askBobResponseHandler.handleResponse();
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW);
        expectedIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + "com.snapchat.android"));
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}
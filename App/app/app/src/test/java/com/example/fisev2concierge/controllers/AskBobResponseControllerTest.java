package com.example.fisev2concierge.controllers;

import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.example.fisev2concierge.UI.HistoryActivity;
import com.example.fisev2concierge.UI.MainActivity;
import com.example.fisev2concierge.UI.ViewRemindersActivity;
import com.example.fisev2concierge.speech.SpeechSynthesis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowToast;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;
import static org.robolectric.RuntimeEnvironment.application;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class AskBobResponseControllerTest {

    @Test
    public void handleApiResponseTest(){
        //no way to test if this actually works
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        HashMap parsedResponse = new HashMap();
        parsedResponse.put("Service_Type", "API_CALL");
        parsedResponse.put("Service", "Weather");
        parsedResponse.put("Response", "this is a test");
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        askBobResponseController.responseController(parsedResponse, activity, activity, activity, speechSynthesis);
    }

    @Test
    public void handleTransportApiResponseTest(){
        //no way to test if this actually works
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        HashMap parsedResponse = new HashMap();
        parsedResponse.put("Service_Type", "API_CALL");
        parsedResponse.put("Service", "Transport");
        parsedResponse.put("Response", "this is a test");
        parsedResponse.put("Message", "this is a test");
        parsedResponse.put("lat", "51.501009");
        parsedResponse.put("lon", "-0.141588");
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        askBobResponseController.responseController(parsedResponse, activity, activity, activity, speechSynthesis);
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/@51.501009,-0.141588,14z"));
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void handleRecipesApiResponseTest(){
        //no way to test if this actually works
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        HashMap parsedResponse = new HashMap();
        parsedResponse.put("Service_Type", "API_CALL");
        parsedResponse.put("Service", "Recipes");
        parsedResponse.put("Response", "this is a test");
        parsedResponse.put("Steps", "steps...");
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        askBobResponseController.responseController(parsedResponse, activity, activity, activity, speechSynthesis);
    }

    @Test
    public void handleBooksInfoApiResponseTest(){
        //no way to test if this actually works
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        HashMap parsedResponse = new HashMap();
        parsedResponse.put("Service_Type", "API_CALL");
        parsedResponse.put("Service", "Books");
        parsedResponse.put("Response", "this is a test");
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        askBobResponseController.responseController(parsedResponse, activity, activity, activity, speechSynthesis);
    }

    @Test
    public void handleBooksOpenApiResponseTest(){
        //no way to test if this actually works
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        HashMap parsedResponse = new HashMap();
        parsedResponse.put("Service_Type", "API_CALL");
        parsedResponse.put("Service", "Books");
        parsedResponse.put("Response", "https://www.gutenberg.org/");
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        askBobResponseController.responseController(parsedResponse, activity, activity, activity, speechSynthesis);
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gutenberg.org/"));
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void handleCallResponse(){
        //no way to test if this works
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        HashMap parsedResponse = new HashMap();
        parsedResponse.put("Service_Type", "APP_SERVICE");
        parsedResponse.put("Service", "Call Contact");
        parsedResponse.put("Response", "this is a test");
        parsedResponse.put("Contact", "contact");
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        askBobResponseController.responseController(parsedResponse, activity, activity, activity, speechSynthesis);
    }

    @Test
    public void handleMessageResponse(){
        //no way to test if this works
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        HashMap parsedResponse = new HashMap();
        parsedResponse.put("Service_Type", "APP_SERVICE");
        parsedResponse.put("Service", "SMS Contact");
        parsedResponse.put("Response", "this is a test");
        parsedResponse.put("Contact", "contact");
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        askBobResponseController.responseController(parsedResponse, activity, activity, activity, speechSynthesis);
    }

    @Test
    public void handleOpenAppResponse(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        HashMap parsedResponse = new HashMap();
        parsedResponse.put("Service_Type", "APP_SERVICE");
        parsedResponse.put("Service", "Open App");
        parsedResponse.put("Application", "snapchat");
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        askBobResponseController.responseController(parsedResponse, activity, activity, activity, null);
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW);
        expectedIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + "com.snapchat.android"));
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void handleShopSearchResponse(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        HashMap parsedResponse = new HashMap();
        parsedResponse.put("Service_Type", "LOOKUP");
        parsedResponse.put("Service", "Shop Search");
        parsedResponse.put("Response", "Searching AMAZON for BAKED BEANS");
        parsedResponse.put("Shop", "amazon");
        parsedResponse.put("Application", "ipad");
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        askBobResponseController.responseController(parsedResponse, activity, activity, activity, speechSynthesis);
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.co.uk/s?k=ipad"));
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void handleYellSearchResponse(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        HashMap parsedResponse = new HashMap();
        parsedResponse.put("Service_Type", "LOOKUP");
        parsedResponse.put("Service", "Yell Search");
        parsedResponse.put("Response", "Searching YELL for PLUMBER");
        parsedResponse.put("Application", "plumber");
        parsedResponse.put("location", "london");
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        askBobResponseController.responseController(parsedResponse, activity, activity, activity, speechSynthesis);
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.yell.com/ucs/UcsSearchAction.do?keywords=plumber&location=london"));
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void handleNavigateAppResponse(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        HashMap parsedResponse = new HashMap();
        parsedResponse.put("Service_Type", "APP_SERVICE");
        parsedResponse.put("Service", "Navigate App");
        parsedResponse.put("Application", "history");
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        askBobResponseController.responseController(parsedResponse, activity, activity, activity, null);
        Intent expectedIntent = new Intent(activity, HistoryActivity.class);;
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void handleErrorResponse(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        HashMap parsedResponse = new HashMap();
        parsedResponse.put("Service_Type", "ERROR");
        parsedResponse.put("Service", "ERROR");
        parsedResponse.put("text", "error");
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        askBobResponseController.responseController(parsedResponse, activity, activity, activity, speechSynthesis);
        assertEquals("Command not understood", ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void defaultTest(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        HashMap parsedResponse = new HashMap();
        parsedResponse.put("Service_Type", "RANDOM");
        parsedResponse.put("Service", "RANDOM");
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        askBobResponseController.responseController(parsedResponse, activity, activity, activity, null);
        assertEquals("Error: unknown command: RANDOM", ShadowToast.getTextOfLatestToast());
    }

}
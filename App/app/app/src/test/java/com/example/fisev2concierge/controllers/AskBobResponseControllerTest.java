package com.example.fisev2concierge.controllers;

import android.content.Intent;
import android.net.Uri;

import com.example.fisev2concierge.views.HistoryActivity;
import com.example.fisev2concierge.views.MainActivity;
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
public class AskBobResponseControllerTest {

    @Test
    public void handleApiResponseTest(){
        //no way to test if this actually works
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        HashMap<String, String> parsedResponse = new HashMap<>();
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
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        HashMap<String, String> parsedResponse = new HashMap<>();
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
        ShadowActivity shadowActivity = new ShadowActivity();
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getData(), actual.getData());
    }

    @Test
    public void handleRecipesApiResponseTest(){
        //no way to test if this actually works
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        HashMap<String, String> parsedResponse = new HashMap<>();
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
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        HashMap<String, String> parsedResponse = new HashMap<>();
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
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        HashMap<String, String> parsedResponse = new HashMap<>();
        parsedResponse.put("Service_Type", "API_CALL");
        parsedResponse.put("Service", "Books");
        parsedResponse.put("Response", "https://www.gutenberg.org/");
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        askBobResponseController.responseController(parsedResponse, activity, activity, activity, speechSynthesis);
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gutenberg.org/"));
        ShadowActivity shadowActivity = new ShadowActivity();
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getData(), actual.getData());
    }

    @Test
    public void handleCallResponseTest(){
        //no way to test if this works
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        HashMap<String, String> parsedResponse = new HashMap<>();
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
    public void handleMessageResponseTest(){
        //no way to test if this works
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        HashMap<String, String> parsedResponse = new HashMap<>();
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
    public void handleOpenAppResponseTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        HashMap<String, String> parsedResponse = new HashMap<>();
        parsedResponse.put("Service_Type", "APP_SERVICE");
        parsedResponse.put("Service", "Open App");
        parsedResponse.put("Application", "snapchat");
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        askBobResponseController.responseController(parsedResponse, activity, activity, activity, null);
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW);
        expectedIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + "com.snapchat.android"));
        ShadowActivity shadowActivity = new ShadowActivity();
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getData(), actual.getData());
    }

    @Test
    public void handleShopSearchResponseTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        HashMap<String, String> parsedResponse = new HashMap<>();
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
        ShadowActivity shadowActivity = new ShadowActivity();
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getData(), actual.getData());
    }

    @Test
    public void handleYellSearchResponseTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        HashMap<String, String> parsedResponse = new HashMap<>();
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
        ShadowActivity shadowActivity = new ShadowActivity();
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getData(), actual.getData());
    }

    @Test
    public void handleNavigateAppResponseTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        HashMap<String, String> parsedResponse = new HashMap<>();
        parsedResponse.put("Service_Type", "APP_SERVICE");
        parsedResponse.put("Service", "Navigate App");
        parsedResponse.put("Application", "history");
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        askBobResponseController.responseController(parsedResponse, activity, activity, activity, null);
        Intent expectedIntent = new Intent(activity, HistoryActivity.class);
        ShadowActivity shadowActivity = new ShadowActivity();
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getData(), actual.getData());
    }

    @Test
    public void handleErrorResponseTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        HashMap<String, String> parsedResponse = new HashMap<>();
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
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        HashMap<String, String> parsedResponse = new HashMap<>();
        parsedResponse.put("Service_Type", "RANDOM");
        parsedResponse.put("Service", "RANDOM");
        AskBobResponseController askBobResponseController = new AskBobResponseController();
        askBobResponseController.responseController(parsedResponse, activity, activity, activity, null);
        assertEquals("Error: unknown command: RANDOM", ShadowToast.getTextOfLatestToast());
    }

}
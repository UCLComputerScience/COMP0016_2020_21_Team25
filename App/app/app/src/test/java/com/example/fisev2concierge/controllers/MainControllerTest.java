package com.example.fisev2concierge.controllers;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.TextView;

import com.example.fisev2concierge.R;
import com.example.fisev2concierge.views.MainActivity;
import com.example.fisev2concierge.views.ViewRemindersActivity;
import com.example.fisev2concierge.helperClasses.NotificationHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowToast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class MainControllerTest {

    @Test
    public void openUrlTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        MainController mainController = new MainController();
        mainController.openUrl(activity, "https://www.google.com");
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
        ShadowActivity shadowActivity = new ShadowActivity();
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getData(), actual.getData());
    }

    @Test
    public void handleAskBobResponseTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();
        MainController mainController = new MainController();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Service_Type", "APP_SERVICE");
        hashMap.put("Service", "Open App");
        hashMap.put("Application", "snapchat");
        mainController.handleAskBobResponse(hashMap, activity, activity, activity, null);
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.snapchat.android"));
        ShadowActivity shadowActivity = new ShadowActivity();
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getData(), actual.getData());
    }

    @Test
    public void findServerIpTest(){
        MainController mainController = new MainController();
        assertNotNull(mainController.findServerIp());
    }

    @Test
    public void parseAskBobResponseTest() {
        MainController mainController = new MainController();
        ArrayList<String> mockResponse = new ArrayList<>();
        mockResponse.add("{\"query\":\"open snapchat\",\"messages\":[{\"custom\":{\"Service_Type\":\"APP_SERVICE\",\"Service\":\"Open App\",\"Application\":\"SNAPCHAT\"}}]}");
        HashMap parsedResponse = mainController.parseAskBobResponse(mockResponse);
        assertEquals("APP_SERVICE", parsedResponse.get("Service_Type"));
        assertEquals("Open App", parsedResponse.get("Service"));
        assertEquals("snapchat", parsedResponse.get("Application"));
    }

    @Test
    public void askBobRequestTest(){
        //ensure Ask Bob server is running
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        MainController mainController = new MainController();
        HashMap hashMap = mainController.askBobRequest("open facebook", activity);
        assertTrue(hashMap.containsKey("Service_Type"));
        assertTrue(hashMap.containsKey("Application"));
    }

    @Test
    public void askBobControllerTest(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Service_Type", "APP_SERVICE");
        hashMap.put("Service", "Open App");
        hashMap.put("Application", "snapchat");
        mainController.askBobController(hashMap, activity, activity, activity, null);
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://play.google.com/store/apps/details?id=com.snapchat.android"));
        ShadowActivity shadowActivity = new ShadowActivity();
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getData(), actual.getData());
    }

    @Test
    public void askBobServicesTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        MainController mainController = new MainController();
        ArrayList<String> response = mainController.askBobServices("query", "message=\"open snapchat\"&sender=\"concierge\"", activity);
        assertTrue(response.size()>0);
    }



    @Test
    public void backendServicesTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        MainController mainController = new MainController();
        ArrayList<String> expectedResult = new ArrayList<>();
        expectedResult.add("{\"name\":\"\",\"category\":\"\",\"description\":\"\",\"code\":200}");
        assertEquals(expectedResult.get(0), mainController.backendServices("servicedata", "1", activity).get(0));
    }

    @Test
    public void handleUserRequestTest(){
        String[] request = new String[]{"open snapchat"};
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();
        MainController mainController = new MainController();
        mainController.handleUserRequest(request, null, mainActivity, mainActivity, mainActivity, mainActivity.findViewById(R.id.conciergeStatusText));
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://play.google.com/store/apps/details?id=com.snapchat.android"));
        ShadowActivity shadowActivity = new ShadowActivity();
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getData(), actual.getData());
    }

    @Test
    public void handleUserRequestSearchYellTest(){
        String[] request = new String[]{"find me a plumber"};
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();
        MainController mainController = new MainController();
        mainController.handleUserRequest(request, null, mainActivity, mainActivity, mainActivity, mainActivity.findViewById(R.id.conciergeStatusText));
    }

    @Test
    public void handleUserRequestEmptyRequestTest(){
        String[] request = new String[]{""};
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();
        TextView conciergeStatusText = mainActivity.findViewById(R.id.conciergeStatusText);
        MainController mainController = new MainController();
        mainController.handleUserRequest(request, null, mainActivity, mainActivity, mainActivity, conciergeStatusText);
        assertEquals("Empty input", ShadowToast.getTextOfLatestToast());
        assertEquals(conciergeStatusText.getText(), "Concierge is off");
    }

    @Test
    public void testTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        MainController mainController = new MainController();
        mainController.getLatLon(activity, activity, null , activity, null);
    }

    @Test
    public void getLocationTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        MainController mainController = new MainController();
        String postcode = mainController.getLocation(51.501009, -0.141588);
        assertEquals("SW1A 1AA", postcode);
    }

    @Test
    public void searchContactTest(){
        //can only assert output if contact is not found or if contact queries backend as unable to mock contacts
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        MainController mainController = new MainController();
        String number = mainController.searchContact("test", activity, activity, activity);
        assertEquals(number, "-1");
    }

    @Test
    public void makeCallTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        ShadowActivity shadowActivity = new ShadowActivity();
        shadowActivity.grantPermissions(Manifest.permission.CALL_PHONE);
        MainController mainController = new MainController();
        mainController.makeCall(activity, activity, "012345678910");
        Intent expectedIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:012345678910"));
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getData(), actual.getData());
    }

    @Test
    public void openActivityTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        MainController mainController = new MainController();
        mainController.openActivity(activity, activity, "reminders");
        Intent expectedIntent = new Intent(activity, ViewRemindersActivity.class);
        ShadowActivity shadowActivity = new ShadowActivity();
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }



    @Test
    public void getUserIdTest(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        mainController.addUserID(activity, "1");
        String userID = mainController.getUserID(activity);
        assertEquals("1", userID);
    }

    @Test
    public void addUserIdTest(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        mainController.addUserID(activity, "1");
        assertTrue(mainController.hasUserID(activity));
    }

    @Test
    public void hasUserIdTest(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        mainController.hasUserID(activity);
        assertFalse(mainController.hasUserID(activity));
    }

    @Test
    public void getRemindersTest(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        Cursor cursor = mainController.getReminders(activity);
        assertNotNull(cursor);
    }

    @Test
    public void addReminderTest(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        mainController.addReminder(activity, "test");
        assertEquals(1, mainController.getReminders(activity).getCount());
    }

    @Test
    public void updateReminderTest(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        mainController.addReminder(activity, "old reminder");
        mainController.updateReminder(activity, 1, "new reminder");
        Cursor cursor = mainController.getReminders(activity);
        cursor.moveToFirst();
        assertEquals("new reminder", cursor.getString(1));
    }

    @Test
    public void deleteReminderTest(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        mainController.addReminder(activity, "reminder");
        mainController.deleteReminder(activity, 1);
        assertEquals(0, mainController.getReminders(activity).getCount());
    }

    @Test
    public void getAlarmsTest(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        Cursor cursor = mainController.getAlarm(activity);
        assertNotNull(cursor);
    }

    @Test
    public void addAlarmTest(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        mainController.addAlarm(activity, "test", "date");
        assertEquals(1, mainController.getAlarm(activity).getCount());
    }

    @Test
    public void updateAlarmTest(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        mainController.addAlarm(activity, "test", "date");
        mainController.updateAlarm(activity, 1, "new alarm", "new date");
        Cursor cursor = mainController.getAlarm(activity);
        cursor.moveToFirst();
        assertEquals("new alarm", cursor.getString(1));
    }

    @Test
    public void deleteAlarmTest(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        mainController.addAlarm(activity, "test", "date");
        mainController.deleteAlarm(activity, 1);
        assertEquals(0, mainController.getAlarm(activity).getCount());
    }

    @Test
    public void getRecentAlarmTest(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        mainController.addAlarm(activity, "alarm 1", "date 1");
        mainController.addAlarm(activity, "alarm 2", "date 2");
        Cursor cursor = mainController.getRecentAlarm(activity);
        cursor.moveToFirst();
        assertEquals("alarm 2", cursor.getString(1));
    }

    @Test
    public void startAlarmTest(){
        //no way to check if this is working
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 1);
        c.set(Calendar.MINUTE, 1);
        c.set(Calendar.SECOND, 1);
        c.set(Calendar.YEAR, 2021);
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        mainController.startAlarm(activity, activity, "0", c, "message");
//        NotificationHelper notificationHelper = new NotificationHelper(activity);
//        assertEquals(notificationHelper.getManager().getActiveNotifications().length, 1);
    }

    @Test
    public void stopAlarmTest(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 1);
        c.set(Calendar.MINUTE, 1);
        c.set(Calendar.SECOND, 1);
        c.set(Calendar.YEAR, 2021);
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        mainController.startAlarm(activity, activity, "0", c, "message");
        mainController.stopAlarm(activity, activity, 0);
        NotificationHelper notificationHelper = new NotificationHelper(activity);
        assertEquals(0, notificationHelper.getManager().getActiveNotifications().length);
    }

    @Test
    public void sendTextTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        MainController mainController = new MainController();
        ShadowActivity shadowActivity = shadowOf(activity);
        shadowActivity.grantPermissions(Manifest.permission.SEND_SMS);
        mainController.sendText(activity, activity, "012345678910", "test");
        assertEquals("Text sent successfully", ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void openAppTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        MainController mainController = new MainController();
        mainController.openApp(activity, activity, "snapchat");
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW);
        expectedIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + "com.snapchat.android"));
        ShadowActivity shadowActivity = new ShadowActivity();
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getData(), actual.getData());
    }

    @Test
    public void openWebsiteTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        MainController mainController = new MainController();
        mainController.openWebsite(activity, "amazon");
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.co.uk/"));
        ShadowActivity shadowActivity = new ShadowActivity();
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getData(), actual.getData());
    }

    @Test
    public void searchSiteTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        MainController mainController = new MainController();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Application", "ipad");
        mainController.searchSite(activity, "amazon", hashMap);
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.co.uk/s?k=ipad"));
        ShadowActivity shadowActivity = new ShadowActivity();
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getData(), actual.getData());
    }

    @Test
    public void websiteUrlLookup() {
        MainController mainController = new MainController();
        assertEquals("https://www.amazon.co.uk/", mainController.websiteUrlLookup("amazon"));
    }

    @Test
    public void searchUrlLookup() {
        MainController mainController = new MainController();
        assertEquals("https://www.amazon.co.uk/s?k=", mainController.searchUrlLookup("amazon"));
    }

    @Test
    public void packageNameLookup() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        MainController mainController = new MainController();
        assertEquals("com.google.android.youtube", mainController.packageNameLookup(activity, "youtube"));
    }

}
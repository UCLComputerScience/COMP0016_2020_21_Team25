package com.example.fisev2concierge.controllers;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fisev2concierge.UI.MainActivity;
import com.example.fisev2concierge.R;
import com.example.fisev2concierge.UI.ViewRemindersActivity;
import com.example.fisev2concierge.helperClasses.NotificationHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

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
public class MainControllerTest {

    @Test
    public void parseAskBobResponse() {
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
        MainController mainController = new MainController();
        HashMap hashMap = mainController.askBobRequest("open facebook");
        assertTrue(hashMap.containsKey("Service_Type"));
        assertTrue(hashMap.containsKey("Application"));
    }

    @Test
    public void askBobControllerTest(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        HashMap hashMap = new HashMap();
        hashMap.put("Service_Type", "APP_SERVICE");
        hashMap.put("Service", "Open App");
        hashMap.put("Application", "snapchat");
        mainController.askBobController(hashMap, activity, activity, activity, null);
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://play.google.com/store/apps/details?id=com.snapchat.android"));
        Intent actual = shadowOf(application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void askBobServices(){
        MainController mainController = new MainController();
        ArrayList<String> response = mainController.askBobServices("query", "message=\"open snapchat\"&sender=\"concierge\"");
        assertTrue(response.size()>0);
    }



    @Test
    public void backendTest(){
        //might be worth asserting if response code is correct
        MainController mainController = new MainController();
        ArrayList<String> expectedResult = new ArrayList<>();
        expectedResult.add("{\"name\":\"\",\"category\":\"\",\"description\":\"\",\"code\":200}");
        assertEquals(expectedResult.get(0), mainController.backendServices("servicedata", "1").get(0));
    }

    @Test
    public void handleUserRequest(){
        String[] request = new String[]{"open snapchat"};
        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
        MainController mainController = new MainController();
        mainController.handleUserRequest(request, null, mainActivity, mainActivity, mainActivity, mainActivity.findViewById(R.id.conciergeStatusText));
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://play.google.com/store/apps/details?id=com.snapchat.android"));
        Intent actual = shadowOf(application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void handleUserRequestSearchYell(){
        //only run this test once onFailureListener has been added to getLatLon class
        String[] request = new String[]{"find me a plumber"};
        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
        MainController mainController = new MainController();
        mainController.handleUserRequest(request, null, mainActivity, mainActivity, mainActivity, mainActivity.findViewById(R.id.conciergeStatusText));
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.yell.com/ucs/UcsSearchAction.do?keywords=plumber&location=london"));
        Intent actual = shadowOf(application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void handleUserRequestEmptyRequest(){
        //only run this test once onFailureListener has been added to getLatLon class
        String[] request = new String[]{""};
        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
        TextView conciergeStatusText = mainActivity.findViewById(R.id.conciergeStatusText);
        MainController mainController = new MainController();
        mainController.handleUserRequest(request, null, mainActivity, mainActivity, mainActivity, conciergeStatusText);
        List<Toast> toasts = shadowOf(application).getShownToasts();
        assertEquals(toasts.toArray().length, 1);
        assertEquals(conciergeStatusText.getText(), "Concierge is off");
    }

    @Test
    public void test(){
        //Unable to assert correct output as unable to mock location
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        MainController mainController = new MainController();
        mainController.test(activity, activity, null , activity, null);
    }

    @Test
    public void getLocation() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        MainController mainController = new MainController();
        String postcode = mainController.getLocation(activity, activity, 51.501009, -0.141588);
        assertEquals("SW1A 1AA", postcode);
    }

    @Test
    public void searchContact(){
        //can only assert output if contact is not found or if contact queries backend
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        MainController mainController = new MainController();
        String number = mainController.searchContact("test", activity, activity, activity);
        assertEquals(number, "-1");
    }

    @Test
    public void makeCall() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        MainController mainController = new MainController();
        mainController.makeCall(activity, activity, "012345678910");
        Intent expectedIntent = new Intent(Intent.ACTION_CALL, Uri.parse("012345678910"));
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void openActivity() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        MainController mainController = new MainController();
        mainController.openActivity(activity, activity, "reminders");
        Intent expectedIntent = new Intent(activity, ViewRemindersActivity.class);;
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }



    @Test
    public void getUserIdTest(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        mainController.addUserID(activity, "1");
        String userID = mainController.getUserID(activity);
        assertEquals("1", userID);
    }

    @Test
    public void addUserIdTest(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        mainController.addUserID(activity, "1");
        assertTrue(mainController.hasUserID(activity));
    }

    @Test
    public void hasUserIdTest(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        mainController.hasUserID(activity);
        assertFalse(mainController.hasUserID(activity));
    }

    @Test
    public void getRemindersTest(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        Cursor cursor = mainController.getReminders(activity);
        assertFalse(cursor == null);
    }

    @Test
    public void addReminder(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        mainController.addReminder(activity, "test");
        assertTrue(mainController.getReminders(activity).getCount() == 1);
    }

    @Test
    public void updateReminder(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        mainController.addReminder(activity, "old reminder");
        mainController.updateReminder(activity, "old reminder", 1, "new reminder");
        Cursor cursor = mainController.getReminders(activity);
        cursor.moveToFirst();
        assertEquals(cursor.getString(1), "new reminder");
    }

    @Test
    public void deleteReminder(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        mainController.addReminder(activity, "reminder");
        mainController.deleteReminder(activity, 1, "reminder");
        assertEquals(mainController.getReminders(activity).getCount(), 0);
    }

    @Test
    public void getAlarmsTest(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        Cursor cursor = mainController.getAlarm(activity);
        assertFalse(cursor == null);
    }

    @Test
    public void addAlarm(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        mainController.addAlarm(activity, "test", "date");
        assertTrue(mainController.getAlarm(activity).getCount() == 1);
    }

    @Test
    public void updateAlarm(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        mainController.addAlarm(activity, "test", "date");
        mainController.updateAlarm(activity, "test", 1, "new alarm", "new date");
        Cursor cursor = mainController.getAlarm(activity);
        cursor.moveToFirst();
        assertEquals(cursor.getString(1), "new alarm");
    }

    @Test
    public void deleteAlarm(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        mainController.addAlarm(activity, "test", "date");
        mainController.deleteAlarm(activity, 1, "test");
        assertEquals(mainController.getAlarm(activity).getCount(), 0);
    }

    @Test
    public void getRecentAlarm(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        mainController.addAlarm(activity, "alarm 1", "date 1");
        mainController.addAlarm(activity, "alarm 2", "date 2");
        Cursor cursor = mainController.getRecentAlarm(activity);
        cursor.moveToFirst();
        assertEquals(cursor.getString(1), "alarm 2");
    }

    @Test
    public void startAlarm(){
        //no way to check if this is working
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 1);
        c.set(Calendar.MINUTE, 1);
        c.set(Calendar.SECOND, 1);
        c.set(Calendar.YEAR, 2021);
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        mainController.startAlarm(activity, activity, "0", c);
//        NotificationHelper notificationHelper = new NotificationHelper(activity);
//        assertEquals(notificationHelper.getManager().getActiveNotifications().length, 1);
    }

    @Test
    public void stopAlarm(){
        MainController mainController = new MainController();
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 1);
        c.set(Calendar.MINUTE, 1);
        c.set(Calendar.SECOND, 1);
        c.set(Calendar.YEAR, 2021);
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        mainController.startAlarm(activity, activity, "0", c);
        mainController.stopAlarm(activity, activity, 0);
        NotificationHelper notificationHelper = new NotificationHelper(activity);
        assertEquals(notificationHelper.getManager().getActiveNotifications().length, 0);
    }

    @Test
    public void sendText(){
        //not too sure what's going on here but don't think we can test this
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        MainController mainController = new MainController();
        mainController.sendText(activity, activity, "012345678910", "test");
        List<Toast> toasts = shadowOf(application).getShownToasts();
        assertEquals(toasts.toArray().length, 1);
    }

    @Test
    public void openApp() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        MainController mainController = new MainController();
        mainController.openApp(activity, activity, "snapchat");
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW);
        expectedIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + "com.snapchat.android"));
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void openWebsite(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        MainController mainController = new MainController();
        mainController.openWebsite(activity, "amazon");
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.co.uk/"));
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void searchSite() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        MainController mainController = new MainController();
        HashMap hashMap = new HashMap();
        hashMap.put("Application", "ipad");
        mainController.searchSite(activity, "amazon", hashMap);
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.co.uk/s?k=ipad"));
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
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
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        MainController mainController = new MainController();
        assertEquals("com.google.android.youtube", mainController.packageNameLookup(activity, "youtube"));
    }

}
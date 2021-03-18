package com.example.fisev2concierge.functionalityClasses;

import android.database.Cursor;

import com.example.fisev2concierge.UI.MainActivity;
import com.example.fisev2concierge.helperClasses.NotificationHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.Calendar;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class AlarmsFunctionalityTest {

    @Test
    public void getAlarmsTest(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AlarmsFunctionality alarmsFunctionality = new AlarmsFunctionality(activity);
        Cursor cursor = alarmsFunctionality.getAlarms();
        assertFalse(cursor == null);
    }

    @Test
    public void addAlarm(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AlarmsFunctionality alarmsFunctionality = new AlarmsFunctionality(activity);
        alarmsFunctionality.addAlarm("test", "date");
        assertTrue(alarmsFunctionality.getAlarms().getCount() == 1);
    }

    @Test
    public void updateAlarm(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AlarmsFunctionality alarmsFunctionality = new AlarmsFunctionality(activity);
        alarmsFunctionality.addAlarm("test", "date");
        alarmsFunctionality.updateAlarm("test", 1, "new alarm", "new date");
        Cursor cursor = alarmsFunctionality.getAlarms();
        cursor.moveToFirst();
        assertEquals(cursor.getString(1), "new alarm");
    }

    @Test
    public void deleteAlarm(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AlarmsFunctionality alarmsFunctionality = new AlarmsFunctionality(activity);
        alarmsFunctionality.addAlarm("test", "date");
        alarmsFunctionality.deleteAlarm(1, "test");
        assertEquals(alarmsFunctionality.getAlarms().getCount(), 0);
    }

    @Test
    public void getRecentAlarm(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AlarmsFunctionality alarmsFunctionality = new AlarmsFunctionality(activity);
        alarmsFunctionality.addAlarm("alarm 1", "date 1");
        alarmsFunctionality.addAlarm("alarm 2", "date 2");
        Cursor cursor = alarmsFunctionality.getRecent();
        cursor.moveToFirst();
        assertEquals(cursor.getString(1), "alarm 2");
    }

    @Test
    public void startAlarm(){
        //no way to check if this is working
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AlarmsFunctionality alarmsFunctionality = new AlarmsFunctionality(activity);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 1);
        c.set(Calendar.MINUTE, 1);
        c.set(Calendar.SECOND, 1);
        c.set(Calendar.YEAR, 2021);
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        alarmsFunctionality.startAlarm(activity, activity, "0", c);
//        NotificationHelper notificationHelper = new NotificationHelper(activity);
//        assertEquals(notificationHelper.getManager().getActiveNotifications().length, 1);
    }

    @Test
    public void stopAlarm(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AlarmsFunctionality alarmsFunctionality = new AlarmsFunctionality(activity);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 1);
        c.set(Calendar.MINUTE, 1);
        c.set(Calendar.SECOND, 1);
        c.set(Calendar.YEAR, 2021);
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        alarmsFunctionality.startAlarm(activity, activity, "0", c);
        alarmsFunctionality.stopAlarm(activity, activity, 0);
        NotificationHelper notificationHelper = new NotificationHelper(activity);
        assertEquals(notificationHelper.getManager().getActiveNotifications().length, 0);
    }

}
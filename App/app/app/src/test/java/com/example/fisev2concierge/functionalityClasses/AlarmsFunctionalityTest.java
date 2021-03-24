package com.example.fisev2concierge.functionalityClasses;

import android.database.Cursor;

import com.example.fisev2concierge.views.MainActivity;
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
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        AlarmsFunctionality alarmsFunctionality = new AlarmsFunctionality(activity);
        Cursor cursor = alarmsFunctionality.getAlarms();
        assertNotNull(cursor);
    }

    @Test
    public void addAlarmTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        AlarmsFunctionality alarmsFunctionality = new AlarmsFunctionality(activity);
        alarmsFunctionality.addAlarm("test", "date");
        assertEquals(1, alarmsFunctionality.getAlarms().getCount());
    }

    @Test
    public void updateAlarmTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        AlarmsFunctionality alarmsFunctionality = new AlarmsFunctionality(activity);
        alarmsFunctionality.addAlarm("test", "date");
        alarmsFunctionality.updateAlarm(1, "new alarm", "new date");
        Cursor cursor = alarmsFunctionality.getAlarms();
        cursor.moveToFirst();
        assertEquals("new alarm", cursor.getString(1));
    }

    @Test
    public void deleteAlarmTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        AlarmsFunctionality alarmsFunctionality = new AlarmsFunctionality(activity);
        alarmsFunctionality.addAlarm("test", "date");
        alarmsFunctionality.deleteAlarm(1);
        assertEquals(0, alarmsFunctionality.getAlarms().getCount());
    }

    @Test
    public void getRecentAlarmTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        AlarmsFunctionality alarmsFunctionality = new AlarmsFunctionality(activity);
        alarmsFunctionality.addAlarm("alarm 1", "date 1");
        alarmsFunctionality.addAlarm("alarm 2", "date 2");
        Cursor cursor = alarmsFunctionality.getRecent();
        cursor.moveToFirst();
        assertEquals("alarm 2", cursor.getString(1));
    }

    @Test
    public void startAlarmTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        AlarmsFunctionality alarmsFunctionality = new AlarmsFunctionality(activity);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 1);
        c.set(Calendar.MINUTE, 1);
        c.set(Calendar.SECOND, 1);
        c.set(Calendar.YEAR, 2021);
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        alarmsFunctionality.startAlarm(activity, activity, "0", c, "message");
    }

    @Test
    public void stopAlarmTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        AlarmsFunctionality alarmsFunctionality = new AlarmsFunctionality(activity);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 1);
        c.set(Calendar.MINUTE, 1);
        c.set(Calendar.SECOND, 1);
        c.set(Calendar.YEAR, 2021);
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        alarmsFunctionality.startAlarm(activity, activity, "0", c, "message");
        alarmsFunctionality.stopAlarm(activity, activity, 0);
        NotificationHelper notificationHelper = new NotificationHelper(activity);
        assertEquals(0, notificationHelper.getManager().getActiveNotifications().length);
    }

}
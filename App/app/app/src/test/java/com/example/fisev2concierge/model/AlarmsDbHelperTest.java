package com.example.fisev2concierge.model;

import android.database.Cursor;

import com.example.fisev2concierge.UI.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class AlarmsDbHelperTest {

    @Test
    public void getDataTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        AlarmsDbHelper alarmsDbHelper = new AlarmsDbHelper(activity);
        Cursor cursor = alarmsDbHelper.getData();
        assertNotNull(cursor);
    }

    @Test
    public void addAlarmTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        AlarmsDbHelper alarmsDbHelper = new AlarmsDbHelper(activity);
        alarmsDbHelper.addData("test", "date");
        assertEquals(1, alarmsDbHelper.getData().getCount());
    }

    @Test
    public void updateAlarmTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        AlarmsDbHelper alarmsDbHelper = new AlarmsDbHelper(activity);
        alarmsDbHelper.addData("test", "date");
        alarmsDbHelper.updateAlarm("test", 1, "new alarm", "new date");
        Cursor cursor = alarmsDbHelper.getData();
        cursor.moveToFirst();
        assertEquals("new alarm", cursor.getString(1));
    }

    @Test
    public void deleteAlarmTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        AlarmsDbHelper alarmsDbHelper = new AlarmsDbHelper(activity);
        alarmsDbHelper.addData("test", "date");
        alarmsDbHelper.deleteAlarm(1, "test");
        assertEquals(0, alarmsDbHelper.getData().getCount());
    }

    @Test
    public void getRecentAlarmTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        AlarmsDbHelper alarmsDbHelper = new AlarmsDbHelper(activity);
        alarmsDbHelper.addData("alarm 1", "date 1");
        alarmsDbHelper.addData("alarm 2", "date 2");
        Cursor cursor = alarmsDbHelper.getRecent();
        cursor.moveToFirst();
        assertEquals("alarm 2", cursor.getString(1));
    }

    @Test
    public void onUpgradeTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        AlarmsDbHelper alarmsDbHelper = new AlarmsDbHelper(activity);
        alarmsDbHelper.onUpgrade(alarmsDbHelper.getWritableDatabase(), 2, 3);
    }
}
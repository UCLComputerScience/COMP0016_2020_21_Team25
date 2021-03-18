package com.example.fisev2concierge.model;

import android.database.Cursor;

import com.example.fisev2concierge.UI.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class AlarmsDbHelperTest {

    @Test
    public void getDataTest(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AlarmsDbHelper alarmsDbHelper = new AlarmsDbHelper(activity);
        Cursor cursor = alarmsDbHelper.getData();
        assertFalse(cursor == null);
    }

    @Test
    public void addAlarm(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AlarmsDbHelper alarmsDbHelper = new AlarmsDbHelper(activity);
        alarmsDbHelper.addData("test", "date");
        assertTrue(alarmsDbHelper.getData().getCount() == 1);
    }

    @Test
    public void updateAlarm(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AlarmsDbHelper alarmsDbHelper = new AlarmsDbHelper(activity);
        alarmsDbHelper.addData("test", "date");
        alarmsDbHelper.updateAlarm("test", 1, "new alarm", "new date");
        Cursor cursor = alarmsDbHelper.getData();
        cursor.moveToFirst();
        assertEquals(cursor.getString(1), "new alarm");
    }

    @Test
    public void deleteAlarm(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AlarmsDbHelper alarmsDbHelper = new AlarmsDbHelper(activity);
        alarmsDbHelper.addData("test", "date");
        alarmsDbHelper.deleteAlarm(1, "test");
        assertEquals(alarmsDbHelper.getData().getCount(), 0);
    }

    @Test
    public void getRecentAlarm(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AlarmsDbHelper alarmsDbHelper = new AlarmsDbHelper(activity);
        alarmsDbHelper.addData("alarm 1", "date 1");
        alarmsDbHelper.addData("alarm 2", "date 2");
        Cursor cursor = alarmsDbHelper.getRecent();
        cursor.moveToFirst();
        assertEquals(cursor.getString(1), "alarm 2");
    }

    @Test
    public void onUpgrade(){
        //no way to test this
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AlarmsDbHelper alarmsDbHelper = new AlarmsDbHelper(activity);
        alarmsDbHelper.onUpgrade(alarmsDbHelper.getWritableDatabase(), 2, 3);
    }
}
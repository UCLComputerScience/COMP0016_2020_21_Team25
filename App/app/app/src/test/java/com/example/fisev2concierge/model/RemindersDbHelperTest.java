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
public class RemindersDbHelperTest {

    @Test
    public void getDataTest(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        RemindersDbHelper remindersDbHelper = new RemindersDbHelper(activity);
        Cursor cursor = remindersDbHelper.getData();
        assertFalse(cursor == null);
    }

    @Test
    public void addAlarm(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        RemindersDbHelper remindersDbHelper = new RemindersDbHelper(activity);
        remindersDbHelper.addData("test");
        assertTrue(remindersDbHelper.getData().getCount() == 1);
    }

    @Test
    public void updateAlarm(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        RemindersDbHelper remindersDbHelper = new RemindersDbHelper(activity);
        remindersDbHelper.addData("test");
        remindersDbHelper.updateReminder("test", 1, "new alarm");
        Cursor cursor = remindersDbHelper.getData();
        cursor.moveToFirst();
        assertEquals(cursor.getString(1), "new alarm");
    }

    @Test
    public void deleteAlarm(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        RemindersDbHelper remindersDbHelper = new RemindersDbHelper(activity);
        remindersDbHelper.addData("test");
        remindersDbHelper.deleteReminder(1, "test");
        assertEquals(remindersDbHelper.getData().getCount(), 0);
    }

    @Test
    public void onUpgrade(){
        //no way to test this
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        RemindersDbHelper remindersDbHelper = new RemindersDbHelper(activity);
        remindersDbHelper.onUpgrade(remindersDbHelper.getWritableDatabase(), 2, 3);
    }
    
}
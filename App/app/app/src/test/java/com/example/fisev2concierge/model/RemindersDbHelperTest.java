package com.example.fisev2concierge.model;

import android.database.Cursor;

import com.example.fisev2concierge.views.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class RemindersDbHelperTest {

    @Test
    public void getDataTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        RemindersDbHelper remindersDbHelper = new RemindersDbHelper(activity);
        Cursor cursor = remindersDbHelper.getData();
        assertNotNull(cursor);
    }

    @Test
    public void addReminderTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        RemindersDbHelper remindersDbHelper = new RemindersDbHelper(activity);
        remindersDbHelper.addData("test");
        assertEquals(1, remindersDbHelper.getData().getCount());
    }

    @Test
    public void updateReminderTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        RemindersDbHelper remindersDbHelper = new RemindersDbHelper(activity);
        remindersDbHelper.addData("test");
        remindersDbHelper.updateReminder(1, "new alarm");
        Cursor cursor = remindersDbHelper.getData();
        cursor.moveToFirst();
        assertEquals("new alarm", cursor.getString(1));
    }

    @Test
    public void deleteReminderTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        RemindersDbHelper remindersDbHelper = new RemindersDbHelper(activity);
        remindersDbHelper.addData("test");
        remindersDbHelper.deleteReminder(1);
        assertEquals(0, remindersDbHelper.getData().getCount());
    }

    @Test
    public void onUpgradeTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        RemindersDbHelper remindersDbHelper = new RemindersDbHelper(activity);
        remindersDbHelper.onUpgrade(remindersDbHelper.getWritableDatabase(), 2, 3);
    }
    
}
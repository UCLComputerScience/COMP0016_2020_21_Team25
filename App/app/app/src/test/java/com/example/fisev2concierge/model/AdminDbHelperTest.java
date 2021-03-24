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
public class AdminDbHelperTest {

    @Test
    public void addUserIdIfItAlreadyExists(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        AdminDbHelper adminDbHelper = new AdminDbHelper(activity);
        adminDbHelper.addID("0");
        adminDbHelper.addID("1");
        Cursor cursor = adminDbHelper.getData();
        cursor.moveToFirst();
        String userID = cursor.getString(1);
        assertEquals(1, adminDbHelper.getData().getCount());
        assertEquals("1", userID);
    }

    @Test
    public void getIdOfUserIdTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        AdminDbHelper adminDbHelper = new AdminDbHelper(activity);
        adminDbHelper.addID("0");
        assertEquals("1", adminDbHelper.getIdOfUserId());
    }

    @Test
    public void getDataTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        AdminDbHelper adminDbHelper = new AdminDbHelper(activity);
        Cursor cursor = adminDbHelper.getData();
        assertNotNull(cursor);
    }

    @Test
    public void addUserIdTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        AdminDbHelper adminDbHelper = new AdminDbHelper(activity);
        adminDbHelper.addID("99");
        Cursor cursor = adminDbHelper.getData();
        cursor.moveToFirst();
        String userID = cursor.getString(1);
        assertEquals(1, adminDbHelper.getData().getCount());
        assertEquals("99", userID);
    }

    @Test
    public void updateUserIDTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        AdminDbHelper adminDbHelper = new AdminDbHelper(activity);
        adminDbHelper.addID("99");
        adminDbHelper.updateID("1", "100");
        Cursor cursor = adminDbHelper.getData();
        cursor.moveToFirst();
        assertEquals("100", cursor.getString(1));
    }

    @Test
    public void onUpgradeTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        AdminDbHelper adminDbHelper = new AdminDbHelper(activity);
        adminDbHelper.onUpgrade(adminDbHelper.getWritableDatabase(), 1, 2);
    }
}
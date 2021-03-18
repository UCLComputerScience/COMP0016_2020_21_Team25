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
public class AdminDbHelperTest {

    @Test
    public void getDataTest(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AdminDbHelper adminDbHelper = new AdminDbHelper(activity);
        Cursor cursor = adminDbHelper.getData();
        assertFalse(cursor == null);
    }

    @Test
    public void addUserID(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AdminDbHelper adminDbHelper = new AdminDbHelper(activity);
        adminDbHelper.addID("99");
        Cursor cursor = adminDbHelper.getData();
        cursor.moveToFirst();
        String userID = cursor.getString(1);
        assertTrue(adminDbHelper.getData().getCount() == 1);
        assertEquals("99", userID);
    }

    @Test
    public void updateUserID(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AdminDbHelper adminDbHelper = new AdminDbHelper(activity);
        adminDbHelper.addID("99");
        adminDbHelper.updateID("1", "100");
        Cursor cursor = adminDbHelper.getData();
        cursor.moveToFirst();
        assertEquals(cursor.getString(1), "100");
    }

    @Test
    public void onUpgrade(){
        //no way to test this
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AdminDbHelper adminDbHelper = new AdminDbHelper(activity);
        adminDbHelper.onUpgrade(adminDbHelper.getWritableDatabase(), 1, 2);
        assertEquals(2, adminDbHelper.getWritableDatabase().getVersion());
    }
}
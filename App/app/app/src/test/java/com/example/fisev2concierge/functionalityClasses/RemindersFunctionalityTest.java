package com.example.fisev2concierge.functionalityClasses;

import android.database.Cursor;

import com.example.fisev2concierge.UI.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class RemindersFunctionalityTest {

    @Test
    public void getRemindersTest(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        RemindersFunctionality remindersFunctionality = new RemindersFunctionality(activity);
        Cursor cursor = remindersFunctionality.getReminders();
        assertFalse(cursor == null);
    }

    @Test
    public void addReminder(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        RemindersFunctionality remindersFunctionality = new RemindersFunctionality(activity);
        remindersFunctionality.addReminder("test");
        assertTrue(remindersFunctionality.getReminders().getCount() == 1);
    }

    @Test
    public void updateReminder(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        RemindersFunctionality remindersFunctionality = new RemindersFunctionality(activity);
        remindersFunctionality.addReminder("old reminder");
        remindersFunctionality.updateReminder("old reminder", 1, "new reminder");
        Cursor cursor = remindersFunctionality.getReminders();
        cursor.moveToFirst();
        assertEquals(cursor.getString(1), "new reminder");
    }

    @Test
    public void deleteReminder(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        RemindersFunctionality remindersFunctionality = new RemindersFunctionality(activity);
        remindersFunctionality.addReminder("reminder");
        remindersFunctionality.deleteReminder(1, "reminder");
        assertEquals(remindersFunctionality.getReminders().getCount(), 0);
    }

}
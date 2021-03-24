package com.example.fisev2concierge.functionalityClasses;

import android.database.Cursor;

import com.example.fisev2concierge.views.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class RemindersFunctionalityTest {

    @Test
    public void getRemindersTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        RemindersFunctionality remindersFunctionality = new RemindersFunctionality(activity);
        Cursor cursor = remindersFunctionality.getReminders();
        assertNotNull(cursor);
    }

    @Test
    public void addReminderTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        RemindersFunctionality remindersFunctionality = new RemindersFunctionality(activity);
        remindersFunctionality.addReminder("test");
        assertEquals(1, remindersFunctionality.getReminders().getCount());
    }

    @Test
    public void updateReminderTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        RemindersFunctionality remindersFunctionality = new RemindersFunctionality(activity);
        remindersFunctionality.addReminder("old reminder");
        remindersFunctionality.updateReminder(1, "new reminder");
        Cursor cursor = remindersFunctionality.getReminders();
        cursor.moveToFirst();
        assertEquals("new reminder", cursor.getString(1));
    }

    @Test
    public void deleteReminderTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        RemindersFunctionality remindersFunctionality = new RemindersFunctionality(activity);
        remindersFunctionality.addReminder("reminder");
        remindersFunctionality.deleteReminder(1);
        assertEquals(0, remindersFunctionality.getReminders().getCount());
    }

}
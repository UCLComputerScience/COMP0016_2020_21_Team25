package com.example.fisev2concierge.UI;

import android.content.Intent;
import android.widget.EditText;

import com.example.fisev2concierge.R;
import com.example.fisev2concierge.model.RemindersDbHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class AddReminderActivityTest {

    @Test
    public void backButtonTest() {
        AddReminderActivity activity = Robolectric.buildActivity(AddReminderActivity.class).create().get();
        activity.findViewById(R.id.backButtonAddReminder).performClick();
        Intent expectedIntent = new Intent(activity, ViewRemindersActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void addReminderButtonTest() {
        AddReminderActivity activity = Robolectric.buildActivity(AddReminderActivity.class).create().get();
        EditText editText = activity.findViewById(R.id.reminderText);
        editText.setText("new reminder");
        activity.findViewById(R.id.addReminderButton).performClick();
        RemindersDbHelper remindersDbHelper = new RemindersDbHelper(activity);
        Intent expectedIntent = new Intent(activity, ViewRemindersActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        assertEquals(1, remindersDbHelper.getData().getCount());
    }

}
package com.example.fisev2concierge.UI;

import android.content.Intent;
import android.widget.EditText;

import com.example.fisev2concierge.R;
import com.example.fisev2concierge.model.RemindersDbHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class AddReminderActivityTest {
    //Adding tests for buttons on ViewRemindersActivity

    @Test
    public void backButton() {
        AddReminderActivity activity = Robolectric.setupActivity(AddReminderActivity.class);
        activity.findViewById(R.id.backButtonAddReminder).performClick();
        Intent expectedIntent = new Intent(activity, ViewRemindersActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void addReminderButton() {
        AddReminderActivity activity = Robolectric.setupActivity(AddReminderActivity.class);
        EditText editText = activity.findViewById(R.id.reminderText);
        editText.setText("new reminder");
        activity.findViewById(R.id.addReminderButton).performClick();
        RemindersDbHelper remindersDbHelper = new RemindersDbHelper(activity);
        Intent expectedIntent = new Intent(activity, ViewRemindersActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        assertEquals(1, remindersDbHelper.getData().getCount());
    }

}
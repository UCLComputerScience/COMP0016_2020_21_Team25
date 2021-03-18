package com.example.fisev2concierge.UI;

import android.content.Intent;

import com.example.fisev2concierge.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class EditReminderActivityTest {

    @Test
    public void backButton() {
        EditReminderActivity activity = Robolectric.setupActivity(EditReminderActivity.class);
        activity.findViewById(R.id.backButton).performClick();
        Intent expectedIntent = new Intent(activity, ViewRemindersActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void saveReminderButton() {
        Intent intent = new Intent(Robolectric.setupActivity(ViewRemindersActivity.class), ViewRemindersActivity.class);
        intent.putExtra("ID", 0);
        intent.putExtra("Reminder", "new reminder");
        EditReminderActivity activity = Robolectric.buildActivity(EditReminderActivity.class, intent).create().start().resume().get();
        activity.findViewById(R.id.saveReminderButton).performClick();
        Intent expectedIntent = new Intent(activity, ViewRemindersActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        //intent is only launched if value of remindersText is not empty
    }

    @Test
    public void deleteReminderButton() {
        EditReminderActivity activity = Robolectric.setupActivity(EditReminderActivity.class);
        activity.findViewById(R.id.deleteReminderButton).performClick();
        Intent expectedIntent = new Intent(activity, ViewRemindersActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}
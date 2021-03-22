package com.example.fisev2concierge.UI;

import android.content.Intent;

import com.example.fisev2concierge.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class EditReminderActivityTest {

    @Test
    public void backButtonTest() {
        EditReminderActivity activity = Robolectric.buildActivity(EditReminderActivity.class).create().get();
        activity.findViewById(R.id.backButtonEditReminder).performClick();
        Intent expectedIntent = new Intent(activity, ViewRemindersActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void saveReminderButtonTest() {
        Intent intent = new Intent(Robolectric.buildActivity(ViewRemindersActivity.class).create().get(), ViewRemindersActivity.class);
        intent.putExtra("ID", 0);
        intent.putExtra("Reminder", "new reminder");
        EditReminderActivity activity = Robolectric.buildActivity(EditReminderActivity.class, intent).create().start().resume().get();
        activity.findViewById(R.id.saveReminderButton).performClick();
        Intent expectedIntent = new Intent(activity, ViewRemindersActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void deleteReminderButtonTest() {
        EditReminderActivity activity = Robolectric.buildActivity(EditReminderActivity.class).create().get();
        activity.findViewById(R.id.deleteReminderButton).performClick();
        Intent expectedIntent = new Intent(activity, ViewRemindersActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}
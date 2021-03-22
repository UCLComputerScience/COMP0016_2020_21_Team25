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
public class MainActivityTest {

    @Test
    public void clickingAbout_shouldStartAboutActivityTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();
        activity.findViewById(R.id.about_activity_button).performClick();
        Intent expectedIntent = new Intent(activity, AboutActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickingRegister_shouldStartRegisterActivityTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();
        activity.findViewById(R.id.activity_register_button).performClick();
        Intent expectedIntent = new Intent(activity, RegisterActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickingInstructions_shouldStartInstructionsActivityTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();
        activity.findViewById(R.id.instructions_view_button).performClick();

        Intent expectedIntent = new Intent(activity, InstructionActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickingAlarms_shouldStartAlarmsActivityTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();
        activity.findViewById(R.id.alarms_view_button).performClick();

        Intent expectedIntent = new Intent(activity, ViewAlarmsActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickingReminders_shouldStartRemindersActivityTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();
        activity.findViewById(R.id.reminders_view_button).performClick();

        Intent expectedIntent = new Intent(activity, ViewRemindersActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickingTimers_shouldStartTimersActivityTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();
        activity.findViewById(R.id.timers_view_button).performClick();

        Intent expectedIntent = new Intent(activity, TimersActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickingHistory_shouldStartHistoryActivity() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();
        activity.findViewById(R.id.history_view_button).performClick();
        Intent expectedIntent = new Intent(activity, HistoryActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}
package com.example.fisev2concierge.functionalityClasses;

import android.content.Intent;

import com.example.fisev2concierge.UI.InstructionActivity;
import com.example.fisev2concierge.UI.HistoryActivity;
import com.example.fisev2concierge.UI.MainActivity;
import com.example.fisev2concierge.UI.TimersActivity;
import com.example.fisev2concierge.UI.ViewAlarmsActivity;
import com.example.fisev2concierge.UI.ViewRemindersActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class OpenActivityTest {
    //Adding tests for buttons on MainActivity

    @Test
    public void openReminders() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        OpenActivity openActivity = new OpenActivity(activity, activity);
        openActivity.openActivity("reminders");
        Intent expectedIntent = new Intent(activity, ViewRemindersActivity.class);;
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void openAlarms() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        OpenActivity openActivity = new OpenActivity(activity, activity);
        openActivity.openActivity("alarms");
        Intent expectedIntent = new Intent(activity, ViewAlarmsActivity.class);;
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void openTimers() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        OpenActivity openActivity = new OpenActivity(activity, activity);
        openActivity.openActivity("timers");
        Intent expectedIntent = new Intent(activity, TimersActivity.class);;
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void openInstructions() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        OpenActivity openActivity = new OpenActivity(activity, activity);
        openActivity.openActivity("instructions");
        Intent expectedIntent = new Intent(activity, InstructionActivity.class);;
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void openHistory() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        OpenActivity openActivity = new OpenActivity(activity, activity);
        openActivity.openActivity("history");
        Intent expectedIntent = new Intent(activity, HistoryActivity.class);;
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}
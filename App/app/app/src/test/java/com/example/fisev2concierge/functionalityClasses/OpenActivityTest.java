package com.example.fisev2concierge.functionalityClasses;

import android.content.Intent;

import com.example.fisev2concierge.views.HistoryActivity;
import com.example.fisev2concierge.views.InstructionActivity;
import com.example.fisev2concierge.views.MainActivity;
import com.example.fisev2concierge.views.TimersActivity;
import com.example.fisev2concierge.views.ViewAlarmsActivity;
import com.example.fisev2concierge.views.ViewRemindersActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class OpenActivityTest {

    @Test
    public void openRemindersTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        OpenActivity openActivity = new OpenActivity(activity, activity);
        openActivity.openActivity("reminders");
        Intent expectedIntent = new Intent(activity, ViewRemindersActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void openAlarmsTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        OpenActivity openActivity = new OpenActivity(activity, activity);
        openActivity.openActivity("alarms");
        Intent expectedIntent = new Intent(activity, ViewAlarmsActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void openTimersTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        OpenActivity openActivity = new OpenActivity(activity, activity);
        openActivity.openActivity("timers");
        Intent expectedIntent = new Intent(activity, TimersActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void openInstructionsTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        OpenActivity openActivity = new OpenActivity(activity, activity);
        openActivity.openActivity("instructions");
        Intent expectedIntent = new Intent(activity, InstructionActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void openHistoryTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        OpenActivity openActivity = new OpenActivity(activity, activity);
        openActivity.openActivity("history");
        Intent expectedIntent = new Intent(activity, HistoryActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void defaultTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        OpenActivity openActivity = new OpenActivity(activity, activity);
        openActivity.openActivity("random");
        Intent expectedIntent = new Intent(activity, MainActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}
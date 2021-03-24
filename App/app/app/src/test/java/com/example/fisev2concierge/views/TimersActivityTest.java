package com.example.fisev2concierge.views;

import android.content.Intent;
import android.widget.NumberPicker;

import com.example.fisev2concierge.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class TimersActivityTest {

    @Test
    public void backButtonTest() {
        TimersActivity activity = Robolectric.buildActivity(TimersActivity.class).create().get();
        activity.findViewById(R.id.backButtonTimers).performClick();
        Intent expectedIntent = new Intent(activity, MainActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void startTimerButtonTest() {
        TimersActivity activity = Robolectric.buildActivity(TimersActivity.class).create().get();
        NumberPicker hourPicker = activity.findViewById(R.id.hoursNumberPicker);
        hourPicker.setValue(1);
        activity.findViewById(R.id.startTimerButton).performClick();
    }

    @Test
    public void pauseTimerButtonTest() {
        TimersActivity activity = Robolectric.buildActivity(TimersActivity.class).create().get();
        NumberPicker hourPicker = activity.findViewById(R.id.hoursNumberPicker);
        hourPicker.setValue(1);
        activity.findViewById(R.id.startTimerButton).performClick();
        activity.findViewById(R.id.startTimerButton).performClick();
    }
}
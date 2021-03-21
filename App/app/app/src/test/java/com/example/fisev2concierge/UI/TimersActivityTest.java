package com.example.fisev2concierge.UI;

import android.content.Intent;
import android.widget.NumberPicker;

import com.example.fisev2concierge.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class TimersActivityTest {
    //Adding tests for buttons on ViewRemindersActivity

    @Test
    public void clickingBackButton_shouldStartMainActivity() {
        TimersActivity activity = Robolectric.setupActivity(TimersActivity.class);
        activity.findViewById(R.id.backButtonTimers).performClick();
        Intent expectedIntent = new Intent(activity, MainActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void startTimer() {
        //unable to check if correct output is produced
        TimersActivity activity = Robolectric.setupActivity(TimersActivity.class);
        NumberPicker hourPicker = activity.findViewById(R.id.hoursNumberPicker);
        hourPicker.setValue(1);
        activity.findViewById(R.id.startTimerButton).performClick();
        activity.findViewById(R.id.startTimerButton).performClick();
    }


}
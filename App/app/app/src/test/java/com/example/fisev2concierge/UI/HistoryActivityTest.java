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
public class HistoryActivityTest {
    //Adding tests for buttons on MainActivity

    @Test
    public void backButton() {
        HistoryActivity activity = Robolectric.setupActivity(HistoryActivity.class);
        activity.findViewById(R.id.backButton).performClick();
        Intent expectedIntent = new Intent(activity, MainActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

}
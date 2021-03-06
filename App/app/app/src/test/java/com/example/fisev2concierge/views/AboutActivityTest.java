package com.example.fisev2concierge.views;

import android.content.Intent;

import com.example.fisev2concierge.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class AboutActivityTest {

    @Test
    public void backButtonTest() {
        AboutActivity activity = Robolectric.buildActivity(AboutActivity.class).create().get();
        activity.findViewById(R.id.backButtonAbout).performClick();
        Intent expectedIntent = new Intent(activity, MainActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

}
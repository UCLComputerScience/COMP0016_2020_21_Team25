package com.example.fisev2concierge.helperClasses;

import android.os.Bundle;

import com.example.fisev2concierge.UI.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class DatePickerFragmentTest {
    //Adding tests for buttons on MainActivity

    @Test
    public void makingCall() {
//        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        ShadowActivity shadowActivity = shadowOf(activity);
        DatePickerFragment timePicker = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("activity", activity.toString());
        timePicker.onCreateDialog(bundle);
//        ShadowTimePickerDialog shadowTimePickerDialog = shadowOf(timePicker);
    }

}
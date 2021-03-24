package com.example.fisev2concierge.helperClasses;

import android.os.Bundle;

import com.example.fisev2concierge.views.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class TimePickerFragmentTest {

    @Test
    public void timePickerFragmentTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        TimePickerFragment timePicker = new TimePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("activity", String.valueOf(activity));
        timePicker.onCreateDialog(bundle);
    }

}
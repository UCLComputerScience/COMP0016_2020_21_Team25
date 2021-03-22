package com.example.fisev2concierge.helperClasses;

import android.os.Bundle;

import com.example.fisev2concierge.UI.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class DatePickerFragmentTest {

    @Test
    public void datePickerTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        DatePickerFragment timePicker = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("activity", activity.getClass().toString());
        timePicker.onCreateDialog(bundle);
    }

}
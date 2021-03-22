package com.example.fisev2concierge.helperClasses;

import com.example.fisev2concierge.UI.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class NumberPickerFragmentTest {

    @Test
    public void getNumberPickerFragmentTest(){
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).get();
        NumberPickerFragment numberPickerFragment = new NumberPickerFragment(mainActivity);
        assertNotNull(numberPickerFragment);
    }
}
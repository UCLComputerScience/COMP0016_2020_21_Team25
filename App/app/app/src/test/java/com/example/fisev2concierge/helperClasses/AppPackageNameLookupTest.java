package com.example.fisev2concierge.helperClasses;

import com.example.fisev2concierge.views.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class AppPackageNameLookupTest {

    @Test
    public void searchTest() {
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).get();
        AppPackageNameLookup appPackageNameLookup = new AppPackageNameLookup(mainActivity);
        assertEquals("com.google.android.youtube", appPackageNameLookup.search("youtube"));
    }

}
package com.example.fisev2concierge.helperClasses;

import com.example.fisev2concierge.UI.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class AppPackageNameLookupTest {
    //Adding tests for buttons on MainActivity

    @Test
    public void search() {
        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
        AppPackageNameLookup appPackageNameLookup = new AppPackageNameLookup(mainActivity);
        assertEquals("com.google.android.youtube", appPackageNameLookup.search("youtube"));
    }

}
package com.example.fisev2concierge.helperClasses;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class WebsiteUrlLookupTest {
    //Adding tests for buttons on MainActivity

    @Test
    public void search() {
        WebsiteUrlLookup websiteUrlLookup = new WebsiteUrlLookup();
        assertEquals("https://www.amazon.co.uk/", websiteUrlLookup.search("amazon"));
        assertEquals("https://www.yell.com/", websiteUrlLookup.search("yell"));
    }

}
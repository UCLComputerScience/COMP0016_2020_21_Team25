package com.example.fisev2concierge.helperClasses;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class SearchUrlLookupTest {

    @Test
    public void search() {
        SearchUrlLookup searchUrlLookup = new SearchUrlLookup();
        assertEquals("https://www.amazon.co.uk/s?k=", searchUrlLookup.search("amazon"));
        assertEquals("https://www.yell.com/ucs/UcsSearchAction.do?keywords={keywords}&location={location}", searchUrlLookup.search("yell"));
        assertEquals("https://play.google.com/store/search?q=", searchUrlLookup.search("playstoreSearchApp"));
        assertEquals("https://play.google.com/store/apps/details?id=", searchUrlLookup.search("playstoreOpenApp"));
        assertEquals("https://www.google.com/search?q=", searchUrlLookup.search("google"));
    }

}
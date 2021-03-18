package com.example.fisev2concierge.functionalityClasses;

import android.Manifest;

import com.example.fisev2concierge.UI.MainActivity;
import com.example.fisev2concierge.controllers.MainController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class SearchContactsTest {
    //Adding tests for buttons on MainActivity

    @Test
    public void getContactNotFoundCase() {
//        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        ShadowActivity shadowActivity = shadowOf(activity);
        SearchContacts searchContacts = new SearchContacts(activity, activity, activity);
        searchContacts.searchContacts("Bob");
        shadowActivity.grantPermissions(Manifest.permission.READ_CONTACTS);
        String contact = searchContacts.searchContacts("Bob");
        assertTrue(contact.equals("-1"));
    }

    @Test
    public void queryContactBackend() {
//        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        ShadowActivity shadowActivity = shadowOf(activity);
        shadowActivity.grantPermissions(Manifest.permission.READ_CONTACTS);
        SearchContacts searchContacts = new SearchContacts(activity, activity, activity);
        searchContacts.searchContacts("Gp");
        MainController mainController = new MainController();
        mainController.addUserID(activity, "101");
        String number = searchContacts.searchContacts("Gp");
        assertEquals("11111111112", number);
    }

}
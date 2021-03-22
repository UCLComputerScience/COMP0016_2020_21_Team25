package com.example.fisev2concierge.functionalityClasses;

import android.Manifest;

import com.example.fisev2concierge.UI.MainActivity;
import com.example.fisev2concierge.controllers.MainController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowToast;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class SearchContactsTest {

    @Test
    public void getContactNotFoundCaseTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        ShadowActivity shadowActivity = shadowOf(activity);
        shadowActivity.grantPermissions(Manifest.permission.READ_CONTACTS);
        SearchContacts searchContacts = new SearchContacts(activity, activity, activity);
        String contact = searchContacts.searchContacts("Bob");
        assertEquals("-1", contact);
    }

    @Test
    public void searchBackendForContactNoUserIdTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        ShadowActivity shadowActivity = shadowOf(activity);
        shadowActivity.grantPermissions(Manifest.permission.READ_CONTACTS);
        SearchContacts searchContacts = new SearchContacts(activity, activity, activity);
        searchContacts.searchContacts("Gp");
        assertEquals("Not connected to an admin", ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void searchBackendForContactWithUserIdTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        ShadowActivity shadowActivity = shadowOf(activity);
        shadowActivity.grantPermissions(Manifest.permission.READ_CONTACTS);
        SearchContacts searchContacts = new SearchContacts(activity, activity, activity);
        MainController mainController = new MainController();
        mainController.addUserID(activity, "101");
        String number = searchContacts.searchContacts("Gp");
        assertEquals("11111111112", number);
    }

}
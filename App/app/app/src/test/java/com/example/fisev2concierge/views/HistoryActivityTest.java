package com.example.fisev2concierge.views;

import android.content.Intent;
import android.widget.ListView;

import com.example.fisev2concierge.R;
import com.example.fisev2concierge.controllers.MainController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class HistoryActivityTest {

    @Test
    public void backButtonTest() {
        HistoryActivity activity = Robolectric.buildActivity(HistoryActivity.class).create().get();
        activity.findViewById(R.id.backButtonHistory).performClick();
        Intent expectedIntent = new Intent(activity, MainActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void notConnectedToAdminListViewTest(){
        HistoryActivity activity = Robolectric.buildActivity(HistoryActivity.class).create().get();
        ListView listView = activity.findViewById(R.id.historyListView);
        assertEquals(1, listView.getCount());
    }

    @Test
    public void connectedToAdminNoHistoryListViewTest(){
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).get();
        MainController mainController = new MainController();
        mainController.addUserID(mainActivity, "0");
        HistoryActivity activity = Robolectric.buildActivity(HistoryActivity.class).create().get();
        ListView listView = activity.findViewById(R.id.historyListView);
        assertEquals(1, listView.getCount());
    }

    @Test
    public void connectedToAdminHasHistoryListViewTest(){
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).get();
        MainController mainController = new MainController();
        mainController.addUserID(mainActivity, "101");
        HistoryActivity activity = Robolectric.buildActivity(HistoryActivity.class).create().get();
        ListView listView = activity.findViewById(R.id.historyListView);
        assertTrue(listView.getCount()>1);
    }

}
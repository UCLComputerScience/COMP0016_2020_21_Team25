package com.example.fisev2concierge.UI;

import android.content.Intent;
import android.widget.ListView;

import com.example.fisev2concierge.R;
import com.example.fisev2concierge.controllers.MainController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class ViewAlarmsActivityTest {

    @Test
    public void clickingAddAlarm_shouldStartAddAlarmViewTest() {
        ViewAlarmsActivity activity = Robolectric.buildActivity(ViewAlarmsActivity.class).create().get();
        activity.findViewById(R.id.addNewAlarm).performClick();
        Intent expectedIntent = new Intent(activity, AddAlarmActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickingBackButton_shouldStartMainActivityTest() {
        ViewAlarmsActivity activity = Robolectric.buildActivity(ViewAlarmsActivity.class).create().get();
        activity.findViewById(R.id.backButtonViewAlarm).performClick();
        Intent expectedIntent = new Intent(activity, MainActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void alarmShouldBeAddedToListViewTest() {
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).get();
        MainController mainController = new MainController();
        mainController.addAlarm(mainActivity, "new alarm", "Monday 1st January 2021");
        ViewAlarmsActivity activity = Robolectric.buildActivity(ViewAlarmsActivity.class).create().get();
        ListView listView = activity.findViewById(R.id.alarmsListView);
        assertEquals(1, listView.getCount());
    }

}
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
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class ViewRemindersActivityTest {

    @Test
    public void clickingAddReminder_shouldStartAddReminderViewActivityTest() {
        ViewRemindersActivity activity = Robolectric.buildActivity(ViewRemindersActivity.class).create().get();
        activity.findViewById(R.id.addNewReminder).performClick();
        Intent expectedIntent = new Intent(activity, AddReminderActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickingBackButton_shouldStartMainActivityTest() {
        ViewRemindersActivity activity = Robolectric.buildActivity(ViewRemindersActivity.class).create().get();
        activity.findViewById(R.id.backButtonViewReminders).performClick();
        Intent expectedIntent = new Intent(activity, MainActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void reminderShouldBeAddedToListViewTest() {
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).get();
        MainController mainController = new MainController();
        mainController.addReminder(mainActivity, "new reminder");
        ViewRemindersActivity activity = Robolectric.buildActivity(ViewRemindersActivity.class).create().get();
        ListView listView = activity.findViewById(R.id.remindersListView);
        assertEquals(1, listView.getCount());
    }

}
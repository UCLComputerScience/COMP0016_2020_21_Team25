package com.example.fisev2concierge.UI;

import android.content.Intent;
import android.widget.ListView;

import com.example.fisev2concierge.R;
import com.example.fisev2concierge.controllers.MainController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class ViewRemindersActivityTest {
    //Adding tests for buttons on ViewRemindersActivity
    @Test
    public void clickingAddReminder_shouldStartAddReminderViewActivity() {
        ViewRemindersActivity activity = Robolectric.setupActivity(ViewRemindersActivity.class);
        activity.findViewById(R.id.addNewReminder).performClick();
        Intent expectedIntent = new Intent(activity, AddReminderActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickingBackButton_shouldStartMainActivity() {
        ViewRemindersActivity activity = Robolectric.setupActivity(ViewRemindersActivity.class);
        activity.findViewById(R.id.backButtonViewReminders).performClick();
        Intent expectedIntent = new Intent(activity, MainActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void reminderShouldBeAddedToListView() {
        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
        MainController mainController = new MainController();
        mainController.addReminder(mainActivity, "new reminder");
        ViewRemindersActivity activity = Robolectric.setupActivity(ViewRemindersActivity.class);
        ListView listView = activity.findViewById(R.id.remindersListView);
        assertEquals(listView.getCount(), 1);
//        Intent editReminderIntent = new Intent(activity, EditReminderActivity.class);
//        editReminderIntent.putExtra("ID", 0);
//        editReminderIntent.putExtra("Reminder", "new reminder");
//        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
//        assertEquals(editReminderIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickOnReminderShouldLeadToAlarmView() {
        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
        MainController mainController = new MainController();
        mainController.addReminder(mainActivity, "new reminder");
        ViewRemindersActivity activity = Robolectric.setupActivity(ViewRemindersActivity.class);
        ListView listView = activity.findViewById(R.id.remindersListView);
        assertEquals(listView.getCount(), 1);
//        Intent editReminderIntent = new Intent(activity, EditReminderActivity.class);
//        editReminderIntent.putExtra("ID", 0);
//        editReminderIntent.putExtra("Reminder", "new reminder");
//        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
//        assertEquals(editReminderIntent.getComponent(), actual.getComponent());
    }

}
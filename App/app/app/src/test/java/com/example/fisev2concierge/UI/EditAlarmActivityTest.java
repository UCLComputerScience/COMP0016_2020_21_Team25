package com.example.fisev2concierge.UI;

import android.content.Intent;
import android.widget.EditText;

import com.example.fisev2concierge.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class EditAlarmActivityTest {

    @Test
    public void backButtonTest() {
        EditAlarmActivity activity = Robolectric.buildActivity(EditAlarmActivity.class).create().get();
        activity.findViewById(R.id.backButtonEditAlarm).performClick();
        Intent expectedIntent = new Intent(activity, ViewAlarmsActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void saveAlarmButtonTest() {
        Intent intent = new Intent(Robolectric.buildActivity(ViewAlarmsActivity.class).create().get(), ViewAlarmsActivity.class);
        intent.putExtra("ID", 0);
        intent.putExtra("Alarm", "alarm");
        EditAlarmActivity activity = Robolectric.buildActivity(EditAlarmActivity.class).create().get();
        EditText alarm = activity.findViewById(R.id.editAlarmText);
        alarm.setText("new alarm");
        activity.findViewById(R.id.saveAlarmButton).performClick();
        Intent expectedIntent = new Intent(activity, ViewAlarmsActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void deleteAlarmButtonTest() {
        EditAlarmActivity activity = Robolectric.buildActivity(EditAlarmActivity.class).create().get();
        activity.findViewById(R.id.deleteAlarmButton).performClick();
        Intent expectedIntent = new Intent(activity, ViewAlarmsActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void datePickerFragmentTest() {
        EditAlarmActivity activity = Robolectric.buildActivity(EditAlarmActivity.class).create().get();
        activity.findViewById(R.id.selectDateButton).performClick();
    }

    @Test
    public void timePickerFragment() {
        EditAlarmActivity activity = Robolectric.buildActivity(EditAlarmActivity.class).create().get();
        activity.findViewById(R.id.alarmTimeButton).performClick();
    }
}
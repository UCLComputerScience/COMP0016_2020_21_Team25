package com.example.fisev2concierge.views;

import android.content.Intent;
import android.widget.EditText;

import com.example.fisev2concierge.R;
import com.example.fisev2concierge.model.AlarmsDbHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class AddAlarmActivityTest {

    @Test
    public void backButtonTest() {
        AddAlarmActivity activity = Robolectric.buildActivity(AddAlarmActivity.class).create().get();
        activity.findViewById(R.id.backButtonAddAlarm).performClick();
        Intent expectedIntent = new Intent(activity, ViewAlarmsActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void addAlarmButton() {
        AddAlarmActivity activity = Robolectric.buildActivity(AddAlarmActivity.class).create().get();
        EditText editText = activity.findViewById(R.id.alarmText);
        editText.setText("new alarm");
        activity.findViewById(R.id.addAlarmButton).performClick();
        AlarmsDbHelper alarmsDbHelper = new AlarmsDbHelper(activity);
        Intent expectedIntent = new Intent(activity, ViewAlarmsActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        assertEquals(1, alarmsDbHelper.getData().getCount());
    }

    @Test
    public void timePickerFragment() {
        AddAlarmActivity activity = Robolectric.buildActivity(AddAlarmActivity.class).create().get();
        activity.findViewById(R.id.alarmTimeButton).performClick();
    }

    @Test
    public void datePickerFragment() {
        AddAlarmActivity activity = Robolectric.buildActivity(AddAlarmActivity.class).create().get();
        activity.findViewById(R.id.selectDateButton).performClick();
    }

}
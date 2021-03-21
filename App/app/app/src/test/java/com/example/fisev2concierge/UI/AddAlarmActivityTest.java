package com.example.fisev2concierge.UI;

import android.content.Intent;
import android.widget.EditText;

import com.example.fisev2concierge.R;
import com.example.fisev2concierge.model.AlarmsDbHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class AddAlarmActivityTest {
    //Adding tests for buttons on ViewRemindersActivity

    @Test
    public void backButton() {
        AddAlarmActivity activity = Robolectric.setupActivity(AddAlarmActivity.class);
        activity.findViewById(R.id.backButtonAddAlarm).performClick();
        Intent expectedIntent = new Intent(activity, ViewAlarmsActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void addAlarmButton() {
        AddAlarmActivity activity = Robolectric.setupActivity(AddAlarmActivity.class);
        EditText editText = activity.findViewById(R.id.alarmText);
        editText.setText("new alarm");
        activity.findViewById(R.id.addAlarmButton).performClick();
        AlarmsDbHelper alarmsDbHelper = new AlarmsDbHelper(activity);
        Intent expectedIntent = new Intent(activity, ViewAlarmsActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        assertEquals(1, alarmsDbHelper.getData().getCount());
    }

    @Test
    public void timePickerFragment() {
        //can't test if correct output is produced
        AddAlarmActivity activity = Robolectric.setupActivity(AddAlarmActivity.class);
        activity.findViewById(R.id.alarmTimeButton).performClick();
    }

    @Test
    public void datePickerFragment() {
        //can't test if correct output is produced
        AddAlarmActivity activity = Robolectric.setupActivity(AddAlarmActivity.class);
        activity.findViewById(R.id.selectDateButton).performClick();
    }

}
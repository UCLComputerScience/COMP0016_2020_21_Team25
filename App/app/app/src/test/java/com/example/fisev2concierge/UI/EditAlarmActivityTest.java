package com.example.fisev2concierge.UI;

import android.content.Intent;
import android.widget.EditText;

import com.example.fisev2concierge.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowActivity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;
import static org.robolectric.util.FragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
public class EditAlarmActivityTest {
    //Adding tests for buttons on ViewRemindersActivity

    @Test
    public void backButton() {
        EditAlarmActivity activity = Robolectric.setupActivity(EditAlarmActivity.class);
        activity.findViewById(R.id.backButton).performClick();
        Intent expectedIntent = new Intent(activity, ViewAlarmsActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void saveAlarmButton() {
        //can't check if value is actually updated since it doesn't actually exist within database
        Intent intent = new Intent(Robolectric.setupActivity(ViewAlarmsActivity.class), ViewAlarmsActivity.class);
        intent.putExtra("ID", 0);
        intent.putExtra("Alarm", "alarm");
        EditAlarmActivity activity = Robolectric.buildActivity(EditAlarmActivity.class, intent).create().start().resume().get();
        EditText alarm = activity.findViewById(R.id.alarmText);
        alarm.setText("new alarm");
        activity.findViewById(R.id.saveAlarmButton).performClick();
        Intent expectedIntent = new Intent(activity, ViewAlarmsActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void deleteAlarmButton() {
        EditAlarmActivity activity = Robolectric.setupActivity(EditAlarmActivity.class);
        activity.findViewById(R.id.deleteAlarmButton).performClick();
        Intent expectedIntent = new Intent(activity, ViewAlarmsActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void datePickerFragment() {
        //unable to check if correct output is produced
        EditAlarmActivity activity = Robolectric.setupActivity(EditAlarmActivity.class);
        ShadowActivity shadowActivity = shadowOf(activity);
        activity.findViewById(R.id.selectDateButton).performClick();
    }

    @Test
    public void timePickerFragment() {
        //unable to check if correct output is produced
        EditAlarmActivity activity = Robolectric.setupActivity(EditAlarmActivity.class);
        activity.findViewById(R.id.alarmTimeButton).performClick();
    }
}
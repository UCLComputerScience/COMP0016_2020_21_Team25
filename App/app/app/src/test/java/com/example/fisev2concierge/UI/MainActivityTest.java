package com.example.fisev2concierge.UI;

import android.content.Intent;

import com.example.fisev2concierge.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.LooperMode;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;
import static org.robolectric.annotation.LooperMode.Mode.PAUSED;

@RunWith(RobolectricTestRunner.class)
@LooperMode(PAUSED)
public class MainActivityTest {
    //Adding tests for buttons on MainActivity

    @Test
    public void clickingRegister_shouldStartRegisterActivity() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.findViewById(R.id.activity_register_button).performClick();
        Intent expectedIntent = new Intent(activity, RegisterActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickingInstructions_shouldStartInstructionsActivity() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.findViewById(R.id.instructions_view_button).performClick();

        Intent expectedIntent = new Intent(activity, InstructionActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickingAlarms_shouldStartAlarmsActivity() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.findViewById(R.id.alarms_view_button).performClick();

        Intent expectedIntent = new Intent(activity, ViewAlarmsActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickingReminders_shouldStartRemindersActivity() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.findViewById(R.id.reminders_view_button).performClick();

        Intent expectedIntent = new Intent(activity, ViewRemindersActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickingTimers_shouldStartTimersActivity() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.findViewById(R.id.timers_view_button).performClick();

        Intent expectedIntent = new Intent(activity, TimersActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickingHistory_shouldStartHistoryActivity() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.findViewById(R.id.history_view_button).performClick();
        Intent expectedIntent = new Intent(activity, HistoryActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

//    @Test
//    public void holdingMicIcon_shouldChangeMicIcon() {
//        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
////        ImageView imageView = activity.findViewById(R.id.tapToStartConciergeIcon);
//        MotionEvent motionEvent = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), ACTION_DOWN, 10, 10, 0);
//        activity.findViewById(R.id.tapToStartConciergeIcon).dispatchGenericMotionEvent(motionEvent);
////        assertEquals(imageView.getId(), R.drawable.miciconoffwithbg);
//    }

//    @Test
//    public void speechRecognition() {
//        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
//        ImageView imageView = activity.findViewById(R.id.tapToStartConciergeIcon);
////        activity.findViewById(R.id.tapToStartConciergeIcon).performClick();
//        assertEquals(imageView.getId(), R.drawable.miciconoffwithbg);
//    }




    //concierge status text changing on voice command
    //mic icon changing on holding down mic icon
    //speech recognition working
    //speech synthesis working
    //permission granting

}
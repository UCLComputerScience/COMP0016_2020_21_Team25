package com.example.fisev2concierge.speech;

import com.example.fisev2concierge.R;
import com.example.fisev2concierge.UI.HistoryActivity;
import com.example.fisev2concierge.UI.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class SpeechRecognitionTest {

    @Test
    public void checkPermissionTest() {
        HistoryActivity activity = Robolectric.buildActivity(HistoryActivity.class).get();
        ShadowActivity shadowActivity = shadowOf(activity);
        SpeechRecognition speechRecognition = new SpeechRecognition();
        speechRecognition.checkPermission(activity);
        assertEquals("android.permission.RECORD_AUDIO", shadowActivity.getLastRequestedPermission().requestedPermissions[0]);
    }

    @Test
    public void configTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        SpeechRecognition speechRecognition = new SpeechRecognition();
        speechRecognition.config(activity, null, activity, activity, activity.findViewById(R.id.conciergeStatusText));
    }

    @Test
    public void startListeningTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        SpeechRecognition speechRecognition = new SpeechRecognition();
        speechRecognition.config(activity, null, activity, activity, activity.findViewById(R.id.conciergeStatusText));
        speechRecognition.startListening();
    }

    @Test
    public void stopListeningTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        SpeechRecognition speechRecognition = new SpeechRecognition();
        speechRecognition.config(activity, null, activity, activity, activity.findViewById(R.id.conciergeStatusText));
        speechRecognition.stopListening();
    }



}
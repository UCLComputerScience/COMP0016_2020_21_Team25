package com.example.fisev2concierge.speech;

import com.example.fisev2concierge.UI.MainActivity;
import com.example.fisev2concierge.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class SpeechRecognitionTest {
    //Adding tests for buttons on ViewRemindersActivity

    @Test
    public void backButton() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        SpeechRecognition speechRecognition = new SpeechRecognition();
        speechRecognition.checkPermission(activity);
        speechRecognition.config(activity, null, activity, activity, activity.findViewById(R.id.conciergeStatusText));
        speechRecognition.startListening();
        speechRecognition.stopListening();
    }



}
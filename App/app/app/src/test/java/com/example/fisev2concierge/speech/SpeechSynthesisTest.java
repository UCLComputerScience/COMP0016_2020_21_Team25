package com.example.fisev2concierge.speech;

import com.example.fisev2concierge.UI.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class SpeechSynthesisTest {
    //Adding tests for buttons on ViewRemindersActivity

    @Test
    public void backButton() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        speechSynthesis.runTts("test");
    }



}
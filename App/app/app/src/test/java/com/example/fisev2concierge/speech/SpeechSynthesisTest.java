package com.example.fisev2concierge.speech;

import com.example.fisev2concierge.views.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class SpeechSynthesisTest {

    @Test
    public void configTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
    }

    @Test
    public void runTtsTest(){
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(activity);
        speechSynthesis.runTts("test");
    }



}
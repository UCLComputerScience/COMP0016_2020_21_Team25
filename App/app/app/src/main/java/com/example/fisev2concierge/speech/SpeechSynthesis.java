package com.example.fisev2concierge.speech;

import android.speech.tts.TextToSpeech;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SpeechSynthesis {

    TextToSpeech tts;

    public void configTts(AppCompatActivity appCompatActivity){
        tts = new TextToSpeech(appCompatActivity.getApplicationContext(), status -> {
            if(status != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.UK);
            }
        });
    }

    public void runTts(String s){
        tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
    }
}

package com.example.fisev2concierge.speech;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.fisev2concierge.controllers.MainController;

import java.util.ArrayList;
import java.util.Locale;

public class SpeechRecognition{

    public static final Integer RecordAudioRequestCode = 1;

    private TextView conciergeStatusText;
    private SpeechSynthesis speechSynthesis;
    private AppCompatActivity appCompatActivity;
    private Context context;
    private Activity activity;
    private final MainController mainController = new MainController();
    private final String[] result = new String[]{""};

    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;

    private void createSpeechRecognizer(AppCompatActivity appCompatActivity){
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(appCompatActivity);
    }

    private void createSpeechRecognizerIntent(){
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    }

    private void configSpeechRecognizer(){
        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public synchronized void onResults(Bundle results) {
                ArrayList<String> matches= results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                //on the tablet, returning 'matches' from here to MainActivity did not work -> it would always cause an Empty Input error
                //therefore, MainController must be called directly from here
                if(matches!=null){
                    result[0] = (matches.get(0));
                    mainController.handleUserRequest(result, speechSynthesis, appCompatActivity, context, activity, conciergeStatusText);
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }

        });
    }

    private void configSpeechRecognizerIntent(){
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
    }

    public void checkPermission(AppCompatActivity appCompatActivity){
        if (!(ContextCompat.checkSelfPermission(appCompatActivity, Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_GRANTED)){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                ActivityCompat.requestPermissions(appCompatActivity,new String[]{Manifest.permission.RECORD_AUDIO},RecordAudioRequestCode);
            }
        }
    }

    public void startListening(){
        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
    }

    public void stopListening(){
        mSpeechRecognizer.stopListening();
    }

    public void config(AppCompatActivity appCompatActivity, SpeechSynthesis speechSynthesis, Context context, Activity activity, TextView conciergeStatusText) {
        this.speechSynthesis = speechSynthesis;
        this.appCompatActivity = appCompatActivity;
        this.context = context;
        this.activity = activity;
        this.conciergeStatusText = conciergeStatusText;
        createSpeechRecognizer(appCompatActivity);
        createSpeechRecognizerIntent();
        configSpeechRecognizer();
        configSpeechRecognizerIntent();
    }
}

package com.example.fisev2concierge.speech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Locale;

public class SpeechRecognition {

    public static final Integer RecordAudioRequestCode = 1;

    public SpeechRecognizer mSpeechRecognizer;
    public Intent mSpeechRecognizerIntent;

    public Integer getRecordAudioRequestCode(){
        return RecordAudioRequestCode;
    }

    public SpeechRecognizer getmSpeechRecognizer(){
        return mSpeechRecognizer;
    }

    public Intent getmSpeechRecognizerIntent(){
        return mSpeechRecognizerIntent;
    }

    private void createSpeechRecognizer(AppCompatActivity appCompatActivity){
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(appCompatActivity);
    }

    private void createSpeechRecognizerIntent(){
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    }

    private void configSpeechRecognizer(EditText conciergeStatusText){
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
            public void onResults(Bundle results) {
                ArrayList<String> matches= results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                if(matches!=null){
                    conciergeStatusText.setText(matches.get(0));
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

    public void run(AppCompatActivity appCompatActivity, EditText editText) {
        System.out.println("Created Speech Recognizer");
        createSpeechRecognizer(appCompatActivity);
        System.out.println("Created Speech Recognizer Intent");
        createSpeechRecognizerIntent();
        System.out.println("Configured Speech Recognizer");
        configSpeechRecognizer(editText);
        System.out.println("Configured Speech Recognizer Intent");
        configSpeechRecognizerIntent();
    }

}

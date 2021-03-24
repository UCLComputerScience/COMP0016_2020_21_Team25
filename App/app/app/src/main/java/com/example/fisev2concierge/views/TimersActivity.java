package com.example.fisev2concierge.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.R;

public class TimersActivity extends AppCompatActivity {
    private Button startTimerButton;

    private CountDownTimer countDownTimer;
    private long timeLeftMS;
    private boolean timerRunning;
    private NumberPicker hoursNumberPicker, minutesNumberPicker, secondsNumberPicker;
    boolean initialised = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timers);

        startTimerButton = findViewById(R.id.startTimerButton);
        Button backButton = findViewById(R.id.backButtonTimers);
        hoursNumberPicker = findViewById(R.id.hoursNumberPicker);
        minutesNumberPicker = findViewById(R.id.minutesNumberPicker);
        secondsNumberPicker= findViewById(R.id.secondsNumberPicker);

        hoursNumberPicker.setMinValue(0);
        hoursNumberPicker.setMaxValue(99);
        minutesNumberPicker.setMinValue(0);
        minutesNumberPicker.setMaxValue(59);
        secondsNumberPicker.setMinValue(0);
        secondsNumberPicker.setMaxValue(59);

        hoursNumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        minutesNumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        secondsNumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(TimersActivity.this, MainActivity.class);
            startActivity(intent);
        });

        startTimerButton.setOnClickListener(v -> {
            if (!initialised) {
                int hour = hoursNumberPicker.getValue();
                int minutes = minutesNumberPicker.getValue();
                int seconds = secondsNumberPicker.getValue();
                long inputMS = (((hour * 60) + minutes) * 60000) + seconds * 1000;
                if (inputMS == 0) {
                    Toast.makeText(TimersActivity.this, "Please enter a number greater than 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                initialised = true;
                setTime(inputMS);
            }
            startStop();
        });
        updateTimer();
    }

    public void setTime(long ms){
        timeLeftMS = ms;
        updateTimer();
    }

    public void startStop(){
        if (timerRunning){
            stopTimer();
        } else {
            startTimer();
        }
    }

    public void startTimer(){
        hoursNumberPicker.setEnabled(false);
        minutesNumberPicker.setEnabled(false);
        secondsNumberPicker.setEnabled(false);
        countDownTimer = new CountDownTimer(timeLeftMS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftMS = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                hoursNumberPicker.setEnabled(true);
                minutesNumberPicker.setEnabled(true);
                secondsNumberPicker.setEnabled(true);
                timerRunning = false;
                startTimerButton.setText("Start");
            }
        }.start();
        startTimerButton.setText("Pause");
        timerRunning = true;
    }

    public void stopTimer(){
        startTimerButton.setText("Start");
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        hoursNumberPicker.setEnabled(true);
        minutesNumberPicker.setEnabled(true);
        secondsNumberPicker.setEnabled(true);
        timerRunning = false;
    }

    public void updateTimer(){
        int hours = (int) (timeLeftMS / 1000) / 3600;
        int minutes = (int) ( (timeLeftMS / 1000) % 3600) / 60;
        int seconds = (int) (timeLeftMS / 1000) % 60;

        hoursNumberPicker.setValue(hours);
        minutesNumberPicker.setValue(minutes);
        secondsNumberPicker.setValue(seconds);
    }

}
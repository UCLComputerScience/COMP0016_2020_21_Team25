package com.example.fisev2concierge;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TimersView extends AppCompatActivity {
    private Button startTimerButton;
    private TextView countdownText;
    private EditText timeStartInput;
    private Button timeStartButton, backButton;

    private CountDownTimer countDownTimer;
    private long timeStartMS;
    private long timeLeftMS = 600000;
    private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timers_view);

        countdownText = findViewById(R.id.countdownText);
        startTimerButton = findViewById(R.id.startTimerButton);
        timeStartInput = findViewById(R.id.setTimerText);
        timeStartButton = findViewById(R.id.setTimerButton);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimersView.this, MainActivity.class);
                startActivity(intent);
            }
        });

        timeStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = timeStartInput.getText().toString();
                if (input.length() == 0){
                    Toast.makeText(TimersView.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                long inputMS = Long.parseLong(input) * 60000;
                if (inputMS == 0){
                    Toast.makeText(TimersView.this, "Please enter a number greater than 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                timeStartInput.setText("");
                setTime(inputMS);
            }
        });

        startTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();
            }
        });

        updateTimer();
    }

    public void setTime(long ms){
        timeStartMS = ms;
        timeLeftMS = timeStartMS;
        updateTimer();
        closeKeyboard();
    }

    public void startStop(){
        if (timerRunning){
            stopTimer();
        } else {
            startTimer();
        }
    }

    public void startTimer(){
        countDownTimer = new CountDownTimer(timeLeftMS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftMS = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                startTimerButton.setText("Start");

            }
        }.start();
        startTimerButton.setText("Pause");
        timerRunning = true;
    }

    public void stopTimer(){
        startTimerButton.setText("Start");
        countDownTimer.cancel();
        timerRunning = false;
    }

    public void updateTimer(){
        int minutes = (int) timeLeftMS / 60000;
        int seconds = (int) timeLeftMS % 60000 / 1000;

        String timeLeftText;

        timeLeftText = "" + minutes + ":";
        if (seconds < 10) {
            timeLeftText += "0";
        }
        timeLeftText += seconds;
        //change number picker rather than text
        countdownText.setText(timeLeftText);
    }

    public void closeKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

}
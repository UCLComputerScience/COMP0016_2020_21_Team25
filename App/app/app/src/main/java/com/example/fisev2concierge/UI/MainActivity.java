package com.example.fisev2concierge.UI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fisev2concierge.R;
import com.example.fisev2concierge.controllers.MainController;
import com.example.fisev2concierge.speech.SpeechRecognition;
import com.example.fisev2concierge.speech.SpeechSynthesis;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.RECORD_AUDIO, Manifest.permission.INTERNET, Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, 10);

        //check session to see if we have ip of server
        //if not make request
        SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        if (!sharedpreferences.contains("server_ip")){
            String ip = new MainController().findServerIp();
            if (ip.startsWith("/")) {
                ip = ip.substring(1);
            }
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("server_ip", ip);
            editor.apply();
            Toast.makeText(MainActivity.this, "Connected to server!", Toast.LENGTH_SHORT).show();
        }

        TextView conciergeStatusText = findViewById(R.id.conciergeStatusText);

        //Speech Synthesis must be defined in MainActivity
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(this);

        //note that speech recognition software must be installed and configured on target device
        SpeechRecognition speechRecognition = new SpeechRecognition();
        speechRecognition.checkPermission(this);
        speechRecognition.config(this, speechSynthesis, this, this, conciergeStatusText);

        ImageView mic = findViewById(R.id.tapToStartConciergeIcon);
        mic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public synchronized boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_UP:
                        speechRecognition.stopListening();
                        mic.setImageResource(R.drawable.miciconoff);
                        conciergeStatusText.setHint("Give Concierge a command");
                        break;
                    case MotionEvent.ACTION_DOWN:
                        speechRecognition.startListening();
                        mic.setImageResource(R.drawable.miciconon);
                        conciergeStatusText.setText("");
                        conciergeStatusText.setHint("Concierge is listening");
                        break;
                }
                return false;
            }
        });

        Button activity_register = findViewById(R.id.activity_register_button);
        activity_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button history_view_button = findViewById(R.id.history_view_button);
        history_view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        Button reminders_view_button = findViewById(R.id.reminders_view_button);
        reminders_view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewRemindersActivity.class);
                startActivity(intent);
            }
        });

        Button alarms_view_button = findViewById(R.id.alarms_view_button);
        alarms_view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewAlarmsActivity.class);
                startActivity(intent);
            }
        });

        Button timers_view_button = findViewById(R.id.timers_view_button);
        timers_view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TimersActivity.class);
                startActivity(intent);
            }
        });

        Button help_view_button = findViewById(R.id.instructions_view_button);
        help_view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InstructionActivity.class);
                startActivity(intent);
            }
        });

        Button about_activity_button = findViewById(R.id.about_activity_button);
        about_activity_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
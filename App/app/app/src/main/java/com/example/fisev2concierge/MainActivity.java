package com.example.fisev2concierge;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fisev2concierge.controllers.MainController;
import com.example.fisev2concierge.speech.SpeechRecognition;
import static com.example.fisev2concierge.speech.SpeechRecognition.RecordAudioRequestCode;
import com.example.fisev2concierge.speech.SpeechSynthesis;

import org.w3c.dom.Text;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ask for all permissions
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.RECORD_AUDIO, Manifest.permission.INTERNET, Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, 10);

        TextView conciergeStatusText = findViewById(R.id.conciergeStatusText);

        //Speech Synthesis defined on main thread
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(this);

        //note that speech recognition software must be installed and configured on target device
        SpeechRecognition speechRecognition = new SpeechRecognition();
        speechRecognition.checkPermission(this);
        speechRecognition.config(this, speechSynthesis, this, this, conciergeStatusText);

        ImageView mic = findViewById(R.id.tapToStartConciergeIcon);
        findViewById(R.id.tapToStartConciergeIcon).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public synchronized boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_UP:
                        //test opening apps
                        //test if calls are made automatically after being granted permission
                        speechRecognition.stopListening();
                        mic.setImageResource(R.drawable.miciconoffwithbg);
                        conciergeStatusText.setHint("Concierge is off");
                        break;
                    case MotionEvent.ACTION_DOWN:
                        speechRecognition.startListening();
                        mic.setImageResource(R.drawable.micicononwithbg);
                        conciergeStatusText.setText("");
                        conciergeStatusText.setHint("Concierge is listening");
                        break;
                }
                return false;
            }
        });

        Button history_view_button = findViewById(R.id.history_view_button);
        history_view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryView.class);
                startActivity(intent);
            }
        });

        Button reminders_view_button = findViewById(R.id.reminders_view_button);
        reminders_view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewRemindersView.class);
                startActivity(intent);
            }
        });

        Button alarms_view_button = findViewById(R.id.alarms_view_button);
        alarms_view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewAlarmsView.class);
                startActivity(intent);
            }
        });

        Button timers_view_button = findViewById(R.id.timers_view_button);
        timers_view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TimersView.class);
                startActivity(intent);
            }
        });

        Button help_view_button = findViewById(R.id.instructions_view_button);
        help_view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HelpView.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RecordAudioRequestCode && grantResults.length > 0 ){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this,"Permission Granted", Toast.LENGTH_SHORT).show();
        }
    }
}
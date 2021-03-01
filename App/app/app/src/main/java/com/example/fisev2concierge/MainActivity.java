package com.example.fisev2concierge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.content.pm.PackageManager;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.fisev2concierge.controllers.MainController;
import com.example.fisev2concierge.speech.SpeechRecognition;
import static com.example.fisev2concierge.speech.SpeechRecognition.RecordAudioRequestCode;
import com.example.fisev2concierge.speech.SpeechSynthesis;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText conciergeStatusText = findViewById(R.id.conciergeStatusText);

        SpeechRecognition speechRecognition = new SpeechRecognition();
        speechRecognition.checkPermission(this);
        speechRecognition.config(this, conciergeStatusText);

        MainController mainController = new MainController();
        //Speech Synthesis defined on main thread
        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(this);

        findViewById(R.id.tapToStartConciergeIcon).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {    
                switch(event.getAction()){
                    case MotionEvent.ACTION_UP:
                        //test opening apps
                        //test if calls are made automatically after being granted permission
                        speechRecognition.stopListening();
                        String userRequest = String.valueOf(conciergeStatusText.getText());
                        conciergeStatusText.setHint("Concierge is off");
                        if (userRequest.length()>0){
                            HashMap askBobResponse = mainController.askBobRequest(userRequest);
                            if (askBobResponse.get("Service_Type").equals("API_CALL")){
                                speechSynthesis.runTts((String) askBobResponse.get("Response"));
                            } else if (askBobResponse.get("Service_Type").equals("ERROR")){
                                speechSynthesis.runTts((String) askBobResponse.get("text"));
                                Toast.makeText(MainActivity.this, "Command not understood", Toast.LENGTH_SHORT).show();
                            } else {
                                mainController.askBobController(askBobResponse, MainActivity.this, MainActivity.this, MainActivity.this);
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Empty input", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case MotionEvent.ACTION_DOWN:
                        speechRecognition.startListening();
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
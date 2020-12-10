package com.example.fisev2concierge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.content.pm.PackageManager;
import android.speech.SpeechRecognizer;
import android.view.MotionEvent;
import android.widget.Toast;
import com.example.fisev2concierge.speech.SpeechRecognition;
import static com.example.fisev2concierge.speech.SpeechRecognition.RecordAudioRequestCode;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.os.Build;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText conciergeStatusText = findViewById(R.id.conciergeStatusText);

        SpeechRecognition speechRecognition = new SpeechRecognition();
        speechRecognition.checkPermission(this);
        speechRecognition.run(this, conciergeStatusText);

        SpeechRecognizer mSpeechRecognizer = speechRecognition.getmSpeechRecognizer();
        Intent mSpeechRecognizerIntent = speechRecognition.getmSpeechRecognizerIntent();

        //Change this to on tap rather than hold down to keep speaking
        findViewById(R.id.tapToStartConciergeIcon).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizer.stopListening();
                        conciergeStatusText.setHint("Concierge is off");
                        break;
                    case MotionEvent.ACTION_DOWN:
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        conciergeStatusText.setText("");
                        conciergeStatusText.setHint("Concierge is listening");
                        break;
                }
                return false;
            }
        });

//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);

//         Adding functionality to History_View_Button

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
                Intent intent = new Intent(MainActivity.this, RemindersView.class);
                startActivity(intent);
            }
        });

        Button alarms_view_button = findViewById(R.id.alarms_view_button);
        alarms_view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AlarmsView.class);
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

        Button help_view_button = findViewById(R.id.help_view_button);
        help_view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HelpView.class);
                startActivity(intent);
            }
        });

//        ImageView test = findViewById(R.id.tapToStartConciergeIcon);
//        test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("hello");
//            }
//        });

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
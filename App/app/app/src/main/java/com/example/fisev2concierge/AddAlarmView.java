package com.example.fisev2concierge;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import com.example.fisev2concierge.controllers.MainController;
import com.example.fisev2concierge.helperClasses.DatePickerFragment;
import com.example.fisev2concierge.helperClasses.NotificationHelper;
import com.example.fisev2concierge.helperClasses.TimePickerFragment;

import java.text.DateFormat;
import java.util.Calendar;

public class AddAlarmView extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private Button addAlarmButton, alarmTimeButton, alarmDateButton, backButton;
    private EditText alarmText;
    private TextView timeSelectedText, dateSelectedText;
    private Calendar c = Calendar.getInstance();
    private MainController mainController = new MainController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm_view);

        alarmText = findViewById(R.id.alarmText);
        alarmTimeButton = findViewById(R.id.alarmTimeButton);
        addAlarmButton = findViewById(R.id.addAlarmButton);
        timeSelectedText = findViewById(R.id.timeSelectedText);
        alarmDateButton = findViewById(R.id.selectDateButton);
        dateSelectedText = findViewById(R.id.dateSelectedText);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAlarmView.this, ViewAlarmsView.class);
                startActivity(intent);
            }
        });

        alarmDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        alarmTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        addAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = alarmText.getText().toString();
                String newDate = dateSelectedText.getText().toString();
                String newTime = timeSelectedText.getText().toString();
                String date = newDate + " " + newTime;
                if (newEntry.length() != 0){
                    AddData(newEntry, date);
                    alarmText.setText("");
                    startAlarm(c);
                    Intent intent = new Intent(AddAlarmView.this, ViewAlarmsView.class);
                    startActivity(intent);
                } else {
                    toastMessage("Field cannot be empty");
                }
            }
        });
    }

    String newID = "";
    public void AddData(String newEntry, String date){
        boolean insertData = mainController.addAlarm(AddAlarmView.this, newEntry, date);
        if (insertData){
            toastMessage("Data entered successfully");
            Cursor data = mainController.getRecentAlarm(AddAlarmView.this);
            while (data.moveToNext()){
                newID = data.getString(0);
            }
        } else {
            toastMessage("Data not entered successfully");
        }
    }

    /**
     * Customised toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        updateTimeText(c);
    }

    private void updateTimeText(Calendar c){
        String timeText = "Alarm set for ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        timeSelectedText.setText(timeText);
    }

    private void startAlarm(Calendar c){
        mainController.startAlarm(AddAlarmView.this, AddAlarmView.this, newID, c);
        System.out.println(newID);
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(this, AlertReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, Integer.parseInt(newID), intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
//        } else {
//            alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
//        }
    }

    public void test(){
        NotificationHelper notificationHelper = new NotificationHelper(this);
        NotificationCompat.Builder nb = notificationHelper.getChannel1Notification("Test", "Message");
        notificationHelper.getManager().notify(1, nb.build());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String dateSelected = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        dateSelectedText.setText(dateSelected);
    }
}
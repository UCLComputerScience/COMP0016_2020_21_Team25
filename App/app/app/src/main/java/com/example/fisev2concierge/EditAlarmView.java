package com.example.fisev2concierge;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.fisev2concierge.controllers.MainController;
import com.example.fisev2concierge.helperClasses.DatePickerFragment;
import com.example.fisev2concierge.helperClasses.TimePickerFragment;

import java.text.DateFormat;
import java.util.Calendar;

public class EditAlarmView extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private Button alarmTimeButton, alarmDateButton;
    private TextView timeSelectedText, dateSelectedText;
    private Button deleteAlarmButton, saveAlarmButton, backButton;
    private EditText alarmText;
    private MainController mainController = new MainController();
    private String selectedAlarm = "";
    private int selectedID;
    private Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_alarm_view);
        alarmTimeButton = findViewById(R.id.alarmTimeButton);
        alarmDateButton = findViewById(R.id.selectDateButton);
        timeSelectedText = findViewById(R.id.timeSelectedText);
        dateSelectedText = findViewById(R.id.dateSelectedText);
        deleteAlarmButton = findViewById(R.id.deleteAlarmButton);
        saveAlarmButton = findViewById(R.id.saveAlarmButton);
        alarmText = findViewById(R.id.editAlarmText);
        backButton = findViewById(R.id.backButton);

        Intent receivedIntent = getIntent();

        selectedID = receivedIntent.getIntExtra("ID", -1);
        selectedAlarm = receivedIntent.getStringExtra("Alarm");

        alarmText.setText(selectedAlarm);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditAlarmView.this, ViewAlarmsView.class);
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

        saveAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = alarmText.getText().toString();
                String newDate = dateSelectedText.getText().toString();
                String newTime = timeSelectedText.getText().toString();
                String date = newDate + " " + newTime;
                if (!item.equals("")){
                    mainController.updateAlarm(EditAlarmView.this, selectedAlarm, selectedID, item, date);
                    setNewAlarm(selectedID);
                    Intent intent = new Intent(EditAlarmView.this, ViewAlarmsView.class);
                    startActivity(intent);
                }
            }
        });

        deleteAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainController.deleteAlarm(EditAlarmView.this, selectedID, selectedAlarm);
                alarmText.setText("");
                deleteAlarm(selectedID);
                Intent intent = new Intent(EditAlarmView.this, ViewAlarmsView.class);
                startActivity(intent);
            }
        });
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String dateSelected = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        dateSelectedText.setText(dateSelected);
    }

    private void setNewAlarm(int id){
        deleteAlarm(id);
        mainController.startAlarm(EditAlarmView.this, EditAlarmView.this, id + "", c);
    }

    private void deleteAlarm(int id){
        mainController.stopAlarm(EditAlarmView.this, EditAlarmView.this, id);
    }
}

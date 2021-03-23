package com.example.fisev2concierge.views;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.fisev2concierge.R;
import com.example.fisev2concierge.controllers.MainController;
import com.example.fisev2concierge.helperClasses.DatePickerFragment;
import com.example.fisev2concierge.helperClasses.TimePickerFragment;

import java.text.DateFormat;
import java.util.Calendar;

public class EditAlarmActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private TextView timeSelectedText, dateSelectedText;
    private EditText alarmText;
    private final MainController mainController = new MainController();
    private int selectedID;
    private final Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);
        Button alarmTimeButton = findViewById(R.id.alarmTimeButton);
        Button alarmDateButton = findViewById(R.id.selectDateButton);
        timeSelectedText = findViewById(R.id.timeSelectedText);
        dateSelectedText = findViewById(R.id.dateSelectedText);
        Button deleteAlarmButton = findViewById(R.id.deleteAlarmButton);
        Button saveAlarmButton = findViewById(R.id.saveAlarmButton);
        alarmText = findViewById(R.id.editAlarmText);
        Button backButton = findViewById(R.id.backButtonEditAlarm);

        Intent receivedIntent = getIntent();

        selectedID = receivedIntent.getIntExtra("ID", -1);
        String selectedAlarm = receivedIntent.getStringExtra("Alarm");
        alarmText.setText(selectedAlarm);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(EditAlarmActivity.this, ViewAlarmsActivity.class);
            startActivity(intent);
        });

        alarmDateButton.setOnClickListener(v -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "Date Picker");
        });

        alarmTimeButton.setOnClickListener(v -> {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "time picker");
        });

        saveAlarmButton.setOnClickListener(v -> {
            String item = alarmText.getText().toString();
            String newDate = dateSelectedText.getText().toString();
            String newTime = timeSelectedText.getText().toString();
            String date = newDate + " " + newTime;
            if (!item.equals("")){
                mainController.updateAlarm(EditAlarmActivity.this, selectedID, item, date);
                setNewAlarm(selectedID, item);
                Intent intent = new Intent(EditAlarmActivity.this, ViewAlarmsActivity.class);
                startActivity(intent);
            }
        });

        deleteAlarmButton.setOnClickListener(v -> {
            mainController.deleteAlarm(EditAlarmActivity.this, selectedID);
            alarmText.setText("");
            deleteAlarm(selectedID);
            Intent intent = new Intent(EditAlarmActivity.this, ViewAlarmsActivity.class);
            startActivity(intent);
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
        String timeText = "Time: ";
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

    private void setNewAlarm(int id, String message){
        deleteAlarm(id);
        mainController.startAlarm(EditAlarmActivity.this, EditAlarmActivity.this, id + "", c, message);
    }

    private void deleteAlarm(int id){
        mainController.stopAlarm(EditAlarmActivity.this, EditAlarmActivity.this, id);
    }
}

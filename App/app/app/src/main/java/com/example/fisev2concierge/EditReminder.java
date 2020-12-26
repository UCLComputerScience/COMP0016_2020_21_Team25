package com.example.fisev2concierge;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.fisev2concierge.model.DbHelper;

import java.text.DateFormat;
import java.util.Calendar;

public class EditReminder extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private static final String TAG = "EditReminder";
    private Button reminderTimeButton, reminderDateButton;
    private TextView timeSelectedText, dateSelectedText;
    private Button deleteReminderButton, saveReminderButton;
    private EditText reminderText;
    DbHelper dbHelper;

    private String selectedReminder = "";
    private int selectedID;
    //add and delete alarms
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_reminder_view);
        //need to add date and time here
        reminderTimeButton = findViewById(R.id.reminderTimeButton);
        reminderDateButton = findViewById(R.id.selectDateButton);
        timeSelectedText = findViewById(R.id.timeSelectedText);
        dateSelectedText = findViewById(R.id.dateSelectedText);
        deleteReminderButton = findViewById(R.id.deleteReminderButton);
        saveReminderButton = findViewById(R.id.saveReminderButton);
        reminderText = findViewById(R.id.editReminderText);
        dbHelper = new DbHelper(this);

        Intent receivedIntent = getIntent();

        selectedID = receivedIntent.getIntExtra("ID", -1);
        selectedReminder = receivedIntent.getStringExtra("Reminder");

        reminderText.setText(selectedReminder);

        reminderDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        reminderTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        saveReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = reminderText.getText().toString();
                String newDate = dateSelectedText.getText().toString();
                String newTime = timeSelectedText.getText().toString();
                String date = newDate + " " + newTime;
                if (!item.equals("")){
                    dbHelper.updateReminder(selectedReminder, selectedID, item, date);
                    setNewAlarm(selectedID);
                    //add Toasts
                    Intent intent = new Intent(EditReminder.this, ReminderList.class);
                    startActivity(intent);
                }
            }
        });
        //needs to be changed
        deleteReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteReminder(selectedID, selectedReminder);
                reminderText.setText("");
                //may have to change this as we accommodate for more alarms
                deleteAlarm(selectedID);
                Intent intent = new Intent(EditReminder.this, ReminderList.class);
                startActivity(intent);
            }
        });
    }

    Calendar c = Calendar.getInstance();
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        updateTimeText(c);
//        startAlarm(c);
    }

    private void updateTimeText(Calendar c){
        String timeText = "Reminder set for ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        timeSelectedText.setText(timeText);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //note this supports multilingual
//        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String dateSelected = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        dateSelectedText.setText(dateSelected);
    }

    private void setNewAlarm(int id){
        deleteAlarm(id);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //different
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        }
    }

    private void deleteAlarm(int id){
        //this doesn't delete existing alarms!!!!!!!!!!
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(this, AlertReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0);
//        alarmManager.cancel(pendingIntent);

        Intent intent = new Intent(this.getApplicationContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) this.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}

package com.example.fisev2concierge;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
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

import com.example.fisev2concierge.model.DbHelper;
import com.example.fisev2concierge.model.Notification;

import java.text.DateFormat;
import java.util.Calendar;

public class RemindersView extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private static final String TAG = "RemindersView";
    DbHelper dbHelper;
    private Button addReminderButton, viewReminderButton, reminderTimeButton, reminderDateButton;
    private EditText reminderText;
    private TextView timeSelectedText, dateSelectedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminders_view);

        reminderText = findViewById(R.id.reminderText);
        reminderTimeButton = findViewById(R.id.reminderTimeButton);
        addReminderButton = findViewById(R.id.addReminderButton);
        viewReminderButton = findViewById(R.id.viewReminderButton);
        timeSelectedText = findViewById(R.id.timeSelectedText);
        reminderDateButton = findViewById(R.id.selectDateButton);
        dateSelectedText = findViewById(R.id.dateSelectedText);
        dbHelper = new DbHelper(this);

        Button testButton = findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("brrrr");
                test();
            }
        });

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
                //store the time
                //create an alarm for this time
                //send notification at this time
            }
        });

        addReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = reminderText.getText().toString();
                String newDate = dateSelectedText.getText().toString();
                String newTime = timeSelectedText.getText().toString();
                String date = newDate + " " + newTime;
                if (newEntry.length() != 0){
                    AddData(newEntry, date);
                    reminderText.setText("");
                    startAlarm(c);
                } else {
                    toastMessage("Field cannot be empty");
                }

            }
        });

        viewReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RemindersView.this, ReminderList.class);
                startActivity(intent);
            }
        });

    }
    String newID = "";
    public void AddData(String newEntry, String date){
        boolean insertData = dbHelper.addData(newEntry, date);
        if (insertData){
            toastMessage("Data entered successfully");
            Cursor data = dbHelper.getRecent();
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

    private void startAlarm(Calendar c){
        System.out.println("brrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
        System.out.println(newID);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        //second argumet below must be different for each pending intent!
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, Integer.parseInt(newID), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //different
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        }
    }
    //need to expand beyond just two notificattion channels ottherwise cant have multiple notifications for the same time
    public void test(){
        NotificationHelper notificationHelper = new NotificationHelper(this);
        NotificationCompat.Builder nb = notificationHelper.getChannel1Notification("Test", "Message");
        notificationHelper.getManager().notify(1, nb.build());
    }

//    public void cancelAlarm(){
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(this, AlertReceiver.class);
//        //second argumet below must be different for each pending intent!
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
//        alarmManager.cancel(pendingIntent);
//    }

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
}
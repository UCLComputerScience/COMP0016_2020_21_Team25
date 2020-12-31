package com.example.fisev2concierge;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import com.example.fisev2concierge.helperClasses.NotificationHelper;
import com.example.fisev2concierge.model.RemindersDbHelper;

public class AddReminderView extends AppCompatActivity {

    RemindersDbHelper dbHelper;
    private Button addReminderButton, backButton;
    private EditText reminderText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_reminder_view);

        reminderText = findViewById(R.id.reminderText);
        addReminderButton = findViewById(R.id.addReminderButton);
        backButton = findViewById(R.id.backButton);
        dbHelper = new RemindersDbHelper(this);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddReminderView.this, ViewRemindersView.class);
                startActivity(intent);
            }
        });

        addReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = reminderText.getText().toString();
                if (newEntry.length() != 0){
                    AddData(newEntry);
                    reminderText.setText("");
                    Intent intent = new Intent(AddReminderView.this, ViewRemindersView.class);
                    startActivity(intent);
                } else {
                    toastMessage("Field cannot be empty");
                }

            }
        });
    }

    String newID = "";
    public void AddData(String newEntry){
        boolean insertData = dbHelper.addData(newEntry);
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

    public void test(){
        NotificationHelper notificationHelper = new NotificationHelper(this);
        NotificationCompat.Builder nb = notificationHelper.getChannel1Notification("Test", "Message");
        notificationHelper.getManager().notify(1, nb.build());
    }
}
package com.example.fisev2concierge.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.R;
import com.example.fisev2concierge.controllers.MainController;

public class EditReminderActivity extends AppCompatActivity {

    private EditText reminderText;
    private int selectedID;
    private final MainController mainController = new MainController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reminder);
        Button deleteReminderButton = findViewById(R.id.deleteReminderButton);
        Button saveReminderButton = findViewById(R.id.saveReminderButton);
        reminderText = findViewById(R.id.editReminderText);
        Button backButton = findViewById(R.id.backButtonEditReminder);

        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("ID", -1);
        String selectedReminder = receivedIntent.getStringExtra("Reminder");

        reminderText.setText(selectedReminder);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(EditReminderActivity.this, ViewRemindersActivity.class);
            startActivity(intent);
        });

        saveReminderButton.setOnClickListener(v -> {
            String item = reminderText.getText().toString();
            if (!item.equals("")){
                mainController.updateReminder(EditReminderActivity.this, selectedID, item);
                Intent intent = new Intent(EditReminderActivity.this, ViewRemindersActivity.class);
                startActivity(intent);
            }
        });

        deleteReminderButton.setOnClickListener(v -> {
            mainController.deleteReminder(EditReminderActivity.this, selectedID);
            reminderText.setText("");
            Intent intent = new Intent(EditReminderActivity.this, ViewRemindersActivity.class);
            startActivity(intent);
        });
    }
}

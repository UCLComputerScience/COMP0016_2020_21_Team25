package com.example.fisev2concierge.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.R;
import com.example.fisev2concierge.controllers.MainController;

public class EditReminderActivity extends AppCompatActivity {

    private Button deleteReminderButton, saveReminderButton, backButton;
    private EditText reminderText;
    private String selectedReminder = "";
    private int selectedID;
    private MainController mainController = new MainController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reminder);
        deleteReminderButton = findViewById(R.id.deleteReminderButton);
        saveReminderButton = findViewById(R.id.saveReminderButton);
        reminderText = findViewById(R.id.editReminderText);
        backButton = findViewById(R.id.backButtonEditReminder);

        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("ID", -1);
        selectedReminder = receivedIntent.getStringExtra("Reminder");

        reminderText.setText(selectedReminder);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditReminderActivity.this, ViewRemindersActivity.class);
                startActivity(intent);
            }
        });

        saveReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = reminderText.getText().toString();
                if (!item.equals("")){
                    mainController.updateReminder(EditReminderActivity.this, selectedReminder, selectedID, item);
                    Intent intent = new Intent(EditReminderActivity.this, ViewRemindersActivity.class);
                    startActivity(intent);
                }
            }
        });

        deleteReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainController.deleteReminder(EditReminderActivity.this, selectedID, selectedReminder);
                reminderText.setText("");
                Intent intent = new Intent(EditReminderActivity.this, ViewRemindersActivity.class);
                startActivity(intent);
            }
        });
    }
}

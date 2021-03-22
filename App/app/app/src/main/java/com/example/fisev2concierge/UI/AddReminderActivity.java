package com.example.fisev2concierge.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.R;
import com.example.fisev2concierge.controllers.MainController;

public class AddReminderActivity extends AppCompatActivity {

    private Button addReminderButton, backButton;
    private EditText reminderText;
    MainController mainController = new MainController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        reminderText = findViewById(R.id.reminderText);
        addReminderButton = findViewById(R.id.addReminderButton);
        backButton = findViewById(R.id.backButtonAddReminder);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddReminderActivity.this, ViewRemindersActivity.class);
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
                    Intent intent = new Intent(AddReminderActivity.this, ViewRemindersActivity.class);
                    startActivity(intent);
                } else {
                    toastMessage("Field cannot be empty");
                }

            }
        });
    }

    public void AddData(String newEntry){
        boolean insertData = mainController.addReminder(AddReminderActivity.this, newEntry);
        if (insertData){
            toastMessage("Data entered successfully");
        } else {
            toastMessage("Data not entered successfully");
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
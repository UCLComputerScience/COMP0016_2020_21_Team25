package com.example.fisev2concierge.UI;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.R;
import com.example.fisev2concierge.controllers.MainController;

import java.util.ArrayList;

public class ViewRemindersActivity extends AppCompatActivity {

    private ListView listView;
    private Button backButton, newReminderButton;
    private MainController mainController = new MainController();
    private ArrayList<String> remindersDbIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reminders);

        listView = findViewById(R.id.remindersListView);
        backButton = findViewById(R.id.backButtonViewReminders);
        newReminderButton = findViewById(R.id.addNewReminder);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewRemindersActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        newReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewRemindersActivity.this, AddReminderActivity.class);
                startActivity(intent);
            }
        });

        populateListView();
    }

    private void populateListView(){
        Cursor data = mainController.getReminders(ViewRemindersActivity.this);
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()){
            remindersDbIds.add(data.getString(0));
            listData.add(data.getString(1));
        }

        ListAdapter adapter = new ArrayAdapter<>(this, R.layout.listview_config, listData);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String reminder = parent.getItemAtPosition(position).toString();
                int itemID = -1;
                try {
                    itemID = Integer.parseInt(remindersDbIds.get(position));
                } catch (Exception e){
                    e.printStackTrace();
                }
                if (itemID != -1){
                    Intent editReminderIntent = new Intent(ViewRemindersActivity.this, EditReminderActivity.class).putExtra("ID", itemID).putExtra("Reminder", reminder);
                    startActivity(editReminderIntent);
                }
            }
        });
    }
}

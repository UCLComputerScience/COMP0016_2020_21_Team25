package com.example.fisev2concierge;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.controllers.MainController;

import java.util.ArrayList;

public class ViewRemindersView extends AppCompatActivity {

    private static final String TAG = "ViewRemindersView";
    private ListView listView;
    private Button backButton, newReminderButton;
    private MainController mainController = new MainController();
    private ArrayList<String> remindersDbIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_reminders_view);

        listView = findViewById(R.id.remindersListView);
        backButton = findViewById(R.id.backButton);
        newReminderButton = findViewById(R.id.addNewReminder);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewRemindersView.this, MainActivity.class);
                startActivity(intent);
            }
        });

        newReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewRemindersView.this, AddReminderView.class);
                startActivity(intent);
            }
        });

        populateListView();
    }

    private void populateListView(){
        Log.d("ViewRemindersView", "populateListView: Displaying data in the ListView");

        Cursor data = mainController.getReminders(ViewRemindersView.this);
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()){
            remindersDbIds.add(data.getString(0));
            listData.add(data.getString(1));
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String reminder = parent.getItemAtPosition(position).toString();
                String idGet = "";
                for (char c: reminder.toCharArray()){
                    if (c == ':'){
                        break;
                    } else {
                        idGet += c;
                    }
                }
                Log.d(TAG, "onItemClick: You clicked on " + reminder);

                int itemID = -1;
                try {
                    itemID = Integer.parseInt(remindersDbIds.get(position));
                } catch (Exception e){
                    e.printStackTrace();
                }
//                String cleanedReminder = "";
//                Boolean cleaned = false;
//                for (char c: reminder.toCharArray()){
//                    if (c == ':' && cleaned){
//                        break;
//                    }
//                    else if (c == ':'){
//                        cleanedReminder = "";
//                        cleaned = true;
//                    }
//                    else {
//                        cleanedReminder += c;
//                    }
//                }
//                reminder = cleanedReminder;
                if (itemID != -1){
                    Log.d(TAG, "onItemClick: The ID is " + itemID);
                    Intent editReminderIntent = new Intent(ViewRemindersView.this, EditReminderView.class);
                    editReminderIntent.putExtra("ID", itemID);
                    editReminderIntent.putExtra("Reminder", reminder);
                    startActivity(editReminderIntent);
                }
            }
        });
    }
}

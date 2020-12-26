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

import com.example.fisev2concierge.model.DbHelper;

import java.util.ArrayList;

public class ViewRemindersView extends AppCompatActivity {

    private static final String TAG = "ViewRemindersView";
    DbHelper dbHelper;
    private ListView listView;
    private Button backButton, newReminderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_reminders_view);

        listView = findViewById(R.id.remindersListView);
        dbHelper = new DbHelper(this);
        backButton = findViewById(R.id.backButton);
        newReminderButton = findViewById(R.id.addReminderButton);

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

        Cursor data = dbHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()){
            listData.add(data.getString(0) + ": " + data.getString(1)+ ": " + data.getString(2));
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
                //need a better way of getting item id
//                Cursor data = dbHelper.getItemID(idGet);
//                int itemID = -1;
//                while (data.moveToNext()){
//                    itemID = data.getInt(0);
//                }
                int itemID = -1;
                try {
                    itemID = Integer.parseInt(idGet);
                } catch (Exception e){
                    e.printStackTrace();
                }
                String cleanedReminder = "";
                Boolean cleaned = false;
                for (char c: reminder.toCharArray()){
                    if (c == ':' && cleaned){
                        break;
                    }
                    else if (c == ':'){
                        cleanedReminder = "";
                        cleaned = true;
                    }
                    else {
                        cleanedReminder += c;
                    }
                }
                reminder = cleanedReminder;
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

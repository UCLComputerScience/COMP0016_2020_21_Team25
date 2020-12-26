package com.example.fisev2concierge;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.model.DbHelper;

import java.util.ArrayList;

public class ReminderList extends AppCompatActivity {

    private static final String TAG = "ReminderList";
    DbHelper dbHelper;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_list);

        listView = findViewById(R.id.remindersListView);
        dbHelper = new DbHelper(this);

        populateListView();
    }

    private void populateListView(){
        Log.d("ReminderList", "populateListView: Displaying data in the ListView");

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
                    Intent editReminderIntent = new Intent(ReminderList.this, EditReminder.class);
                    editReminderIntent.putExtra("ID", itemID);
                    editReminderIntent.putExtra("Reminder", reminder);
                    startActivity(editReminderIntent);
                }
            }
        });
    }
}

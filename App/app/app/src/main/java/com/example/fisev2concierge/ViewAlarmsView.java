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

import com.example.fisev2concierge.controller.MainController;
import com.example.fisev2concierge.model.AlarmsDbHelper;

import java.util.ArrayList;

public class ViewAlarmsView extends AppCompatActivity {

    private static final String TAG = "ViewAlarmsView";
    private ListView listView;
    private Button backButton;
    private MainController mainController = new MainController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_alarms_view);

        listView = findViewById(R.id.alarmsListView);
        backButton = findViewById(R.id.backButton);
        Button newAlarmButton = findViewById(R.id.addNewAlarm);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAlarmsView.this, MainActivity.class);
                startActivity(intent);
            }
        });

        newAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAlarmsView.this, AddAlarmView.class);
                startActivity(intent);
            }
        });

        populateListView();
    }

    private void populateListView(){
        Log.d("ViewAlarmsView", "populateListView: Displaying data in the ListView");

        Cursor data = mainController.getAlarm(ViewAlarmsView.this);
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()){
            listData.add(data.getString(0) + ": " + data.getString(1)+ ": " + data.getString(2));
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String alarm = parent.getItemAtPosition(position).toString();
                String idGet = "";
                for (char c: alarm.toCharArray()){
                    if (c == ':'){
                        break;
                    } else {
                        idGet += c;
                    }
                }
                Log.d(TAG, "onItemClick: You clicked on " + alarm);

                int itemID = -1;
                try {
                    itemID = Integer.parseInt(idGet);
                } catch (Exception e){
                    e.printStackTrace();
                }
                String cleanedAlarm = "";
                Boolean cleaned = false;
                for (char c: alarm.toCharArray()){
                    if (c == ':' && cleaned){
                        break;
                    }
                    else if (c == ':'){
                        cleanedAlarm = "";
                        cleaned = true;
                    }
                    else {
                        cleanedAlarm += c;
                    }
                }
                alarm = cleanedAlarm;
                if (itemID != -1){
                    Log.d(TAG, "onItemClick: The ID is " + itemID);
                    Intent editAlarmIntent = new Intent(ViewAlarmsView.this, EditAlarmView.class);
                    editAlarmIntent.putExtra("ID", itemID);
                    editAlarmIntent.putExtra("Alarm", alarm);
                    startActivity(editAlarmIntent);
                }
            }
        });
    }
}

package com.example.fisev2concierge.views;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.R;
import com.example.fisev2concierge.controllers.MainController;

import java.util.ArrayList;

public class ViewAlarmsActivity extends AppCompatActivity {

    private ListView listView;
    private final MainController mainController = new MainController();
    private final ArrayList<String> alarmsDbIds = new ArrayList<>();
    private final ArrayList<String> alarmsDbMessages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_alarms);

        listView = findViewById(R.id.alarmsListView);
        Button backButton = findViewById(R.id.backButtonViewAlarm);
        Button newAlarmButton = findViewById(R.id.addNewAlarm);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ViewAlarmsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        newAlarmButton.setOnClickListener(v -> {
            Intent intent = new Intent(ViewAlarmsActivity.this, AddAlarmActivity.class);
            startActivity(intent);
        });

        populateListView();
    }

    private void populateListView(){

        Cursor data = mainController.getAlarm(ViewAlarmsActivity.this);
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()){
            alarmsDbIds.add(data.getString(0));
            alarmsDbMessages.add(data.getString(1));
            listData.add(data.getString(1)+ " - Date and Time: " + data.getString(2));
        }

        ListAdapter adapter = new ArrayAdapter<>(this, R.layout.listview_config, listData);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String alarm = alarmsDbMessages.get(position);
            int itemID = -1;
            try {
                itemID = Integer.parseInt(alarmsDbIds.get(position));
            } catch (Exception e){
                e.printStackTrace();
            }
            if (itemID != -1){
                Intent editAlarmIntent = new Intent(ViewAlarmsActivity.this, EditAlarmActivity.class).putExtra("ID", itemID).putExtra("Alarm", alarm);
                startActivity(editAlarmIntent);
            }
        });
    }
}

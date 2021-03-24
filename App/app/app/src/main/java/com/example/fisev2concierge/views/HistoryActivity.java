package com.example.fisev2concierge.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.R;
import com.example.fisev2concierge.controllers.MainController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private final MainController mainController = new MainController();
    private ListView historyListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Button backButton = findViewById(R.id.backButtonHistory);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
            startActivity(intent);
        });

        historyListView = findViewById(R.id.historyListView);
        populateListView();
    }

    private void populateListView(){
        ArrayList<String> listData = new ArrayList<>();
        if (mainController.hasUserID(HistoryActivity.this)) {
            fetchAndAddData(listData);
        } else {
            listData.add("Not connected to an admin - no history to display");
        }
        ListAdapter adapter = new ArrayAdapter<>(this, R.layout.listview_config, listData);
        historyListView.setAdapter(adapter);
    }
    
    private void fetchAndAddData(ArrayList<String> listData){
        ArrayList<String> history = mainController.backendServices("getHistory", mainController.getUserID(HistoryActivity.this), HistoryActivity.this);
        String json = history.get(0);
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("history");
            addData(listData, jsonArray);
        } catch (Exception e){
            Toast.makeText(HistoryActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addData(ArrayList<String> listData, JSONArray jsonArray) throws Exception{
        if (jsonArray.length() == 0) {
            listData.add("No history to display");
        } else {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject newJsonObject = new JSONObject(jsonArray.get(i).toString());
                String service = newJsonObject.getString("service_name");
                String date = newJsonObject.getString("timestamp");
                listData.add("Service: " + service + "\nDate & Time: " + date);
            }
        }
    }
}
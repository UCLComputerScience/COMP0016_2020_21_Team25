package com.example.fisev2concierge.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.R;
import com.example.fisev2concierge.controllers.MainController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    MainController mainController = new MainController();
    ListView historyListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Button backButton = findViewById(R.id.backButtonHistory);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

//        Button temp = findViewById(R.id.askBobOpenApp);
//        temp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!mainController.hasUserID(HistoryActivity.this)){
//                    mainController.addUserID(HistoryActivity.this, "101");
//                }
////                String number = mainController.searchContact("Gp", HistoryActivity.this, HistoryActivity.this, HistoryActivity.this);
////                System.out.println("number: " + number);
//            }
//        });

        historyListView = findViewById(R.id.historyListView);
        populateListView();
    }

    private void populateListView(){
        ArrayList<String> listData = new ArrayList<>();
        if (mainController.hasUserID(HistoryActivity.this)) {
            ArrayList<String> history = mainController.backendServices("getHistory", mainController.getUserID(HistoryActivity.this), HistoryActivity.this);
            String json = history.get(0);
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("history");
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
            } catch (Exception e){
                Toast.makeText(HistoryActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            listData.add("Not connected to an admin - no history to display");
        }
        ListAdapter adapter = new ArrayAdapter<>(this, R.layout.listview_config, listData);
        historyListView.setAdapter(adapter);
    }
}
package com.example.fisev2concierge.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fisev2concierge.R;

public class InstructionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        Button backButton = findViewById(R.id.backButtonInstructions);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(InstructionActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

}
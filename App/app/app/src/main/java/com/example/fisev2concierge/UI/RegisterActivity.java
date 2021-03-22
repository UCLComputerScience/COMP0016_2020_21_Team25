package com.example.fisev2concierge.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Toast;

import com.example.fisev2concierge.R;
import com.example.fisev2concierge.controllers.MainController;
import com.example.fisev2concierge.speech.SpeechSynthesis;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SpeechSynthesis speechSynthesis = new SpeechSynthesis();
        speechSynthesis.configTts(this);

        TextInputEditText code1Input = findViewById(R.id.registrationCodeText1);
        TextInputEditText code2Input = findViewById(R.id.registrationCodeText2);
        TextInputEditText code3Input = findViewById(R.id.registrationCodeText3);
        Button submit = findViewById(R.id.submitRegistrationCode);
        Button back = findViewById(R.id.backButtonRegister);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code1 = String.valueOf(code1Input.getText());
                String code2 = String.valueOf(code2Input.getText());
                String code3 = String.valueOf(code3Input.getText());
                String parameter = code1+"&second_word="+code2+"&last_word="+code3;
                if (!code1.isEmpty() && !code2.isEmpty() && !code3.isEmpty()){
                    MainController mainController = new MainController();
                    ArrayList<String> result = mainController.backendServices("register", parameter, RegisterActivity.this);
                    String json = result.get(0);
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String message = jsonObject.getString("message");
                        if (message.equals("OK")){
                            String userID = jsonObject.getString("userId");
                            mainController.addUserID(RegisterActivity.this, userID);
                            Toast.makeText(RegisterActivity.this, "Successfully connected to admin!", Toast.LENGTH_SHORT).show();
                            speechSynthesis.runTts("Successfully connected to admin!");
                        } else {
                            Toast.makeText(RegisterActivity.this, "Incorrect validation codes", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(RegisterActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Validation codes cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
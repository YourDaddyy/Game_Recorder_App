package com.example.projectscandium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddGame extends AppCompatActivity {

    private EditText player, score;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        setTitle("Add Game");

        setupSaveBtn();
    }

    private void setupSaveBtn() {
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save data, calculate level and save into class
            }
        });
    }

    //Create Intent Portal
    public static Intent makeIntent(Context context, int gamePos){
        Intent intent = new Intent(context, AddGame.class);
        return intent;
    }


}
package com.example.projectscandium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.projectscandium.model.ConfigManager;
import com.example.projectscandium.model.Configs;
import com.example.projectscandium.model.Game;

public class AddGame extends AppCompatActivity {

    private EditText rtPlayer, etScore;
    private Button btnSave;
    private int players, scores, level;
    private int position;

    //Singleton the game list
    private ConfigManager cm = ConfigManager.getInstance();
    private Configs config = cm.getConfigById(0);
    private static final String EXTRA_POS = "com.example.projectscandium.AddGame - the gamePos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        setTitle("Add Game");

        setupSaveBtn();
    }

    // extract edit game index
    private void extractDataFromIntent() {
        Intent intent = getIntent();
        position = intent.getIntExtra(EXTRA_POS, -1);
    }

    //Create Intent Portal
    public static Intent makeIntent(Context context, int pos){
        Intent intent = new Intent(context, AddGame.class);
        intent.putExtra(EXTRA_POS, pos);
        return intent;
    }

    private void setupSaveBtn() {
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save data, calculate level and save into class
                String sPlayer = ((String)((EditText)findViewById(R.id.player)).getText().toString());
                players = Integer.parseInt(sPlayer);
                String sScore = ((String)((EditText)findViewById(R.id.point)).getText().toString());
                scores = Integer.parseInt(sScore);
                level = config.calculateLevel(players, scores);
                Game game = new Game(players, scores, level);
                config.addGame(game);
            }
        });
    }

}
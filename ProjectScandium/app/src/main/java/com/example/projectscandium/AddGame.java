package com.example.projectscandium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectscandium.model.ConfigManager;
import com.example.projectscandium.model.Configs;
import com.example.projectscandium.model.Game;

public class AddGame extends AppCompatActivity {

    private EditText etPlayer, etScore;
    private int players, scores;
    private int gamePos, configPos;
    Button btnDelete, btnSave;

    //Singleton the game list
    private ConfigManager cm = ConfigManager.getInstance();
    private Configs config;
    private static final String CONFIG_POS = "com.example.projectscandium.AddGame - the config pos";
    private static final String GAME_POS = "com.example.projectscandium.AddGame - the gamePos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        extractDataFromIntent();
        config = cm.getConfigById(configPos);
        setTitle("Add Game");
        if(gamePos != -1){setTitle(getString(R.string.editGame_title));}

        //Enable return btn
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        setupPage();

    }

    // extract edit game index
    private void extractDataFromIntent() {
        Intent intent = getIntent();
        configPos = intent.getIntExtra(CONFIG_POS, 0);
        gamePos = intent.getIntExtra(GAME_POS, -1);
    }

    //Create Intent Portal
    public static Intent makeIntent(Context context, int configPos, int gamePos){
        Intent intent = new Intent(context, AddGame.class);
        intent.putExtra(CONFIG_POS, configPos);
        intent.putExtra(GAME_POS, gamePos);
        return intent;
    }

    //App Bar Controller, setup function of save and return btn
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            //setup return button
            case android.R.id.home: {
                checkReturn();
            }
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        checkReturn();
        super.onBackPressed();
    }

    private void checkReturn(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddGame.this);
        builder1.setIcon(null);
        builder1.setTitle("Return to Game List?");
        builder1.setMessage("Nothing is saved");
        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    };

    // set up Game Page
    private void setupPage() {
        btnDelete = (Button) findViewById(R.id.btnDelete);
        if(gamePos == -1){
            btnDelete.setVisibility(View.GONE);
        }else{
            btnDelete.setVisibility(View.VISIBLE);
            Game game = config.getGames().get(gamePos);
            etPlayer = (EditText) findViewById(R.id.player);
            etScore = (EditText) findViewById(R.id.score);
            etPlayer.setText("" + game.getPlayerNum());
            etScore.setText("" + game.getCombinedScore());
            setupDeleteBtn();
        }
        setupSaveBtn();
    }

    // set up delete button
    private void setupDeleteBtn() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(AddGame.this);
                builder.setIcon(null);
                builder.setTitle("Delete?");
                builder.setMessage("Are You Sure Want to Delete This Game?");
                //make Sure
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        config.getGames().remove(gamePos);
                    }
                });
                //Cancel
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
    }

    // set up save button
    private void setupSaveBtn() {
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValid()){
                    //Save data, calculate level and save into class
                    String sPlayer = ((String)((EditText)findViewById(R.id.player)).getText().toString());
                    players = Integer.parseInt(sPlayer);
                    String sScore = ((String)((EditText)findViewById(R.id.score)).getText().toString());
                    scores = Integer.parseInt(sScore);
                    checkSave();
                    Game game = new Game(players, scores);
                    config.addGame(game);
                }
            }
        });
    }

    private void checkSave(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddGame.this);
        builder.setIcon(null);
        builder.setTitle("Save Game?");
        builder.setMessage("Game will be saved.");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(gamePos == -1) {
                    Game newGame = new Game(players, scores);
                    config.getGames().add(newGame);
                }else {
                    config.getGames().get(gamePos).restoreGame(players, scores);
                }
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    //Check if the game is valid
    private boolean checkValid() {
        if(etPlayer.length() == 0){
            Toast.makeText(AddGame.this, "Players should not be empty",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if(etScore.length() == 0){
            Toast.makeText(AddGame.this, "Scores should not be empty",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
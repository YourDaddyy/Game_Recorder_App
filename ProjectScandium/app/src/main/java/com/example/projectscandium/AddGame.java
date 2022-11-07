package com.example.projectscandium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projectscandium.model.Achievements;
import com.example.projectscandium.model.ConfigManager;
import com.example.projectscandium.model.Configs;
import com.example.projectscandium.model.Game;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

public class AddGame extends AppCompatActivity {

    private int players, scores;
    private int gamePos, configPos;
    Button btnDelete;
    private String time;

    // Singleton the game list
    private final ConfigManager cm = ConfigManager.getInstance();
    private Configs config;
    private static final String CONFIG_POS = "com.example.projectscandium.AddGame - the config pos";
    private static final String GAME_POS = "com.example.projectscandium.AddGame - the gamePos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        // get config and game data
        extractDataFromIntent();

        // get config instance
        config = cm.getConfigById(configPos);

        // set title
        setTitle("Game: " + (config.getGameNum() + 1));
        time = setTime();
        TextView tvTime = findViewById(R.id.Time);
        tvTime.setText(time);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if (gamePos != -1) {
            setTitle(getString(R.string.editGame_title));
        }

        setupPage();

    }

    // extract edit game index
    private void extractDataFromIntent() {
        Intent intent = getIntent();
        configPos = intent.getIntExtra(CONFIG_POS, 0);
        gamePos = intent.getIntExtra(GAME_POS, -1);
    }

    // Create Intent Portal
    public static Intent makeIntent(Context context, int configPos, int gamePos) {
        Intent intent = new Intent(context, AddGame.class);
        intent.putExtra(CONFIG_POS, configPos);
        intent.putExtra(GAME_POS, gamePos);
        return intent;
    }

    // App Bar Controller, setup function of save and return btn
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // setup return button
        if (item.getItemId() == android.R.id.home) {
            checkReturn();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        checkReturn();
    }

    private void checkReturn() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddGame.this);
        builder1.setIcon(null);
        builder1.setTitle("Return to Game List?");
        builder1.setMessage("Nothing is saved yet.\nIf you still wish to return press yes!");
        builder1.setPositiveButton("Yes", (dialog, which) -> finish());
        builder1.setNegativeButton("No", (dialog, which) -> dialog.dismiss()).show();
    }

    // set up Game Page
    private void setupPage() {
        btnDelete = findViewById(R.id.btnDelete);
        if (gamePos == -1) {
            btnDelete.setVisibility(View.GONE);
        } else {
            btnDelete.setVisibility(View.VISIBLE);
            Game game = config.getGames().get(gamePos);
            EditText etPlayer = findViewById(R.id.player);
            EditText etScore = findViewById(R.id.score);
            etPlayer.setText(getString(R.string.addPlayer, game.getPlayerNum()));
            etScore.setText(getString(R.string.addScore, game.getCombinedScore()));
            setupDeleteBtn();
        }
    }

    // set up delete button
    private void setupDeleteBtn() {
        btnDelete.setOnClickListener(view -> {
            // create dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(AddGame.this);
            builder.setIcon(null);
            builder.setTitle("Delete?");
            builder.setMessage("Are You Sure Want to Delete This Game?");
            // make Sure
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                config.deleteGame(gamePos);
                finish();
            });
            // Cancel
            builder.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss()).show();
        });
    }

    // Set Game Created Time
    private String setTime() {
        LocalDate localDate = LocalDate.now();

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MMM dd");
        String Date = formatter1.format(localDate);

        LocalTime localTime = LocalTime.now();

        DateTimeFormatter formatter6 = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        String Time = formatter6.format(localTime);
        return Date + " @ " + Time;
    }

    private void checkSave() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddGame.this);
        builder.setIcon(null);
        builder.setTitle("Save Game?");
        builder.setMessage("Game will be saved.");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            Game game = new Game(players, scores, time);
            Achievements achievements = new Achievements();
            achievements.setScoreBounds(config.getPoorExpectedScore(), config.getGreatExpectedScore(), players);
            // set the achievement for the game
            game.setAchievements(achievements);
            config = cm.getConfigById(configPos);
            if(gamePos == -1){// get the config instance
                config.addGame(game);
            }else{
                config.getGames().set(gamePos, game);
                game.setAchievements(achievements);
            }
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss()).show();
    }

    // Check if the game is valid
    private boolean checkValid() {
        EditText etP = findViewById(R.id.player);
        EditText etS = findViewById(R.id.score);
        if (etP.length() == 0) {
            etP.setError("Players should not be empty");
            return false;
        }
        if (etS.length() == 0) {
            etS.setError("Scores should not be empty");
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_bar_item, menu);
        // on click listener for the save button
        MenuItem saveButton = menu.findItem(R.id.saveButton);
        // on click listener for the save button
        saveButton.setOnMenuItemClickListener(item -> {
            if (checkValid()) {
                // Save data into class
                String sPlayer = ((EditText) findViewById(R.id.player)).getText().toString();
                players = Integer.parseInt(sPlayer);
                String sScore = ((EditText) findViewById(R.id.score)).getText().toString();
                scores = Integer.parseInt(sScore);
                checkSave();
            }
            return true;
        });
        return true;
    }

}
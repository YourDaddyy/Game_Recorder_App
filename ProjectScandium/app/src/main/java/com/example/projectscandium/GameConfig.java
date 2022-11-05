package com.example.projectscandium;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.projectscandium.model.ConfigManager;
import com.example.projectscandium.model.Configs;

import java.util.Objects;

public class GameConfig extends AppCompatActivity {

    // get the game config manager
    ConfigManager configManager;
    Configs config;

    String name, upperScore, lowerScore, mode;
    boolean emptyName, upperScoreCheck, lowerScoreCheck;

    EditText nameBox, upperScoreBox, lowerScoreBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_config);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Get intent of the activity
        Intent intent = getIntent();
        // Get the game mode
        mode = intent.getStringExtra("mode");

        if (Objects.equals(mode, "NewConfig")){
            getSupportActionBar().setTitle("New Game Configuration");
        }else if (Objects.equals(mode, "EditConfig")){
            getSupportActionBar().setTitle("Edit Game Configuration");
        }

        // listen to up button
        toolbar.setNavigationOnClickListener(v -> finish());

        emptyName = false;
        upperScoreCheck = false;
        lowerScoreCheck = false;

        // get the game config manager
        configManager = ConfigManager.getInstance();

        // get the name box
        nameBox = findViewById(R.id.configNameBox);
        upperScoreBox = findViewById(R.id.UpperScoreBox);
        lowerScoreBox = findViewById(R.id.LowerScoreBox);

        // get the extra info from the intent
        int index = getIntent().getIntExtra("configIndex", -1);
        // check if a extra was passed
        if (index == -1) {
            // if not, create a new config
            config = new Configs();
        } else {
            config = configManager.getConfigById(index);
            // set the values of the boxes
            nameBox.setText(config.getGameConfigName());
            upperScoreBox.setText(String.valueOf(config.getGreatExpectedScore()));
            lowerScoreBox.setText(String.valueOf(config.getPoorExpectedScore()));
        }

        // on text changed listener for name box
        nameBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                config.setGameConfigName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // get the value of the name box
                name = nameBox.getText().toString();
                // if empty throw an error
                if (name.isEmpty()) {
                    nameBox.setError("Name cannot be empty");
                    emptyName = true;
                } else {
                    emptyName = false;
                }
            }
        });

        // on text changed listener for upper score box
        upperScoreBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // get the value of the upper score box
                try {
                    upperScore = upperScoreBox.getText().toString();
                    if (Integer.parseInt(upperScore) < 0) {
                        upperScoreBox.setError("Score cannot be negative");
                        upperScoreCheck = true;
                    } else {
                        upperScoreBox.setError(null);
                        upperScoreCheck = false;
                    }
                } catch (NumberFormatException e) {
                    upperScoreBox.setError("Score must be a number");
                    upperScoreCheck = true;
                }
            }
        });

        // on text changed listener for lower score box
        lowerScoreBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // get the value of the lower score box
                try {
                    lowerScore = lowerScoreBox.getText().toString();
                    if (Integer.parseInt(lowerScore) < 0) {
                        lowerScoreBox.setError("Score cannot be negative");
                        lowerScoreCheck = true;
                    } else {
                        lowerScoreBox.setError(null);
                        lowerScoreCheck = false;
                    }
                } catch (NumberFormatException ex) {
                    // set an error message based on the exception
                    lowerScoreBox.setError("Please enter a valid number");
                    lowerScoreCheck = true;
                }
            }
        });

        // when the save button is clicked
        Button saveButton = findViewById(R.id.saveConfig);
        saveButton.setOnClickListener(view -> {

            emptyName = checkEmpty(nameBox);
            upperScoreCheck = checkEmpty(upperScoreBox);
            lowerScoreCheck = checkEmpty(lowerScoreBox);

            if (emptyName && upperScoreCheck && lowerScoreCheck) {
                // create a dialog box to confirm the save
                AlertDialog.Builder confirmSaveDialog = new AlertDialog.Builder(this);
                confirmSaveDialog.setTitle("Do you wish to save?");
                confirmSaveDialog.setMessage("Are you sure you want to save these values?");
                confirmSaveDialog.setCancelable(true);
                confirmSaveDialog.setPositiveButton("Yes", (dialog, which) -> {
                    try {
                        // set the values of the config
                        config.setGameConfigName(nameBox.getText().toString());
                        config.setGreatExpectedScore(Integer.parseInt(upperScoreBox.getText().toString()));
                        config.setPoorExpectedScore(Integer.parseInt(lowerScoreBox.getText().toString()));
                        // add the config to the config manager
                        configManager.addConfig(config);
                        // finish the activity
                        finish();
                    } catch (NumberFormatException e) {
                        // set an error message based on the exception
                        lowerScoreBox.setError("Please enter a valid number");
                        lowerScoreCheck = true;
                    }
                });
                confirmSaveDialog.setNegativeButton("No", (dialog, which) -> {
                    // do nothing
                });
                AlertDialog dialog = confirmSaveDialog.create();
                dialog.show();
            } else {
                // display toast
                Toast toast = Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        // when the delete button is clicked
        Button deleteButton = findViewById(R.id.deleteConfig);
        deleteButton.setOnClickListener(view -> {
            if (index != -1) {
                // create a dialog box to confirm the delete
                AlertDialog.Builder confirmDeleteDialog = new AlertDialog.Builder(this);
                confirmDeleteDialog.setTitle("Do you wish to delete?");
                confirmDeleteDialog.setMessage("Are you sure you want to delete this configuration?");
                confirmDeleteDialog.setCancelable(true);
                confirmDeleteDialog.setPositiveButton("Yes", (dialog, which) -> {
                    // remove the config from the config manager
                    configManager.removeConfigById(index);
                    finish();
                });
                confirmDeleteDialog.setNegativeButton("No", (dialog, which) -> {
                    // do nothing
                });
                AlertDialog dialog = confirmDeleteDialog.create();
                dialog.show();
            } else {
                finish();
            }
        });

        Button achievementButton = findViewById(R.id.achConfig);
        // listener for the achievement button
        achievementButton.setOnClickListener(view -> {
            Intent new_intent = new Intent(this, AchievementsPage.class);
            startActivity(new_intent);
        });

    }

    private boolean checkEmpty(EditText BoxId) {
        if (BoxId.getText().toString().isEmpty()) {
            // if any of the boxes are empty, throw an error
            if (BoxId == nameBox) {
                nameBox.setError("Name cannot be empty");
            } else if (BoxId == upperScoreBox) {
                upperScoreBox.setError("Score cannot be negative");
            } else if (BoxId == lowerScoreBox) {
                lowerScoreBox.setError("Score cannot be negative");
            }
            return false;
        } else {
            return true;
        }
    }
}
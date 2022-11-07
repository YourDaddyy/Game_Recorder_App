package com.example.projectscandium;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    private ConfigManager configManager;
    private Configs config;

    private String name, upperScore, lowerScore, mode;
    private boolean emptyName, upperScoreCheck, lowerScoreCheck, lowerScoreDiff;

    EditText nameBox, upperScoreBox, lowerScoreBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_config);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        // listen to up button
        toolbar.setNavigationOnClickListener(v -> checkReturn());

        lowerScoreDiff = false;

        // Get intent of the activity
        Intent intent = getIntent();
        // Get the game mode
        mode = intent.getStringExtra("mode");

        if (Objects.equals(mode, "NewConfig")) {
            getSupportActionBar().setTitle("New Game Configuration");
        } else if (Objects.equals(mode, "EditConfig")) {
            getSupportActionBar().setTitle("Edit Game Configuration");
        }

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
                    if (upperScore.isEmpty()) {
                        upperScoreBox.setError("Score cannot be empty");
                        upperScoreCheck = true;
                    } else {
                        if (Integer.parseInt(upperScore) < 0) {
                            upperScoreBox.setError("Score cannot be negative");
                            upperScoreCheck = true;
                        } else if (lowerScoreDiff) {
                            if (Integer.parseInt(lowerScoreBox.getText().toString()) > Integer
                                    .parseInt(upperScoreBox.getText().toString())) {
                                lowerScoreDiff = true;
                                lowerScoreBox.setError("Lower Score can't be less than Upper Score");
                            } else {
                                lowerScoreBox.setError(null);
                            }
                        } else {
                            upperScoreBox.setError(null);
                            upperScoreCheck = false;
                        }
                    }
                } catch (NumberFormatException e) {
                    upperScoreBox.setError("Please enter a valid number");
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
                    if (lowerScore.isEmpty()) {
                        lowerScoreBox.setError("Score cannot be empty");
                        lowerScoreCheck = true;
                    } else {
                        if (Integer.parseInt(lowerScore) < 0) {
                            lowerScoreBox.setError("Score cannot be negative");
                            lowerScoreCheck = true;
                        } else if (lowerScoreDiff) {
                            if (Integer.parseInt(lowerScoreBox.getText().toString()) > Integer
                                    .parseInt(upperScoreBox.getText().toString())) {
                                lowerScoreDiff = true;
                                lowerScoreBox.setError("Lower Score can't be less than Upper Score");
                            } else {
                                lowerScoreBox.setError(null);
                            }
                        } else {
                            lowerScoreBox.setError(null);
                            lowerScoreCheck = false;
                        }
                    }
                } catch (NumberFormatException ex) {
                    // set an error message based on the exception
                    lowerScoreBox.setError("Please enter a valid number");
                    lowerScoreCheck = true;
                }
            }
        });

        // when the delete button is clicked
        Button deleteButton = findViewById(R.id.deleteConfig);
        if (Objects.equals(mode, "NewConfig")) {
            deleteButton.setVisibility(View.GONE);
        } else if (Objects.equals(mode, "EditConfig")) {
            deleteButton.setVisibility(View.VISIBLE);
        }
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
        if (Objects.equals(mode, "NewConfig")) {
            achievementButton.setVisibility(View.GONE);
        } else if (Objects.equals(mode, "EditConfig")) {
            achievementButton.setVisibility(View.VISIBLE);
        }
        // listener for the achievement button
        achievementButton.setOnClickListener(view -> {
            Intent new_intent = new Intent(this, AchievementsPage.class);
            new_intent.putExtra("configIndex", index);
            startActivity(new_intent);
        });

    }

    private boolean checkEmpty(EditText BoxId) {
        if (BoxId.getText().toString().isEmpty()) {
            // if any of the boxes are empty, throw an error
            if (BoxId == nameBox) {
                nameBox.setError("Name cannot be empty");
            } else if (BoxId == upperScoreBox) {
                upperScoreBox.setError("Score cannot be empty");
            } else if (BoxId == lowerScoreBox) {
                lowerScoreBox.setError("Score cannot be empty");
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        checkReturn();
    }

    private void checkReturn() {
        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(GameConfig.this);
        builder1.setIcon(null);
        builder1.setTitle("Return to Config List?");
        builder1.setMessage("Nothing is saved yet.\nIf you still wish to return press yes!");
        builder1.setPositiveButton("Yes", (dialog, which) -> finish());
        builder1.setNegativeButton("No", (dialog, which) -> dialog.dismiss()).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_bar_item, menu);
        // on click listener for the save button
        MenuItem saveButton = menu.findItem(R.id.saveButton);
        // on click listener for the save button
        saveButton.setOnMenuItemClickListener(item -> {
            SaveConfig();
            return true;
        });
        return true;
    }

    public void SaveConfig() {
        emptyName = checkEmpty(nameBox);
        upperScoreCheck = checkEmpty(upperScoreBox);
        lowerScoreCheck = checkEmpty(lowerScoreBox);

        if (Integer.parseInt(lowerScoreBox.getText().toString()) > Integer
                .parseInt(upperScoreBox.getText().toString())) {
            lowerScoreDiff = true;
            lowerScoreBox.setError("Lower Score can't be less than Upper Score");
            return;
        }

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
                    if (Objects.equals(mode, "NewConfig")) {
                        // add the config to the config manager
                        configManager.addConfig(config);
                    }
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
    }
}
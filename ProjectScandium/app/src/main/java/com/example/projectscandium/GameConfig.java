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

/*Class GameConfig
 * Purpose: This class is the activity that allows the user to create a new config. It also allows the user to edit an existing config
 */
public class GameConfig extends AppCompatActivity {

    // private variables to store the necessary information
    private ConfigManager configManager;
    private Configs config;

    private String name, upperScore, lowerScore, mode;
    private boolean emptyName, upperScoreCheck, lowerScoreCheck, lowerScoreDiff;

    EditText nameBox, upperScoreBox, lowerScoreBox;

    // onCreate method
    // Purpose: creates the activity, set the toolbar (including the title).
    // Extracts the user's input and stores it in the private variables
    // Returns: void
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
            getSupportActionBar().setTitle(R.string.NewConfigTitle);
        } else {
            getSupportActionBar().setTitle(R.string.EditConfigTitle);
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

        // TextWatcher
        // Purpose: listens to the text in the name box and checks if it is empty
        // Returns: void
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
                    nameBox.setError(getString(R.string.EmptyField));
                    emptyName = true;
                } else {
                    emptyName = false;
                }
            }
        });

        // TextWatcher
        // Purpose: listens to the text in the upper score box and checks if the value is valid
        // Returns: void
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
                        upperScoreBox.setError(getString(R.string.EmptyField));
                        upperScoreCheck = true;
                    } else {
                        if (Integer.parseInt(upperScore) < 0) {
                            upperScoreBox.setError(getString(R.string.ErrorNegativeScore));
                            upperScoreCheck = true;
                        } else if (lowerScoreDiff) {
                            if (Integer.parseInt(lowerScoreBox.getText().toString()) >= Integer
                                    .parseInt(upperScoreBox.getText().toString())) {
                                lowerScoreDiff = true;
                                lowerScoreBox.setError(getString(R.string.ErrorLowerNum));
                            } else {
                                lowerScoreBox.setError(null);
                            }
                        } else {
                            upperScoreBox.setError(null);
                            upperScoreCheck = false;
                        }
                    }
                } catch (NumberFormatException e) {
                    upperScoreBox.setError(getString(R.string.ErrorNum));
                    upperScoreCheck = true;
                }
            }
        });

        // TextWatcher
        // Purpose: listens to the text in the lower score box and checks if the value is valid
        // Returns: void
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
                        lowerScoreBox.setError(getString(R.string.EmptyField));
                        lowerScoreCheck = true;
                    } else {
                        if (Integer.parseInt(lowerScore) < 0) {
                            lowerScoreBox.setError(getString(R.string.ErrorNegativeScore));
                            lowerScoreCheck = true;
                        } else if (lowerScoreDiff) {
                            if (Integer.parseInt(lowerScoreBox.getText().toString()) >= Integer
                                    .parseInt(upperScoreBox.getText().toString())) {
                                lowerScoreDiff = true;
                                lowerScoreBox.setError(getString(R.string.ErrorLowerNum));
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
                    lowerScoreBox.setError(getString(R.string.ErrorNum));
                    lowerScoreCheck = true;
                }
            }
        });

        // When the delete button is clicked ask the user if they are sure
        // if yes, delete the config and return to the main activity otherwise do nothing
        Button deleteButton = findViewById(R.id.deleteConfig);
        if (Objects.equals(mode, "NewConfig")) {
            deleteButton.setVisibility(View.GONE);
        } else {
            deleteButton.setVisibility(View.VISIBLE);
        }
        deleteButton.setOnClickListener(view -> {
            if (index != -1) {
                // create a dialog box to confirm the delete
                AlertDialog.Builder confirmDeleteDialog = new AlertDialog.Builder(this);
                confirmDeleteDialog.setTitle(R.string.DeleteConfigTitle);
                confirmDeleteDialog.setMessage(R.string.DeleteConfigMessage);
                confirmDeleteDialog.setCancelable(true);
                confirmDeleteDialog.setPositiveButton(R.string.Yes, (dialog, which) -> {
                    // remove the config from the config manager
                    configManager.removeConfigById(index);
                    finish();
                });
                confirmDeleteDialog.setNegativeButton(R.string.No, (dialog, which) -> {
                    // do nothing
                });
                AlertDialog dialog = confirmDeleteDialog.create();
                dialog.show();
            } else {
                finish();
            }
        });

        // View possible achievements list by calling the activity if the button is pressed
        Button achievementButton = findViewById(R.id.achConfig);
        if (Objects.equals(mode, "NewConfig")) {
            achievementButton.setVisibility(View.GONE);
        } else {
            achievementButton.setVisibility(View.VISIBLE);
        }
        // listener for the achievement button
        achievementButton.setOnClickListener(view -> {
            Intent new_intent = new Intent(this, AchievementsPage.class);
            new_intent.putExtra("configIndex", index);
            startActivity(new_intent);
        });
    }

    // checkEmpty method
    // Purpose: checks if the boxes are empty and sets the error message
    // Returns: boolean
    private boolean checkEmpty(EditText BoxId) {
        if (BoxId.getText().toString().isEmpty()) {
            // if any of the boxes are empty, throw an error
            if (BoxId == nameBox) {
                nameBox.setError(getString(R.string.EmptyField));
            } else if (BoxId == upperScoreBox) {
                upperScoreBox.setError(getString(R.string.EmptyField));
            } else if (BoxId == lowerScoreBox) {
                lowerScoreBox.setError(getString(R.string.EmptyField));
            }
            return false;
        } else {
            return true;
        }
    }

    // onBackPress method
    // Purpose: call the checkReturn method when the back button is pressed to confirm return
    // Returns: void
    @Override
    public void onBackPressed() {
        checkReturn();
    }

    // checkReturn method
    // Purpose: check if the user wants to return to the main activity without saving the changes
    // Returns: void
    private void checkReturn() {
        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(GameConfig.this);
        builder1.setIcon(null);
        builder1.setTitle(R.string.ReturnToConfigListTitle);
        builder1.setMessage(R.string.ReturnMessage);
        builder1.setPositiveButton(R.string.Yes, (dialog, which) -> finish());
        builder1.setNegativeButton(R.string.No, (dialog, which) -> dialog.dismiss()).show();
    }

    // onCreateOptionsMenu method
    // Purpose: create the menu for the activity and inflate the menu with the save button
    // Returns: boolean
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

    // SaveConfig method
    // Purpose: save the config to the config manager when the save button is pressed,
    // and the values are valid
    // Returns: void
    public void SaveConfig() {
        emptyName = checkEmpty(nameBox);
        upperScoreCheck = checkEmpty(upperScoreBox);
        lowerScoreCheck = checkEmpty(lowerScoreBox);

        if (Integer.parseInt(lowerScoreBox.getText().toString()) >= Integer
                .parseInt(upperScoreBox.getText().toString())) {
            lowerScoreDiff = true;
            lowerScoreBox.setError(getString(R.string.ErrorLowerNum));
            return;
        }

        if (emptyName && upperScoreCheck && lowerScoreCheck) {
            // create a dialog box to confirm the save
            AlertDialog.Builder confirmSaveDialog = new AlertDialog.Builder(this);
            confirmSaveDialog.setTitle(R.string.SaveConfigTitle);
            confirmSaveDialog.setMessage(R.string.SaveConfigMessage);
            confirmSaveDialog.setCancelable(true);
            confirmSaveDialog.setPositiveButton(R.string.Yes, (dialog, which) -> {
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
                    lowerScoreBox.setError(getString(R.string.ErrorNum));
                    lowerScoreCheck = true;
                }
            });
            confirmSaveDialog.setNegativeButton(R.string.No, (dialog, which) -> {
                // do nothing
            });
            AlertDialog dialog = confirmSaveDialog.create();
            dialog.show();
        } else {
            // display toast
            Toast toast = Toast.makeText(getApplicationContext(), R.string.EmptyFieldToast, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
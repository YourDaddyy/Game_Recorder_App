package com.example.projectscandium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectscandium.model.Achievements;
import com.example.projectscandium.model.ConfigManager;
import com.example.projectscandium.model.Configs;
import com.example.projectscandium.model.Game;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

/*Class AddGame
 * Purpose: This class is the activity that allows the user to add a game to the list of games
 * in the config
 */
public class AddGame extends AppCompatActivity {

    // private variables to store the necessary information
    private int players, scores, diffLevel = 0, changeStatus = -1;
    private int gamePos, configPos;
    private int[] playerScore;
    private Button btnDelete;
    private String time;
    private TextView etPlayer;
    ArrayAdapter<String> adapter;

    // Singleton the game list
    private final ConfigManager cm = ConfigManager.getInstance();
    private Configs config;
    private static final String CONFIG_POS = "com.example.projectscandium.AddGame - the config pos";
    private static final String GAME_POS = "com.example.projectscandium.AddGame - the gamePos";

    // onCreate method
    // Purpose: creates the activity, set the toolbar (including the title).
    // Extracts the config position and game position.
    // Returns: void
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        // get config and game data
        extractDataFromIntent();

        setupPage();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createRadioButtons();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        setupTxtWatcher();
        setUpPlayBtn();

    }

    // Set up TxtWatcher Method
    // Purpose: Watch Player Num changed
    // Returns: void
    private void setupTxtWatcher() {
        etPlayer = findViewById(R.id.player);
        TextChange tcPlayer = new TextChange();
        etPlayer.addTextChangedListener(tcPlayer);
    }

    // TxtWatcher class
    // Purpose: default create txt watcher
    // Returns: void
    class TextChange implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            changeStatus = 1;
            if(etPlayer.length() > 0){
                players = Integer.parseInt(((String)((EditText)findViewById(R.id.player)).getText().toString()));
                playerScore = new int[players];

            }else{
                etPlayer.setError(getString(R.string.EmptyField));
            }
        }
    }

    // createRadioButtons method
    // Purpose: set up radio button for the difficulty level
    // Returns: void
    private void createRadioButtons() {
        RadioGroup group1 = (RadioGroup) findViewById(R.id.radio_diffLvl);

        String[] diff_level = getResources().getStringArray(R.array.difficulty_level);

        for (int i = 0; i < 3; i++) {
            RadioButton button1 = new RadioButton(this);
            button1.setTextSize(16);
            button1.setText(diff_level[i]);
            final int level = i;
            button1.setOnClickListener(v -> diffLevel = level);
            group1.addView(button1);
            if (gamePos == -1 && i == 0) {
                button1.setChecked(true);
            }else if(gamePos != -1 && i == config.getGames().get(gamePos).getDifficulty()){
                button1.setChecked(true);
            }
        }
    }

    // extractDataFromIntent method
    // Purpose: extracts the config position and game position from the intent
    // Returns: void
    private void extractDataFromIntent() {
        Intent intent = getIntent();
        configPos = intent.getIntExtra(CONFIG_POS, 0);
        gamePos = intent.getIntExtra(GAME_POS, -1);
    }

    // makeIntent method
    // Purpose: creates an intent to start the AddGame activity
    // returns: Intent
    public static Intent makeIntent(Context context, int configPos, int gamePos) {
        Intent intent = new Intent(context, AddGame.class);
        intent.putExtra(CONFIG_POS, configPos);
        intent.putExtra(GAME_POS, gamePos);
        return intent;
    }

    // onCreateOptionsMenu method
    // Purpose: creates the menu
    // Returns: boolean
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // setup return button
        if (item.getItemId() == android.R.id.home) {
            checkReturn();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // onBackPress method
    // Purpose: checks if the user presses the back button
    // Returns: void
    @Override
    public void onBackPressed() {
        checkReturn();
    }

    // checkReturn method
    // Purpose: checks if the user wants to return to the previous activity.
    // In this case: the GameList activity.
    // Returns: void
    private void checkReturn() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddGame.this);
        builder1.setIcon(null);
        builder1.setTitle(R.string.ReturnToGameListTitle);
        builder1.setMessage(R.string.ReturnMessage);
        builder1.setPositiveButton(R.string.Yes, (dialog, which) -> finish());
        builder1.setNegativeButton(R.string.No, (dialog, which) -> dialog.dismiss()).show();
    }

    // setupPage method
    // Purpose: sets up the page, including the buttons and the text fields
    // Returns: void
    private void setupPage() {
        config = cm.getConfigById(configPos);
        btnDelete = findViewById(R.id.btnDelete);
        Button playButton = findViewById(R.id.btnPlay);

        if (gamePos == -1) {
            // set up add title
            setTitle(R.string.GameNumberText + (config.getGameNum() + 1));
            // set up time
            time = setTime();
            TextView tvTime = findViewById(R.id.Time);
            tvTime.setText(time);
            btnDelete.setVisibility(View.GONE);
            playButton.setVisibility(View.GONE);
        } else {
            // set up edit tile
            setTitle(getString(R.string.editGame_title));
            btnDelete.setVisibility(View.VISIBLE);
            Game game = config.getGames().get(gamePos);
            EditText etPlayer = findViewById(R.id.player);
            EditText etScore = findViewById(R.id.score);
            etPlayer.setText(getString(R.string.addPlayer, game.getPlayerNum()));
            etScore.setText(getString(R.string.addScore, game.getCombinedScore()));
            setupDeleteBtn();
        }
    }

    // setupDeleteBtn method
    // Purpose: sets up the delete button to delete the game from the list of games
    // Returns: void
    private void setupDeleteBtn() {
        btnDelete.setOnClickListener(view -> {
            // create dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(AddGame.this);
            builder.setIcon(null);
            builder.setTitle(R.string.DeleteGameTitle);
            builder.setMessage(R.string.DeleteGameMessage);
            // make Sure
            builder.setPositiveButton(R.string.Yes, (dialogInterface, i) -> {
                config.deleteGame(gamePos);
                finish();
            });
            // Cancel
            builder.setNegativeButton(R.string.No, (dialogInterface, i) -> dialogInterface.dismiss()).show();
        });
    }

    // setTime method
    // Purpose: sets the time of the game creation
    // Returns: String
    private String setTime() {
        LocalDate localDate = LocalDate.now();

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MMM dd");
        String Date = formatter1.format(localDate);

        LocalTime localTime = LocalTime.now();

        DateTimeFormatter formatter6 = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        String Time = formatter6.format(localTime);
        return Date + " @ " + Time;
    }

    // checkSave method
    // Purpose: checks if the user wants to save the game and if they do,
    // then saves the game to the game list.
    // Returns: void
    private void checkSave() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddGame.this);
        builder.setIcon(null);
        builder.setTitle(R.string.SaveGameTitle);
        builder.setMessage(R.string.SaveGameMessage);
        builder.setPositiveButton(R.string.Yes, (dialog, which) -> {
            Game game = new Game(players, scores, time, diffLevel, playerScore);
            Achievements achievements = new Achievements();
            achievements.setScoreBounds(config.getPoorExpectedScore(), config.getGreatExpectedScore(), players);
            // set the achievement for the game
            game.setAchievements(achievements);
            config = cm.getConfigById(configPos);
            if (gamePos == -1) {// get the config instance
                config.addGame(game);
            } else {
                config.getGames().set(gamePos, game);
            }
            finish();
        });
        builder.setNegativeButton(R.string.No, (dialog, which) -> dialog.dismiss()).show();
    }

    // checkValid method
    // Purpose: checks if the user has entered valid data into the text fields
    // Returns: boolean
    private boolean checkValid() {
        EditText etP = findViewById(R.id.player);
        EditText etS = findViewById(R.id.score);
        if (etP.length() == 0) {
            etP.setError(getString(R.string.EmptyField));
            return false;
        }
        if (etS.length() == 0) {
            etS.setError(getString(R.string.EmptyField));
            return false;
        }
        return true;
    }

    // onCreateOptionsMenu method
    // Purpose: creates the menu and adds functionality to the save button
    // Returns: boolean
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_bar_item, menu);
        // on click listener for the save button
        MenuItem saveButton = menu.findItem(R.id.saveButton);
        // on click listener for the save button
        saveButton.setOnMenuItemClickListener(item -> {
            if (checkValid()) {
                // Save data into class
                EditText etP = findViewById(R.id.player);
                EditText etS = findViewById(R.id.score);
                try {
                    String sPlayer = (etP).getText().toString();
                    players = Integer.parseInt(sPlayer);
                } catch (NumberFormatException e){
                    etP.setError(getString(R.string.ErrorNum));
                    return false;
                }
                try {
                    // Save data into class
                    String sScore = (etS).getText().toString();
                    scores = Integer.parseInt(sScore);
                } catch (NumberFormatException e){
                    etS.setError(getString(R.string.ErrorNum));
                    return false;
                }
                checkSave();
            }
            return true;
        });
        return true;
    }

    // setUpPlayBtn method
    // Purpose: starts a game play
    // Returns: void
    private void setUpPlayBtn() {
        Button playBtn = findViewById(R.id.btnPlay);
        playBtn.setOnClickListener(view -> {

            checkAchievement();

        });
    }

    // checkAchievement method
    // Purpose: shows the achievement level for user
    // Returns: void
    private void checkAchievement() {
        Animation animation;
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        animation.setDuration(1500);

        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(AddGame.this);
        builder1.setIcon(R.drawable.cat_combat);
        builder1.setTitle("Achievement level Earned!");
        builder1.setMessage("Congratulations little one!!!");
        builder1.setPositiveButton("OK", (dialog, which) -> dialog.dismiss()).show();

    }

}
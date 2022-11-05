package com.example.projectscandium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

/**
 * The tutorial page.
 */
public class EmptyStateTutorial extends AppCompatActivity {

    public static final String EXTRA_MODE = "Extra message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_page);

        // Initial settings
        setupSettingsButton();

        // Sets page up according to mode: config or play
        //pageSetUp();
    }

    // Sets up the page according to mode (game config or game play)
    private void pageSetUp() {
        Intent intent = getIntent();

        String type = intent.getStringExtra(EXTRA_MODE);
        TextView title = findViewById(R.id.tutorialTitle);
        TextView content = findViewById(R.id.tutorial_content);

        // Mode is game config
        if (type.equals("config")){
            title.setText("How to create game configurations");
            content.setText("Getting Started... \\n \\n1. Select Settings button  \\n2. Enter the number of players \\n3. Choose how to set score \\n4. View possible achievement levels based on the number of players by clicking the view levels button \\n5. Save your settings by clicking the save button \\n6. Enjoy the game! :)");
            setupSettingsButton();
        }

        // Mode is game play
        if (type.equals("play")){
            title.setText("How to play game");
            content.setText("Getting Started... \\n \\n1. Select add button \\n2. Enjoy the game!");
            setupGameButton();
        }

    }

    // Button to go to game configurations page
    private void setupSettingsButton() {
        Button btn = findViewById(R.id.configs_button);
        btn.setOnClickListener(view -> {

            // Goes to game configurations page
            startConfigPage();

        });
    }

    // Goes to game configurations page activity
    private void startConfigPage() {
//        Intent i = new Intent(TutorialPage.this, AchievementsPage.class);
        Intent i = new Intent(EmptyStateTutorial.this, GameConfigList.class);
        startActivity(i);

    }

    // Button to go to game page
    private void setupGameButton() {
        Button btn = findViewById(R.id.configs_button);
        btn.setOnClickListener(view -> {

            // Goes to game configurations page
            startGamePage();

        });
    }

    // Goes to game page activity
    private void startGamePage() {
//        Intent i = new Intent(TutorialPage.this, AchievementsPage.class);
        Intent i = new Intent(EmptyStateTutorial.this, AddGame.class);
        startActivity(i);

    }


}
package com.example.projectscandium;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.projectscandium.model.Achievements;
import com.example.projectscandium.model.AchievementsInterface;
import com.example.projectscandium.model.AchievementsList;
import com.example.projectscandium.model.AchievementsManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AchievementsPage extends AppCompatActivity {

    public static final String EXTRA_ACHIEVEMENT = "Extra check";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(v -> finish());
        getSupportActionBar().setTitle(R.string.config_ach_text);

        // Default mode a
        setUpAchievements(3, 0);

        // will change page according to config or game played mode
        //modeSetUp();
    }

    // Sets up the page according to mode (game config or game play)
    private void modeSetUp() {
        Intent intent = getIntent();

        String type = intent.getStringExtra(EXTRA_ACHIEVEMENT);

        // Mode is game config
        if (type.equals("config")){
            // Needs to get score and player count somehow
            setUpAchievements(3,0);
        }

        // Mode is game play
        if (type.equals("play")){
            // Needs to get score and player count somehow
            setUpAchievements(5,0);

        }

    }

    // Get score and set up achievements
    private void setUpAchievements(int playerCount, int score) {

        List<Achievements> achievements;

        AchievementsInterface manager = new AchievementsInterface(playerCount);
        manager.checkAchievements(score);

        // Grabs achievements from interface
        achievements = manager.getAllAchievements();

        ArrayList<Button> buttonList  = new ArrayList<Button>();

        // Populates buttons array
        buttonList.add(findViewById(R.id.achievement1));
        buttonList.add(findViewById(R.id.achievement2));
        buttonList.add(findViewById(R.id.achievement3));
        buttonList.add(findViewById(R.id.achievement4));
        buttonList.add(findViewById(R.id.achievement5));
        buttonList.add(findViewById(R.id.achievement6));
        buttonList.add(findViewById(R.id.achievement7));
        buttonList.add(findViewById(R.id.achievement8));

        // Sets the achievements to be displayed
        TextView name = findViewById(R.id.achievement_name);
        TextView rqd = findViewById(R.id.achievement_rqd);
        TextView info = findViewById(R.id.achievement_info);
        TextView state = findViewById(R.id.achievement_status);

        // Iterates through achievements and buttons
        for (int i = 0; i < 8; i++) {
            int baseScore = achievements.get(i).getScore();
            String achName = achievements.get(i).getName();
            boolean status = achievements.get(i).isCompletion();

            // Sets up buttons with achievements
            buttonList.get(i).setOnClickListener(view -> {
                // Updates info
                name.setText(achName);
                rqd.setText("Complete score of " + baseScore + " to complete!");
                info.setText("0 / " + baseScore);
                state.setText("Completion: " + status);
            });
        }
    }
}
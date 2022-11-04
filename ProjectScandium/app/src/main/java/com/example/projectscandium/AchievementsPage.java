package com.example.projectscandium;

import androidx.appcompat.app.AppCompatActivity;

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

public class AchievementsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        setupBackButton();
        setUpAchievements();
    }

    // Button to go to game configurations page
    private void setupBackButton() {
        Button btn = findViewById(R.id.backButton);
        btn.setOnClickListener(view -> {

            // Goes to game configurations page
            returnToConfigPage();

        });
    }

    // Get score and set up achievements
    private void setUpAchievements() {

        List<Achievements> achievements;

        // Some sorta of player count is received
        int playerCount = 3;
        AchievementsInterface manager = new AchievementsInterface(playerCount);

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


    // Goes to game configurations page activity
    private void returnToConfigPage() {
        Intent i = new Intent(AchievementsPage.this, TutorialPage.class);
        startActivity(i);
    }
}
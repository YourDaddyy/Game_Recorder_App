package com.example.projectscandium;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class AchievementsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        setupBackButton();
    }

    // Button to go to game configurations page
    private void setupBackButton() {
        Button btn = findViewById(R.id.backButton);
        btn.setOnClickListener(view -> {

            // Goes to game configurations page
            returnToConfigPage();

        });
    }

    // Goes to game configurations page activity
    private void returnToConfigPage() {
        //Intent i = new Intent(AchievementsPage.this, gameConfigurations.class);
        //startActivity(i);
    }
}
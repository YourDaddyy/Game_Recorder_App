package com.example.projectscandium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

/**
 * The tutorial page.
 */
public class TutorialPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_page);
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
        //Intent i = new Intent(TutorialPage.this, gameConfigurations.class);
        //startActivity(i);
    }

}
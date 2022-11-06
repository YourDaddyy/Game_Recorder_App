package com.example.projectscandium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

/**
 * The welcome page.
 */
public class WelcomePage extends AppCompatActivity {

    private static final int SECONDS = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_state);

        setupSkipButton();
        setUpAnimation();
    }

    // Button to go to tutorial page
    private void setupSkipButton() {
        Button btn = findViewById(R.id.skip_button);
        btn.setOnClickListener(view -> {

            // Goes to tutorial page
            startTutorialPage();

            // Ends all processes of activity
            finish();

        });
    }

    // Goes to main menu automatically 4 seconds after animation
    private void setUpAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);

        // Set animation of image
        ImageView img = findViewById(R.id.welcome_icon);
        img.setAnimation(animation);

        // Animation listener
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Starts main menu after 4 seconds
                Handler handle = new Handler();
                handle.postDelayed(() -> {

                    // Goes to main menu
                    startTutorialPage();

                }, SECONDS);
            }
        });
    }

    // Goes to tutorial page activity
    private void startTutorialPage() {
//        Intent i = new Intent(WelcomePage.this, EmptyStateTutorial.class);
        Intent i = new Intent(WelcomePage.this, GameConfigList.class);
        startActivity(i);
        finish();
    }
}
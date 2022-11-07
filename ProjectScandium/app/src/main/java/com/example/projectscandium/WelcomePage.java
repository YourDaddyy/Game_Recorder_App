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
    Animation animation, animationGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        setUpAnimation();
        // listen for skip button press
        Button skipButton = findViewById(R.id.skip_button);
        skipButton.setOnClickListener(view -> {

            // end the animation
            animation.cancel();
            animationGame.cancel();

            startTutorialPage();

        });
    }

    // Goes to main menu automatically 4 seconds after animation
    private void setUpAnimation() {
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        animation.setDuration(1500);

        animationGame = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        animationGame.setDuration(2500);

        // Set animation of image
        ImageView img = findViewById(R.id.welcome_icon);
        ImageView gameImage = findViewById(R.id.game_icon);
        img.setAnimation(animation);
        gameImage.setAnimation(animationGame);

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
        Intent i = new Intent(WelcomePage.this, GameConfigList.class);
        startActivity(i);
        finish();
    }
}
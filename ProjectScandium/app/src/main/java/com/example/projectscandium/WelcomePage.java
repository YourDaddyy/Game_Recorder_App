package com.example.projectscandium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

/**
 * The welcome page.
 */
public class WelcomePage extends AppCompatActivity {

    private static final int SECONDS = 5000;
    Animation animation, animationGame;
    Thread timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        animation.setDuration(1500);

        animationGame = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        animationGame.setDuration(2500);

        // Set animation of image
        ImageView img = findViewById(R.id.welcome_icon);
        ImageView gameImage = findViewById(R.id.game_icon);
        img.setAnimation(animation);
        gameImage.setAnimation(animationGame);

        timer = new Thread(() -> {
            try {
                Thread.sleep(SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                Intent intent = new Intent(WelcomePage.this, GameConfigList.class);
                startActivity(intent);
            }
        });
        timer.start();

        // listen for skip button press
        Button skipButton = findViewById(R.id.skip_button);
        skipButton.setOnClickListener(view -> {

            // end the animation
            animation.cancel();
            animationGame.cancel();

            startTutorialPage();

        });
    }

    // Goes to tutorial page activity
    private void startTutorialPage() {
        // cancel the timer
        timer.interrupt();
        timer = null;
        Intent i = new Intent(WelcomePage.this, GameConfigList.class);
        startActivity(i);
        finish();
    }
}
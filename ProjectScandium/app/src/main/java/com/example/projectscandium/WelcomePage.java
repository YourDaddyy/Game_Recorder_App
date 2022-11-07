package com.example.projectscandium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

/*Class WelcomePage
 * Purpose: This class is the activity that displays the welcome page of the app
 */
public class WelcomePage extends AppCompatActivity {

    // private variables to store the necessary information
    private static final int SECONDS = 5000;
    private Animation animation, animationGame;
    private Thread timer;

    // onCreate method
    // Purpose: creates the activity, set the toolbar (including the title).
    // Add the animation to the logos.
    // Returns: void
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

        // Set timer for the welcome page
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

    // startTutorialPage method
    // Purpose: starts the tutorial page activity if the user presses the skip
    // button
    // Returns: void
    private void startTutorialPage() {
        // cancel the timer
        timer.interrupt();
        timer = null;
        Intent i = new Intent(WelcomePage.this, GameConfigList.class);
        startActivity(i);
        finish();
    }
}
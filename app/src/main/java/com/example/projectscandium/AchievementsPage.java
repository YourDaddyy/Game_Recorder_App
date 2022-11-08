package com.example.projectscandium;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projectscandium.model.Achievements;
import com.example.projectscandium.model.ConfigManager;
import com.example.projectscandium.model.Configs;

import java.util.Objects;

/*Class AchievementsPage
 * Purpose: This class is the activity that displays the achievements of the user
 * and allows the user to search for a specific achievement level based on the number of players
 */
public class AchievementsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // onCreate method
        // Purpose: creates the activity, set the toolbar. It populate the list with achievements
        // and the minimum score required based on the num of players
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(v -> finish());
        getSupportActionBar().setTitle(R.string.config_ach_text);

        // retrieve the value playerInput
        EditText playerNum = findViewById(R.id.playerInput);
        ListView achievementList = findViewById(R.id.achievementsList);

        // addTestListener
        // Purpose: adds a listener to the playerNum EditText. Updates the list of
        // achievements when the number of players is changed
        playerNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            // afterTextChanged
            // Purpose: updates the list of achievements when the number of players is changed
            // Return: void
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    // if the playerNum is empty, set the list to be empty
                    if (playerNum.getText().toString().isEmpty()) {
                        achievementList.setAdapter(null);
                    } else {
                        // get the extra info from the intent
                        int index = getIntent().getIntExtra("configIndex", 0);
                        // get the config
                        Configs config = ConfigManager.getInstance().getConfigById(index);
                        // create a new achievements object
                        Achievements achievements = new Achievements();
                        int upperBound = config.getGreatExpectedScore();
                        int lowerBound = config.getPoorExpectedScore();
                        // get the number of players
                        int numPlayers = Integer.parseInt(playerNum.getText().toString());
                        // set the score bounds
                        achievements.setScoreBounds(lowerBound, upperBound, numPlayers);
                        // add the achievements name and the lower bound of the score to the list
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AchievementsPage.this,
                                android.R.layout.simple_list_item_2, android.R.id.text1, achievements.achievements) {
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                View view = super.getView(position, convertView, parent);
                                TextView text1 = view.findViewById(android.R.id.text1);
                                TextView text2 = view.findViewById(android.R.id.text2);
                                text1.setText(achievements.getAchievementName(position));
                                String minText = getString(R.string.minScoreText);
                                text2.setText(
                                        String.format("%s  %s", minText, achievements.getAchievementValue(position)));
                                return view;
                            }
                        };
                        achievementList.setAdapter(adapter);
                    }
                } catch (NumberFormatException e) {
                    // if the playerNum is empty, set the list to be empty
                    achievementList.setAdapter(null);
                    playerNum.setError(getString(R.string.ErrorNum));
                }
            }
        });
    }
}
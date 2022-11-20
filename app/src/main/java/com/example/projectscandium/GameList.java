package com.example.projectscandium;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projectscandium.model.Achievements;
import com.example.projectscandium.model.ConfigManager;
import com.example.projectscandium.model.Configs;
import com.example.projectscandium.model.Game;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

/*Class GameList
 * Purpose: This class is the activity that allows the user to view the list of games
 */
public class GameList extends AppCompatActivity {

    // Singleton the game list
    private final ConfigManager cm = ConfigManager.getInstance();
    // Setup local game list
    private final ArrayList<Game> games = new ArrayList<>();
    private ArrayAdapter<Game> adapter;

    private int configPos;
    private static final String CONFIG_POS = "com.example.projectscandium.GameList - the Config position";
    private static final String ACH_THEME = "com.example.projectscandium.AddGame - the achTheme";

    // onCreate method
    // Purpose: creates the activity, set the toolbar (including the title).
    // Extracts the config from the intent and populates the list of games
    // Returns: void
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        setupToolBar();
        setupAddGameBtn();

        extractDataFromIntent();

        populateGameList();
        populateListView();
        registerCLickCallback();
    }

    // onResume method
    // Purpose: refreshes the list of games and populates the list view
    // Returns: void
    @Override
    public void onResume() {
        super.onResume();
        cm.saveConfigs(this);
        games.clear();
        populateGameList();
        populateListView();
        adapter.notifyDataSetChanged();
    }

    // extractDataFromIntent method
    // Purpose: extracts the config position from the intent
    // Returns: void
    private void extractDataFromIntent() {
        Intent intent = getIntent();
        configPos = intent.getIntExtra(CONFIG_POS, 0);
        Configs config = cm.getConfigById(configPos);
        String configName = config.getGameConfigName();
        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.configTitle) + "  " + configName);
    }

    // populateGameList method
    // Purpose: populates the local game list with the games from the config manager
    // and updates the necessary games.
    // Returns: void
    private void populateGameList() {
        try {
            ListView gameList = findViewById(R.id.gameList);
            Configs config = cm.getConfigById(configPos);
            TextView txtEmpty = findViewById(R.id.gameTutorial);
            if (config.getGameNum() == 0) {
                txtEmpty.setVisibility(View.VISIBLE);
                SpannableString spannableString = new SpannableString(getString(R.string.game_tutorial));
                ForegroundColorSpan teal = new ForegroundColorSpan(Color.parseColor("#03dac5"));
                spannableString.setSpan(teal, 36, 41, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                txtEmpty.setText(spannableString);
                gameList.setEmptyView(txtEmpty);
            } else {
                txtEmpty.setVisibility(View.GONE);
                for (int i = 0; i < config.getGameNum(); i++) {
                    games.add(config.searchGame(i));
                }
            }
        } catch (IndexOutOfBoundsException e) {
            finish();
        }
    }

    // populateListView method
    // Purpose: populates the list view with the games from the local game list
    // Returns: void
    private void populateListView() {
        adapter = new MyListAdapter();
        ListView list = findViewById(R.id.gameList);
        list.setAdapter(adapter);
    }

    /*
     * Class MyListAdapter
     * Purpose: This class is the adapter for the list view for the games
     */
    private class MyListAdapter extends ArrayAdapter<Game> {
        // Constructor for MyListAdapter
        // Purpose: calls the super constructor for ArrayAdapter
        // Returns: none
        public MyListAdapter() {
            super(GameList.this, R.layout.game_list_view, games);
        }

        // getView method
        // Purpose: gets the view for the list view and sets the appropriate text views
        // Returns: View
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.game_list_view, parent, false);
            }
            Game currentGame = games.get(position);

            // set game num txt
            TextView txtNum = itemView.findViewById(R.id.GameNum);
            txtNum.setText(getString(R.string.game_num, position + 1));

            // Set Player txt
            TextView txtPlayer = itemView.findViewById(R.id.txtPlayer);
            txtPlayer.setText(getString(R.string.game_player, currentGame.getPlayerNum()));

            // Set Score txt
            TextView txtScore = itemView.findViewById(R.id.txtScore);
            int gameScore = currentGame.getCombinedScore();
            txtScore.setText(getString(R.string.game_score, gameScore));

            TextView txtTime = itemView.findViewById(R.id.txtTime);
            txtTime.setText(getString(R.string.game_time, currentGame.getTime()));

            TextView txtDiff = itemView.findViewById(R.id.txtDiffLvl);
            String diffLevel = currentGame.getDifficulty();

            txtDiff.setText(getString(R.string.diff_lvl, diffLevel));

            TextView txtLevel = itemView.findViewById(R.id.txtLevel);
            Achievements achievements = currentGame.getAchievements();
            // Add Variable to Achievements method to get diff level
            String achievement = achievements.getAchievement(gameScore);
            txtLevel.setText(getString(R.string.game_level, achievements.getAchievementIndex(gameScore), achievement));

            return itemView;
        }
    }

    // setupToolBar method
    // Purpose: sets up the toolbar for the activity to listen for the up button
    // Returns: void
    private void setupToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    // setupAddGameBtn method
    // Purpose: sets up the FAB add game button to listen for clicks
    // Returns: void
    private void setupAddGameBtn() {
        FloatingActionButton btn = findViewById(R.id.addGameButton);
        btn.setOnClickListener(v -> {
            Intent intent = AddGame.makeIntent(GameList.this, configPos, -1);
            startActivity(intent);
        });
    }

    // registerCLickCallback method
    // Purpose: registers the click callback for the list view to go to the clicked
    // game
    // Returns: void
    private void registerCLickCallback() {
        ListView list = findViewById(R.id.gameList);
        list.setOnItemClickListener((parent, viewClicked, position, id) -> {
            Intent intent = AddGame.makeIntent(GameList.this, configPos, position);
            startActivity(intent);
        });
    }

    // onCreateOptionsMenu method
    // Purpose: creates the options menu for the activity and adds the edit config
    // button
    // Returns: boolean
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar_item, menu);
        return true;
    }

    // editConfig method
    // Purpose: Calls the edit config activity if the edit config button is clicked
    // from the toolbar
    // Returns: boolean
    public void EditConfig(MenuItem menuItem) {
        Intent intent = new Intent(GameList.this, GameConfig.class);
        intent.putExtra("configIndex", configPos);
        startActivity(intent);
    }

    // updateGame method
    // Purpose: updates the game in the config manager and the local game list,
    // specifically
    // the Achievements, if the game is configuration values have changed
    // Returns: void
    private void updateGame(Game game) {
        // update the achievement level of the game
        Achievements achievements = new Achievements();
        achievements.setAchievementName(game.getTheme());

        // get the upper and lower bound of the achievement level
        int upperBound = cm.getConfigById(configPos).getGreatExpectedScore();
        int lowerBound = cm.getConfigById(configPos).getPoorExpectedScore();
        // get the number of players
        int playerNum = game.getPlayerNum();
        String difficultyLvl = game.getDifficulty();
        // update the achievement level of the game
        achievements.setDiffLevel(difficultyLvl, lowerBound, upperBound, playerNum);
        game.setAchievements(achievements);
    }

}
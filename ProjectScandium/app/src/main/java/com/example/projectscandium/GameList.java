package com.example.projectscandium;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

public class GameList extends AppCompatActivity {
    // Singleton the game list
    private final ConfigManager cm = ConfigManager.getInstance();
    // Setup local game list
    private final ArrayList<Game> games = new ArrayList<>();
    private ArrayAdapter<Game> adapter;

    private int configPos;
    private static final String CONFIG_POS = "com.example.projectscandium.GameList - the Config position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        setupToolBar();
        setupAddGameBtn();

        extractDataFromIntent();

        populateGameList();
        populateListView();
//        registerCLickCallback();
    }

    // reset game list page
    @Override
    public void onResume() {
        super.onResume();
        cm.saveConfigs(this);
        games.clear();
        populateGameList();
        populateListView();
        adapter.notifyDataSetChanged();
    }

    // extract game index
    private void extractDataFromIntent() {
        Intent intent = getIntent();
        configPos = intent.getIntExtra(CONFIG_POS, 0);
        Configs config = cm.getConfigById(configPos);
        String configName = config.getGameConfigName();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Configuration: " + configName);
    }

    // get the game list from singleton
    private void populateGameList() {
        try {
            ListView gameList = findViewById(R.id.gameList);
            Configs config = cm.getConfigById(configPos);
            TextView txtEmpty = findViewById(R.id.gameTutorial);
            if (config.getGameNum() == 0) {
                txtEmpty.setVisibility(View.VISIBLE);
                SpannableString spannableString = new SpannableString(getString(R.string.game_tutorial));
                ForegroundColorSpan teal = new ForegroundColorSpan(Color.parseColor("#03dac5"));
                spannableString.setSpan(teal, 34, 39, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                txtEmpty.setText(spannableString);
                gameList.setEmptyView(txtEmpty);
            } else {
                txtEmpty.setVisibility(View.GONE);
                for (int i = 0; i < config.getGameNum(); i++) {
                    games.add(config.searchGame(i));
                }
                for (Game game : games) {
                    updateGame(game);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            finish();
        }
    }

    // create list view
    private void populateListView() {
        adapter = new MyListAdapter();
        ListView list = findViewById(R.id.gameList);
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<Game> {
        public MyListAdapter() {
            super(GameList.this, R.layout.game_list_view, games);
        }

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

            TextView txtLevel = itemView.findViewById(R.id.txtLevel);
            Achievements achievements = currentGame.getAchievements();
            String achievement = achievements.getAchievement(gameScore);
            txtLevel.setText(getString(R.string.game_level, achievements.getAchievementIndex(gameScore), achievement));

            return itemView;
        }
    }

    private void setupToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupAddGameBtn() {
        FloatingActionButton btn = findViewById(R.id.addGameButton);
        btn.setOnClickListener(v -> {
            Intent intent = AddGame.makeIntent(GameList.this, configPos, -1);
            startActivity(intent);
        });
    }

    // Set up switch activity for click game
    private void registerCLickCallback() {
        ListView list = findViewById(R.id.gameList);
        list.setOnItemClickListener((parent, viewClicked, position, id) -> {
            Intent intent = AddGame.makeIntent(GameList.this, configPos, position);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar_item, menu);
        return true;
    }

    public void EditConfig(MenuItem menuItem) {
        Intent intent = new Intent(GameList.this, GameConfig.class);
        intent.putExtra("configIndex", configPos);
        startActivity(intent);
    }

    private void updateGame(Game game) {
        // update the achievement level of the game
        Achievements achievements = new Achievements();
        // get the upper and lower bound of the achievement level
        int upperBound = cm.getConfigById(configPos).getGreatExpectedScore();
        int lowerBound = cm.getConfigById(configPos).getPoorExpectedScore();
        // get the number of players
        int playerNum = game.getPlayerNum();
        // update the achievement level of the game
        achievements.setScoreBounds(lowerBound, upperBound, playerNum);
        game.setAchievements(achievements);
    }

}
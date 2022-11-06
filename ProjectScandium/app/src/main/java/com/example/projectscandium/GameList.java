package com.example.projectscandium;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projectscandium.model.ConfigManager;
import com.example.projectscandium.model.Configs;
import com.example.projectscandium.model.Game;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class GameList extends AppCompatActivity {
    //Singleton the game list
    private ConfigManager cm = ConfigManager.getInstance();
    //Setup local game list
    private ArrayList<Game> games = new ArrayList<>();
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

        registerCLickCallback();
    }

    // reset game list page
    @Override
    public void onResume() {
        super.onResume();
        games.clear();
        populateGameList();
        adapter.notifyDataSetChanged();
    }

    // extract game index
    private void extractDataFromIntent() {
        Intent intent = getIntent();
        configPos = intent.getIntExtra(CONFIG_POS, 0);
    }

    // create Intent Portal
    public static Intent makeIntent(Context context, int pos){
        Intent intent = new Intent(context, AddGame.class);
        intent.putExtra(CONFIG_POS, pos);
        return intent;
    }

    // get the game list from singleton
    private void populateGameList() {
        Configs config = cm.getConfigById(configPos);
        if(config.getGameNum() == 0){
            // empty state...
        }else{
            for(int i = 0; i < config.getGameNum(); i++){
                games.add(config.searchGame(i));
            }
        }

    }

    // create list view
    private void populateListView() {
        adapter = new MyListAdapter();
        ListView list = findViewById(R.id.gameList);
        list.setAdapter(adapter);
    }
    private class MyListAdapter extends ArrayAdapter<Game> {
        public MyListAdapter(){
            super(GameList.this, R.layout.game_list_view, games);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.game_list_view, parent, false);
            }
            Game currentGame = games.get(position);

            // set game num txt
            TextView txtNum = itemView.findViewById(R.id.GameNum);
            txtNum.setText(getString(R.string.game_num, position));

            // Set Player txt
            TextView txtPlayer = itemView.findViewById(R.id.txtPlayer);
            txtPlayer.setText(getString(R.string.game_player, currentGame.getPlayerNum()));

            // Set Score txt
            TextView txtScore = itemView.findViewById(R.id.txtScore);
            txtScore.setText(getString(R.string.game_score, currentGame.getCombinedScore()));

            return itemView;
        }
    }

    //Set up switch activity for click game
    private void registerCLickCallback() {
        ListView list = findViewById(R.id.gameList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Intent intent = AddGame.makeIntent(GameList.this, configPos, position);
                startActivity(intent);
            }
        });
    }

    private void setupToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupAddGameBtn() {
        FloatingActionButton btn = findViewById(R.id.addGamebtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddGame.makeIntent(GameList.this, configPos, -1);
                startActivity(intent);
            }
        });
    }

}
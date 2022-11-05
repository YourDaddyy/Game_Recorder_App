package com.example.projectscandium;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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

    private int gamePos = -1;
    private static final String GAME_POS = "com.example.projectscandium.GameList - the gamePos";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        setupToolBar();
        setupAddGameBtn();

        extractDataFromIntent();

        populateGameList();
        populateListView();
    }

    // extract game index
    private void extractDataFromIntent() {
        Intent intent = getIntent();
        gamePos = intent.getIntExtra(GAME_POS, -1);
    }

    // create Intent Portal
    public static Intent makeIntent(Context context, int pos){
        Intent intent = new Intent(context, AddGame.class);
        intent.putExtra(GAME_POS, pos);
        return intent;
    }

    private void populateListView() {
        Configs config = cm.getConfigById(gamePos);
        for(int i = 0; i < config.getGameNum(); i++){
            games.add(config.searchGame(i));
        }
    }

    private void populateGameList() {
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

            TextView txtNum = itemView.findViewById(R.id.GameNum);
            txtNum.setText("Game " + position);

            // Set Game Num
            TextView txtWinner = itemView.findViewById(R.id.txtPlayer);
            txtWinner.setText("Number Players: " + currentGame.getPlayerNum());

            // Set Player Num
            TextView txtGame = itemView.findViewById(R.id.txtScore);
            txtGame.setText("Combined Score: " + currentGame.getCombinedScore());

            // Set Score
            TextView txtTime = itemView.findViewById(R.id.txtLevel);
            txtTime.setText("LEVEL: " + currentGame.getLevel());

            return itemView;
        }
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
                Intent intent = AddGame.makeIntent(GameList.this, -1);
                startActivity(intent);
            }
        });
    }

}
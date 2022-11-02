package com.example.projectscandium;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class GameList extends AppCompatActivity {
    //Singleton the game list
//    private GameManager gm = GameManager.getInstance();
    //Setup local game list
//    private List<Game> Games = new ArrayList<Game>();
//    private ArrayAdapter<Game> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        setupToolBar();
        setupAddGameBtn();

        populateGameList();
        populateListView();
    }

    private void populateListView() {
//        for(int i = 0; i < gm.getNumber(); i++){
//            Games.add(gm.searchGame(i));
//        }
    }

    private void populateGameList() {
//        MyListAdapter adapter = new MyListAdapter();
//        ListView list = findViewById(R.id.gameList);
//        list.setAdapter(adapter);
//    }
//    private class MyListAdapter extends ArrayAdapter<Game> {
//        public MyListAdapter(){
//            super(GameList.this, R.layout.game_list, Games);
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View itemView = convertView;
//            if(itemView == null){
//                itemView = getLayoutInflater().inflate(R.layout.game_list, parent, false);
//            }
//            // Game currentGame = Games.get(position);
//
//            // Set Game Num
//            TextView txtWinner = itemView.findViewById(R.id.txtWinner);
//            // txtWinner.setText(currentGame.printWinners());
//
//            // Set Player Num
//            TextView txtGame = itemView.findViewById(R.id.txtGameInfo);
//            // txtGame.setText(currentGame.printScores());
//
//            // Set Score
//            TextView txtTime = itemView.findViewById(R.id.txtTime);
//            // txtTime.setText(currentGame.getTime());
//
//            return itemView;
//        }
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
                Intent intent = new Intent(GameList.this, EmptyState.class);
                startActivity(intent);
            }
        });
    }

}
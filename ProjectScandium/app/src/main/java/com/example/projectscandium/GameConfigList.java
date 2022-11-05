package com.example.projectscandium;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projectscandium.model.ConfigManager;
import com.example.projectscandium.model.Configs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class GameConfigList extends AppCompatActivity {

    ListView ConfigList;
    TextView configEmptyState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_config_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set the title of the activity
        setTitle("Game Configurations");

        // get the game config manager
        ConfigManager.getInstance().loadConfigs(this);

        // get the floating action button and set the click listener
        FloatingActionButton newConfig = findViewById(R.id.newConfigButton);
        newConfig.setOnClickListener(view -> {
            // create a new intent to start the new game activity
            Intent intent = new Intent(GameConfigList.this, GameConfig.class);
            intent.putExtra("mode", "NewConfig");
            startActivity(intent);
        });

        // look for back button press
        toolbar.setNavigationOnClickListener(v -> {
            // do nothing
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        ConfigList = findViewById(R.id.configListContainer);

        // Access the ConfigManager to get the list of configs
        ConfigManager.getInstance().saveConfigs(this);
        ConfigManager configManager = ConfigManager.getInstance();

        // Get a copy of the list of configs to a new array list
        ArrayList<Configs> configs = configManager.getConfigs();

        // get the size of the array list
        int size = configManager.getSize();

        configEmptyState = findViewById(R.id.configTutorial);
        if (size == 0) {
            ConfigList.setAdapter(null);
            configEmptyState.setVisibility(TextView.VISIBLE);
        } else {
            configEmptyState.setVisibility(TextView.GONE);
            // set the adapter for the list view
            ConfigListAdapter adapter = new ConfigListAdapter(this, R.layout.config_list_layout, configs);
            ConfigList.setAdapter(adapter);

            // listen for clicks on the list
            ConfigList.setOnItemClickListener((parent, view, position, id) -> {
                // create a new intent to start the new game activity
                Intent intent = new Intent(GameConfigList.this, GameConfig.class);
                // pass the position of the config to the new activity
                intent.putExtra("configIndex", position);
                intent.putExtra("mode", "EditConfig");
                startActivity(intent);
            });
        }
    }
}
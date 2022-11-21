package com.example.projectscandium;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projectscandium.model.ConfigManager;
import com.example.projectscandium.model.Configs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/*Class GameConfigList
 * Purpose: This class is the activity that allows the user to view the list of configs
 */
public class GameConfigList extends AppCompatActivity {

    // private variables to store the necessary information
    ListView ConfigList;
    TextView configEmptyState, addGameTextValue;

    // onCreate method
    // Purpose: creates the activity, set the toolbar (including the title).
    // Loads the list of configs from the config manager(shared preferences)
    // Returns: void
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_config_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set the title of the activity
        setTitle(R.string.ConfigurationListTitle);

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

    // onResume method
    // Purpose: saves the list of configs to the config manager(shared preferences).
    // Also sets the empty state text if there are no configs and populates the list
    // with the custom adapter if there are configs.
    // Returns: void
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
        addGameTextValue = findViewById(R.id.addGameText);
        if (size == 0) {
            addGameTextValue.setVisibility(TextView.GONE);
            ConfigList.setAdapter(null);
            configEmptyState.setVisibility(TextView.VISIBLE);
            SpannableString spannableString = new SpannableString(getString(R.string.config_tutorial_text));
            ForegroundColorSpan teal = new ForegroundColorSpan(Color.parseColor("#03DAC5"));
            spannableString.setSpan(teal, 36, 41, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            configEmptyState.setText(spannableString);
            ConfigList.setEmptyView(configEmptyState);
        } else {
            configEmptyState.setVisibility(TextView.GONE);
            addGameTextValue.setVisibility(TextView.VISIBLE);
            // set the adapter for the list view
            ConfigListAdapter adapter = new ConfigListAdapter(this, R.layout.config_list_layout, configs);
            ConfigList.setAdapter(adapter);

            // listen for clicks on the list
            ConfigList.setOnItemClickListener((parent, view, position, id) -> {
                // create a new intent to start the new game activity
                Intent intent = new Intent(GameConfigList.this, GameList.class);
                intent.putExtra("com.example.projectscandium.GameList - the Config position", position);
                startActivity(intent);
            });
        }
    }

    // onBackPressed method
    // Purpose: creates an alert to confirm exiting the app when the back button is
    // pressed
    // Returns: void
    @Override
    public void onBackPressed() {
        // create dialog box to confirm exit
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.ExitAppMessage);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.Yes, (dialog, id) -> finishAffinity());
        builder.setNegativeButton(R.string.No, (dialog, id) -> dialog.cancel());
        builder.show();
    }
}
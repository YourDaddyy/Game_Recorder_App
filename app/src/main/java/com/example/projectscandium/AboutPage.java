package com.example.projectscandium;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import java.util.Objects;

public class AboutPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        // listen to up button
        toolbar.setNavigationOnClickListener(v -> finish());

        // set the title of the activity
        setTitle(R.string.aboutPageTitle);
    }

    // onBackPressed method
    // Purpose: checks for if the user presses the back button
    // Returns: void
    @Override
    public void onBackPressed() {
        finish();
    }
}
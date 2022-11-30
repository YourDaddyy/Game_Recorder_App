package com.example.projectscandium;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.widget.TextView;

import java.util.Objects;

/*Class AboutPage
 * Purpose: This class is the activity that allows the user to view the About Us page
 */
public class AboutPage extends AppCompatActivity {

    // onCreate method
    // Purpose: creates the activity, set the toolbar (including the title).
    // Loads the list of configs from the config manager(shared preferences)
    // Returns: void
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

        TextView aboutPageText = findViewById(R.id.Resources_Text);
        aboutPageText.setGravity(Gravity.CENTER);
        aboutPageText.setMovementMethod(LinkMovementMethod.getInstance());
        aboutPageText.setText(Html.fromHtml("<b>Tutorials</b> <br> " +
                "- Dr Brian's YouTube Tutorials <br>" +
                "<a href=https://www.youtube.com/watch?v=KsyCfxxEIwQ>- Gif Animation Tutorial</a><br>"+
                "<a href=https://www.youtube.com/watch?v=h6g4NpiC0i4&ab_channel=TechizVibe>- Scroll View Tutorial</a><br>"+
                "<a href=https://www.youtube.com/watch?v=JvyA7seYiCI&ab_channel=TechnicalCoding>- Fade Animation Tutorial</a><br>"+
                "<a href=https://www.youtube.com/watch?v=0SJAK_pKUoE>- Sound Tutorial <br>" +
                "<a href=https://devstudioonline.com/article/create-rounded-background-as-border-radius-in-android-layout>- Rounded Buttons </a><br>" +
                "<a href=https://www.youtube.com/watch?v=5PaWtQAOdi8>- Setup custom Alert Dialog </a><br> <br>" +

                "<b>Media</b> <br>" +
                "<a href=https://sciencenotes.org/scandium-facts/>-Scandium Icon Source</a><br>"+
                "<a href=https://www.xmple.com/>-Background Source</a><br>"+
                "<a href=https://www.xmple.com/>-Dog Theme Icon Source</a><br>"+
                "<a href=https://www.shutterstock.com/image-vector/decorative-bird-icon-vector-illustration-78771382>-Bird Theme Icon Source</a><br>" +
                "<a href=https://www.xmple.com/>-Dog Theme Celebration Sound Source</a><br>"+
                "<a href=https://www.xmple.com/>-Bird Theme Celebration Sound Source</a><br>"+
                "<a href=https://www.youtube.com/watch?v=s8LBlwQGmqo&ab_channel=SoundEffectDatabase/>-Cat Theme Celebration Sound Source</a><br>" +
                "<a href=https://www.freepik.com/free-vector/illustration-animal-adult-coloring-page_2614622.htm#query=cat%20art&position=2&from_view=search&track=sph>-Game Image Source</a><br>"
                ,Html.FROM_HTML_MODE_LEGACY));
    }

    // onBackPressed method
    // Purpose: checks for if the user presses the back button
    // Returns: void
    @Override
    public void onBackPressed() {
        finish();
    }
}
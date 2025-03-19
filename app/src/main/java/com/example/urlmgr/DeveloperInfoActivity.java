package com.example.urlmgr;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class DeveloperInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_info);

        // Initialize ImageView
        ImageView imageViewDeveloper = findViewById(R.id.imageViewDeveloper);

        // Initialize buttons
        Button btnLinkedin = findViewById(R.id.btnLinkedin);
        Button btnTwitter = findViewById(R.id.btnTwitter);
        Button btnFacebook = findViewById(R.id.btnFacebook);
        Button btnGithub = findViewById(R.id.btnGithub);

        // Set click listeners for buttons
        btnLinkedin.setOnClickListener(v -> openUrl("https://www.linkedin.com/in/mohamed-nashath-27a9142b6/"));
        btnTwitter.setOnClickListener(v -> openUrl("https://x.com/NashathM?t=crEae1kwCL2M0j6MLFdVfws=09"));
        btnFacebook.setOnClickListener(v -> openUrl("https://www.facebook.com/profile.php?id=100009394519222"));
        btnGithub.setOnClickListener(v -> openUrl("https://github.com/nashgithub1"));
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
package com.example.urlmgr;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Find buttons by their IDs
        Button createShortUrlBtn = findViewById(R.id.btn_create_short_url);
        Button viewUrlsBtn = findViewById(R.id.btn_view_my_urls);
        Button developerInfoBtn = findViewById(R.id.btn_developer_info);
        Button exitAppBtn = findViewById(R.id.btn_exit_app);

        // Set click listeners for buttons
        createShortUrlBtn.setOnClickListener(this);
        viewUrlsBtn.setOnClickListener(this);
        developerInfoBtn.setOnClickListener(this);
        exitAppBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        // Handle button clicks
        if (v.getId() == R.id.btn_create_short_url) {
            // Handle create short URL button click
            Intent intent = new Intent(MainActivity.this, CreateShortUrlActivity.class);
            startActivity(intent);
        }

        else if (v.getId() == R.id.btn_view_my_urls) {
            // Handle view URLs button click
            Intent intent = new Intent(MainActivity.this, ViewUrlsActivity.class);
            startActivity(intent);
        }


        else if (v.getId() == R.id.btn_developer_info) {
            // Handle developer info button click
            Intent intent = new Intent(MainActivity.this, DeveloperInfoActivity.class);
            startActivity(intent);


        } else if (v.getId() == R.id.btn_exit_app) {
            // Handle exit app button click
            finish();
        }
    }
}

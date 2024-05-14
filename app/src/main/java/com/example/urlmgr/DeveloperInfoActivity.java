package com.example.urlmgr;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DeveloperInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_info);

        // Initialize and populate TextViews with developer information
        TextView textViewTwitter = findViewById(R.id.textViewTwitter);
        textViewTwitter.setMovementMethod(LinkMovementMethod.getInstance());

        TextView textViewTwitter2 = findViewById(R.id.textViewTwitter2);
        textViewTwitter2.setMovementMethod(LinkMovementMethod.getInstance());
    }
}

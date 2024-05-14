package com.example.urlmgr;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ViewUrlsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UrlAdapter urlAdapter;
    private List<UrlItem> urlsList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_urls);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper = new DatabaseHelper(this); // Initialize the DatabaseHelper
        urlsList = databaseHelper.getAllUrls(); // Get the list of URLs
        urlAdapter = new UrlAdapter(this, urlsList);
        recyclerView.setAdapter(urlAdapter);

        loadUrls();
    }

    private void loadUrls() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = databaseHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT " + DatabaseHelper.COLUMN_URL_NAME + ", " + DatabaseHelper.COLUMN_LONG_URL + ", " + DatabaseHelper.COLUMN_SHORT_URL + ", " + DatabaseHelper.COLUMN_LOCATION +
                    " FROM " + DatabaseHelper.TABLE_URLS, null);


            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_URL_NAME);
                int longUrlIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LONG_URL);
                int shortUrlIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_SHORT_URL);
                int locationIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LOCATION);

                do {
                    String name = cursor.getString(nameIndex);
                    String longUrl = cursor.getString(longUrlIndex);
                    String shortUrl = cursor.isNull(shortUrlIndex) ? "" : cursor.getString(shortUrlIndex); // Handle null value for shortUrl
                    String location = cursor.getString(locationIndex);
                    urlsList.add(new UrlItem(name, longUrl, shortUrl, location));
                } while (cursor.moveToNext());
            } else {
                Toast.makeText(this, "No URLs found", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        if (urlsList.isEmpty()) {
            Toast.makeText(this, "No URLs found", Toast.LENGTH_SHORT).show();
        } else {
            urlAdapter = new UrlAdapter(this, urlsList);
            recyclerView.setAdapter(urlAdapter);
        }
    }
}


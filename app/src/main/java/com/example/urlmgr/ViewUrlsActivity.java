package com.example.urlmgr;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ViewUrlsActivity extends AppCompatActivity implements UrlAdapter.OnDeleteClickListener {

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
        databaseHelper = new DatabaseHelper(this);
        urlsList = new ArrayList<>();
        urlAdapter = new UrlAdapter(this, urlsList, this);
        recyclerView.setAdapter(urlAdapter);

        loadUrls();

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                onDeleteClick(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void loadUrls() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = databaseHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT " + DatabaseHelper.COLUMN_ID + ", " + DatabaseHelper.COLUMN_URL_NAME + ", " + DatabaseHelper.COLUMN_LONG_URL + ", " + DatabaseHelper.COLUMN_SHORT_URL + ", " + DatabaseHelper.COLUMN_LOCATION +
                    " FROM " + DatabaseHelper.TABLE_URLS, null);

            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID);
                int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_URL_NAME);
                int longUrlIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LONG_URL);
                int shortUrlIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_SHORT_URL);
                int locationIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LOCATION);

                do {
                    int id = cursor.getInt(idIndex);
                    String name = cursor.getString(nameIndex);
                    String longUrl = cursor.getString(longUrlIndex);
                    String shortUrl = cursor.isNull(shortUrlIndex) ? "" : cursor.getString(shortUrlIndex);
                    String location = cursor.getString(locationIndex);
                    urlsList.add(new UrlItem(id, name, longUrl, shortUrl, location));
                } while (cursor.moveToNext());

                urlAdapter.notifyDataSetChanged();
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
    }

    @Override
    public void onDeleteClick(int position) {
        UrlItem urlItem = urlsList.get(position);
        boolean isDeleted = databaseHelper.deleteUrl(urlItem.getId());
        if (isDeleted) {
            urlsList.remove(position);
            urlAdapter.notifyItemRemoved(position);
            Toast.makeText(this, "URL deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to delete URL", Toast.LENGTH_SHORT).show();
        }
    }
}

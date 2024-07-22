
package com.example.urlmgr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "urls.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_URLS = "urls";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_URL_NAME = "url_name";
    public static final String COLUMN_LONG_URL = "long_url";

    public static final String COLUMN_SHORT_URL = "short_url";
    public static final String COLUMN_LOCATION = "location";

    private static final String CREATE_TABLE_URLS = "CREATE TABLE " + TABLE_URLS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_LONG_URL + " TEXT, "
            + COLUMN_SHORT_URL + " TEXT, "
            + COLUMN_URL_NAME + " TEXT, "
            + COLUMN_LOCATION + " TEXT"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_URLS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_URLS);
        onCreate(db);
    }

    public long insertUrl(String name, String longUrl, String shortUrl, String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_URL_NAME, name);
        values.put(COLUMN_LONG_URL, longUrl);
        values.put(COLUMN_SHORT_URL, shortUrl);
        values.put(COLUMN_LOCATION, location);

        long rowId = -1;
        try {
            rowId = db.insert(TABLE_URLS, null, values);
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error inserting URL: " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.close();
        }
        return rowId;
    }

    public List<UrlItem> getAllUrls() {
        List<UrlItem> urls = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_URLS, null);

            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndexOrThrow(COLUMN_URL_NAME);
                int longUrlIndex = cursor.getColumnIndexOrThrow(COLUMN_LONG_URL);
                int shortUrlIndex = cursor.getColumnIndexOrThrow(COLUMN_SHORT_URL);
                int locationIndex = cursor.getColumnIndexOrThrow(COLUMN_LOCATION);

                do {
                    String name = cursor.getString(nameIndex);
                    String longUrl = cursor.getString(longUrlIndex);
                    String shortUrl = cursor.getString(shortUrlIndex);
                    String location = cursor.getString(locationIndex);

                    urls.add(new UrlItem(name, longUrl, shortUrl, location));
                } while (cursor.moveToNext());
            }
        } catch (IllegalArgumentException e) {
            // Handle the case where a column name was not found
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return urls;
    }


}


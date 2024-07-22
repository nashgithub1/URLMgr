package com.example.urlmgr;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateShortUrlActivity extends AppCompatActivity {

    private EditText etLongUrl;
    private EditText etUrlName;
    private Button btnSaveUrl;
    private TextView tvCurrentLocation;
    private DatabaseHelper databaseHelper;
    private LocationManager locationManager;
    private LocationListener locationListener;

    private static final int REQUEST_LOCATION_PERMISSION = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_short_url);

        etLongUrl = findViewById(R.id.et_long_url);
        etUrlName = findViewById(R.id.et_url_name);
        btnSaveUrl = findViewById(R.id.btn_save_url);
        tvCurrentLocation = findViewById(R.id.tv_current_location);
        databaseHelper = new DatabaseHelper(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String locationString = String.format("Lat: %.6f, Long: %.6f", latitude, longitude);
                tvCurrentLocation.setText("Current Location: " + latitude + ", " + longitude);
                tvCurrentLocation.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };

        btnSaveUrl.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(CreateShortUrlActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                saveUrl(tvCurrentLocation.getText().toString());
            } else {
                requestLocationPermission();
            }
        });
    }

    private void saveUrl(String location) {
        if (isNetworkAvailable()) {
            String longUrl = etLongUrl.getText().toString().trim();
            String urlName = etUrlName.getText().toString().trim();

            if (!longUrl.isEmpty()) {
                shortenUrl(longUrl, urlName, location);
            } else {
                Toast.makeText(this, "Please enter a long URL", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No internet connection available", Toast.LENGTH_SHORT).show();
        }
    }

    private void shortenUrl(String longUrl, String urlName, String location) {
        TinyUrlApi tinyUrlApi = RetrofitClient.getTinyUrlApi();
        TinyUrlRequest tinyUrlRequest = new TinyUrlRequest(longUrl);
        Call<TinyUrlResponse> call = tinyUrlApi.shortenUrl(tinyUrlRequest);

        Log.d("API Debug", "Sending request to TinyURL API");
        Log.d("API Debug", "Long URL: " + longUrl);

        call.enqueue(new Callback<TinyUrlResponse>() {
            @Override
            public void onResponse(@NonNull Call<TinyUrlResponse> call, @NonNull Response<TinyUrlResponse> response) {
                Log.d("API Debug", "Received response from TinyURL API");
                Log.d("API Debug", "Response Code: " + response.code());

                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    String shortUrl = response.body().getData().getTinyUrl();
                    Log.d("API Debug", "Shortened URL: " + shortUrl);

                    long rowId = databaseHelper.insertUrl(urlName, longUrl, shortUrl, location);

                    if (rowId != -1) {
                        Toast.makeText(CreateShortUrlActivity.this, "URL saved successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("API Error", "Failed to save URL to database");
                        Toast.makeText(CreateShortUrlActivity.this, "Failed to save URL", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("API Error", "Response not successful. Code: " + response.code());
                    if (response.errorBody() != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("API Error", "Error Body: " + errorBody);
                        } catch (IOException e) {
                            Log.e("API Error", "Error reading error body: " + e.getMessage());
                        }
                    }
                    Toast.makeText(CreateShortUrlActivity.this, "Failed to shorten URL", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TinyUrlResponse> call, @NonNull Throwable t) {
                Log.e("API Error", "Network Failure: " + t.getMessage());
                Log.e("API Error", "Stack trace: " + Log.getStackTraceString(t));
                Toast.makeText(CreateShortUrlActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(CreateShortUrlActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                } else {
                    Toast.makeText(this, "Location permission seems to be denied again. Check app settings?", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

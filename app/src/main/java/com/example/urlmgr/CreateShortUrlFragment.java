 //CreateShortUrlFragment.java

package com.example.urlmgr;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.util.Pair;

/* public class CreateShortUrlFragment extends Fragment {

    private EditText etLongUrl;
    private EditText etUrlName;
    private Button btnSaveUrl;
    private TextView tvCurrentLocation;
    private TextView tvPopupMessage;

    private DatabaseHelper databaseHelper;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private static final int REQUEST_LOCATION_PERMISSION = 1001;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_short_url, container, false);

        etLongUrl = view.findViewById(R.id.et_long_url);
        etUrlName = view.findViewById(R.id.et_url_name);
        btnSaveUrl = view.findViewById(R.id.btn_save_url);
        tvCurrentLocation = view.findViewById(R.id.tv_current_location);
        tvPopupMessage = view.findViewById(R.id.tv_popup_message);

        databaseHelper = new DatabaseHelper(getContext());

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
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

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

        btnSaveUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUrl();
            }
        });

        return view;
    }

    private void saveUrl() {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted, proceed with getting location
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            // Permission is not granted, request it
            requestLocationPermission();
        }

        String name = etUrlName.getText().toString().trim();
        String longUrl = etLongUrl.getText().toString().trim();

        if (!longUrl.isEmpty()) {
            long rowId = databaseHelper.insertUrl(name, longUrl, location);

            if (rowId != -1) {
                Toast.makeText(getContext(), "URL saved successfully!", Toast.LENGTH_SHORT).show();
                tvPopupMessage.setVisibility(View.VISIBLE);
                etLongUrl.setText("");
            } else {
                Toast.makeText(getContext(), "Failed to save URL!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Please enter a URL", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            } else {
                Toast.makeText(getContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        locationManager.removeUpdates(locationListener);
    }
}  */

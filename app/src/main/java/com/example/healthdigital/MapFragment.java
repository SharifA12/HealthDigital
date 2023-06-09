package com.example.healthdigital;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {

    ReminderActivity reminderActivity;

    public LatLng latLngFinal;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
        View view=inflater.inflate(R.layout.fragment_map, container, false);

        // Initialize map fragment
        SupportMapFragment supportMapFragment=(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);



        // Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                reminderActivity = (ReminderActivity) getActivity();
                if (reminderActivity.latLng != null){
                    latLngFinal = reminderActivity.latLng;
                }else {
                    latLngFinal = new LatLng(49.2827,-123.1207 );
                    reminderActivity.latLng = latLngFinal;
                }

                MarkerOptions markerOptions=new MarkerOptions();
                // Set position of marker
                markerOptions.position(latLngFinal);
                // Set title of marker
                markerOptions.title(latLngFinal.latitude+" : "+latLngFinal.longitude);
                // Remove all marker
                googleMap.clear();
                // Animating to zoom the marker
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngFinal,20));
                // Add marker on map
                googleMap.addMarker(markerOptions);

                // When map is loaded
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngFinal, 10));
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        latLngFinal = latLng;
                        // When clicked on map
                        // Initialize marker options
                        MarkerOptions markerOptions=new MarkerOptions();
                        // Set position of marker
                        markerOptions.position(latLng);
                        // Set title of marker
                        markerOptions.title(latLng.latitude+" : "+latLng.longitude);
                        // Remove all marker
                        googleMap.clear();
                        // Animating to zoom the marker
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));
                        // Add marker on map
                        googleMap.addMarker(markerOptions);


                        reminderActivity.latLng = latLng;

                    }
                });
            }
        });
        // Return view
        return view;
    }
}
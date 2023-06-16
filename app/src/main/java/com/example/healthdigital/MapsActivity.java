//package com.example.healthdigital;
//
//import android.content.pm.PackageManager;
//import android.location.Address;
//import android.location.Geocoder;
//import android.os.Bundle;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//public class MapsActivity extends AppCompatActivity {
//
//    private GoogleMap mMap;
//    private LatLng latLng;
//    private Marker marker;
//    Geocoder geocoder;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_map);
//
//        geocoder = new Geocoder(this, Locale.getDefault());
//
//        setUpMapIfNeeded();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        setUpMapIfNeeded();
//    }
//
//    private void setUpMapIfNeeded() {
//        // Do a null check to confirm that we have not already instantiated the map.
//        if (mMap == null) {
//            // Try to obtain the map from the SupportMapFragment.
//            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
//                    .getMap();
//            // Check if we were successful in obtaining the map.
//            if (mMap != null) {
//                setUpMap();
//            }
//        }
//    }
//
//    private void setUpMap() {
//
//        mMap.setMyLocationEnabled(true);
//        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//        mMap.getUiSettings().setMapToolbarEnabled(false);
//
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//
//            @Override
//            public void onMapClick(LatLng point) {
//                //save current location
//                latLng = point;
//
//                List<Address> addresses = new ArrayList<>();
//                try {
//                    addresses = geocoder.getFromLocation(point.latitude, point.longitude,1);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                android.location.Address address = addresses.get(0);
//
//                if (address != null) {
//                    StringBuilder sb = new StringBuilder();
//                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++){
//                        sb.append(address.getAddressLine(i) + "\n");
//                    }
//                    Toast.makeText(MapsActivity.this, sb.toString(), Toast.LENGTH_LONG).show();
//                }
//
//                //remove previously placed Marker
//                if (marker != null) {
//                    marker.remove();
//                }
//
//                //place marker where user just clicked
//                marker = mMap.addMarker(new MarkerOptions().position(point).title("Marker")
//                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
//
//            }
//        });
//
//    }
//}
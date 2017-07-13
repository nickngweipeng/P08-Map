package com.example.nickng.p08_map;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

//    Button btnNorth, btnCentral, btnEast;
    Spinner spn;
    private GoogleMap map;
    private Marker north, central, east;

    ArrayList<String> al = new ArrayList<String>();
    ArrayAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)fm.findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                LatLng poi_north = new LatLng(1.4543811, 103.8314238);
                LatLng poi_central = new LatLng(1.2978023, 103.8474414);
                LatLng poi_east = new LatLng(1.3500005, 103.9338305);

                north = map.addMarker(new MarkerOptions()
                        .position(poi_north)
                        .title("HQ - North")
                        .snippet("Block 333, Admiralty Ave 3, 765654")
                        .icon(BitmapDescriptorFactory.fromResource(android.R.drawable.star_big_on)));

                central = map.addMarker(new MarkerOptions()
                        .position(poi_central)
                        .title("Central")
                        .snippet("Block 3A, Orchard Ave 3, 134542")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                east = map.addMarker(new MarkerOptions()
                        .position(poi_east)
                        .title("East")
                        .snippet("Block 555, Tampines Ave 3, 287788")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                UiSettings ui = map.getUiSettings();
                ui.setCompassEnabled(true);
                ui.setZoomControlsEnabled(true);

                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

                if(permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                } else {
                    Log.e("GMap - Permission", "GPS access has not been granted");
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
                }
                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if(marker.equals(north)){
                            Toast.makeText(MainActivity.this, north.getTitle().toString(), Toast.LENGTH_SHORT).show();
                        } else if(marker.equals(central)){
                            Toast.makeText(MainActivity.this, central.getTitle().toString(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, east.getTitle().toString(), Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
            }
        });

        spn = (Spinner)findViewById(R.id.spinner);

        String[] strRegion = getResources().getStringArray(R.array.region);
        al.addAll(Arrays.asList(strRegion));
        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al);
        spn.setAdapter(aa);

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spn.getSelectedItemPosition() == 1){
                    LatLng poi_north = new LatLng(1.4543811, 103.8314238);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_north, 15));
                } else if(spn.getSelectedItemPosition() == 2){
                    LatLng poi_central = new LatLng(1.2978023, 103.8474414);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_central, 15));
                } else if(spn.getSelectedItemPosition() == 3){
                    LatLng poi_east = new LatLng(1.3500005, 103.9338305);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_east, 15));
                } else {
                    LatLng poi_default = new LatLng(1.3521, 103.8198);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_default, 10));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        btnNorth = (Button)findViewById(R.id.btnNorth);
//        btnCentral = (Button)findViewById(R.id.btnCentral);
//        btnEast = (Button)findViewById(R.id.btnEast);
//
//        btnNorth.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LatLng poi_north = new LatLng(1.4543811, 103.8314238);
//                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_north, 15));
//
//            }
//        });
//
//        btnCentral.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LatLng poi_central = new LatLng(1.2978023, 103.8474414);
//                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_central, 15));
//
//            }
//        });
//
//        btnEast.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LatLng poi_east = new LatLng(1.3500005, 103.9338305);
//                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_east, 15));
//
//            }
//        });
    }
}

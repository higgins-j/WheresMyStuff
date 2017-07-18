package edu.gatech.cs2340.wheresmystuff.controller;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;

import com.google.android.gms.maps.*;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.gatech.cs2340.wheresmystuff.R;
import edu.gatech.cs2340.wheresmystuff.model.Item;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference itemReference;
    private ArrayList<Item> filteredItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Item.Status filter = (Item.Status) getIntent().getSerializableExtra("filter");

        // inside your activity (if you did not enable transitions in your theme)
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        // set an exit transition
        getWindow().setExitTransition(new Slide());
        getWindow().setEnterTransition(new Slide());

        setContentView(R.layout.activity_maps);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_clear_white_24dp);
        }


        itemReference = FirebaseDatabase.getInstance().getReference().child("items");
        itemReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                filteredItemList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Log.d("onDataChange:", item.getValue(Item.class).getTitle());
                    if (item.getValue(Item.class).getStatusVal() == filter) {
                        filteredItemList.add(item.getValue(Item.class));
                    }
                }
                addMapMarkers(filteredItemList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        addMapMarkers(filteredItemList);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    /**
     * Helper method for adding a list of Items to the Google Map
     *
     * @param itemList the list of Items to add to the Map
     */
    private void addMapMarkers(ArrayList<Item> itemList) {
        if (mMap != null) {
            mMap.clear();
            for (Item i : itemList) {
                Log.d("onMapReady:", "Added item " + i.getTitle());
                mMap.addMarker(
                        new MarkerOptions()
                                .position(i.getLatLngVal())
                                .title(i.getTitle())
                                .snippet(i.getDescription()));
            }
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(filteredItemList.get(filteredItemList.size() - 1).getLatLngVal()));
    }
}

package edu.gatech.cs2340.wheresmystuff.controller;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.gatech.cs2340.wheresmystuff.R;
import edu.gatech.cs2340.wheresmystuff.model.Item;

/**
 * A dialog to create a new item listing
 */
public class AddItemActivity extends AppCompatActivity {

    private EditText textTitle;
    private EditText textDescription;
    private EditText textLocation;
    private Spinner spinnerItemType;
    private Spinner spinnerCategory;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        textTitle = (EditText) findViewById(R.id.text_item_title);
        textDescription = (EditText) findViewById(R.id.text_item_description);
        textLocation = (EditText) findViewById(R.id.text_item_location);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_clear_white_24dp);
        }

        spinnerItemType = (Spinner) findViewById(R.id.spinnerItemType);
        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);

        ArrayAdapter<Item.Status> accountTypeArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, Item.Status.values());
        accountTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerItemType.setAdapter(accountTypeArrayAdapter);
        spinnerItemType.setSelection(1);

        ArrayAdapter<Item.Category> categoryArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, Item.Category.values());
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryArrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.action_save) {
            if (tryCreateItem()) {
                finish();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Tries to add a new item to the Firebase database
     * If required fields in the form are empty, the errors are
     * presented and no actual data is added to the database
     *
     * @return if the Item was created successfully
     */
    private boolean tryCreateItem() {
        // Reset errors.
        textTitle.setError(null);

        // Store values at the time of the login attempt.
        String title = textTitle.getText().toString();
        String description = textDescription.getText().toString();
        String location = textLocation.getText().toString();

        boolean cancel = false;
        View focusView = null;


        if (TextUtils.isEmpty(location)) {
            textLocation.setError("This field is required");
            focusView = textLocation;
            cancel = true;
        }
        if (TextUtils.isEmpty(title)) {
            textTitle.setError("This field is required");
            focusView = textTitle;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
            return false;
        } else {


            Item.Category itemCategory = (Item.Category) spinnerCategory.getSelectedItem();
            Item.Status itemStatus = (Item.Status) spinnerItemType.getSelectedItem();

            Item item = new Item(title, description, itemCategory, itemStatus, FirebaseAuth.getInstance().getCurrentUser().getUid(), 0, new LatLng(0, 0));

            String key = mDatabase.child("items").push().getKey();

            mDatabase.child("items").child(key).setValue(item);
            //TODO: add item key to User's item list

            if (location != null && !location.equals("")) {
                new GeocoderTask(key).execute(location);
            }

            Log.d("AddItem", "mDatabase:setValue");
            return true;
        }

    }

    private class GeocoderTask extends AsyncTask<String, Void, Address> {

        private String itemID;

        public GeocoderTask(String itemID) {
            this.itemID = itemID;
        }

        @Override
        protected Address doInBackground(String... locationName) {
            // Creating an instance of Geocoder class
            Geocoder geocoder = new Geocoder(getBaseContext());
            List<Address> addresses = null;

            try {
                // Getting a maximum of 3 Address that matches the input text
                addresses = geocoder.getFromLocationName(locationName[0], 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses.get(0);
        }

        @Override
        protected void onPostExecute(Address address) {
            ArrayList<Double> latLng = new ArrayList<>();
            latLng.add(address.getLatitude());
            latLng.add(address.getLongitude());

            mDatabase.child("items").child(itemID).child("latLng").setValue(latLng);
        }
    }
}

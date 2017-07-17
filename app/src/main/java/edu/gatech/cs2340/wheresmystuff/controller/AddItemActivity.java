package edu.gatech.cs2340.wheresmystuff.controller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
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
import edu.gatech.cs2340.wheresmystuff.model.ItemBuilder;

/**
 * A dialog to create a new item listing
 */
public class AddItemActivity extends AppCompatActivity {

    private View mAddItemView;
    private View mProgressView;

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

        mAddItemView = findViewById(R.id.add_item_form);
        mProgressView = findViewById(R.id.login_progress);
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
            tryCreateItem();
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
    private void tryCreateItem() {
        // Reset errors.
        textTitle.setError(null);

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
        } else {
            showProgress(true);

            new GeocoderTask(title, description, location).execute(location);
        }

    }

    private class GeocoderTask extends AsyncTask<String, Void, Address> {

        private String itemID;
        private String title;
        private String description;

        public GeocoderTask(String title, String description, String itemID) {
            this.itemID = itemID;
            this.title = title;
            this.description = description;
        }

        @Override
        protected Address doInBackground(String... locationName) {
            // Creating an instance of Geocoder class
            Geocoder geocoder = new Geocoder(getBaseContext());
            List<Address> addresses = null;

            try {
                // Getting a maximum of 3 Address that matches the input text
                Log.d("Geocoder:doInBackground", locationName[0]);
                addresses = geocoder.getFromLocationName(locationName[0], 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return addresses.size() == 0
                    ? null
                    : addresses.get(0);
        }

        @Override
        protected void onPostExecute(Address address) {
            if (address == null) {
                textLocation.setError("Could not understand location");
                textLocation.requestFocus();
                showProgress(false);
            } else {
                Item.Category itemCategory = (Item.Category) spinnerCategory.getSelectedItem();
                Item.Status itemStatus = (Item.Status) spinnerItemType.getSelectedItem();
//                Item item = new Item(title, description, itemCategory, itemStatus, FirebaseAuth.getInstance().getCurrentUser().getUid(), 0, new LatLng(0, 0));

                Item item = new ItemBuilder()
                        .setTitle(title)
                        .setDescription(description)
                        .setCategory(itemCategory)
                        .setStatus(itemStatus)
                        .setUser(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setLatLng(new LatLng(address.getLatitude(), address.getLongitude()))
                        .build();

                //TODO: add item key to User's item list

                String key = mDatabase.child("items").push().getKey();

                mDatabase.child("items").child(key).setValue(item);

                showProgress(false);
                finish();
            }
        }
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mAddItemView.setVisibility(show ? View.GONE : View.VISIBLE);
            mAddItemView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mAddItemView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mAddItemView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}

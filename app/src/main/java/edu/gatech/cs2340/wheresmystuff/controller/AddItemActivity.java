package edu.gatech.cs2340.wheresmystuff.controller;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Slide;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.gatech.cs2340.wheresmystuff.R;
import edu.gatech.cs2340.wheresmystuff.model.Item;

/**
 * A dialog to create a new item listing
 */
public class AddItemActivity extends AppCompatActivity {

    private EditText mTitle;
    private Spinner mSpinner;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Slide());
        getWindow().setEnterTransition(new Slide());

        setContentView(R.layout.activity_add_item);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mTitle = (EditText) findViewById(R.id.text_item_title);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_clear_white_24dp);
        }

        mSpinner = (Spinner) findViewById(R.id.spinnerItemType);


        ArrayAdapter<Item.Status> accountTypeArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, Item.Status.values());
        accountTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(accountTypeArrayAdapter);
        mSpinner.setSelection(1);
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
        mTitle.setError(null);

        // Store values at the time of the login attempt.
        String title = mTitle.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if(TextUtils.isEmpty(title)) {
            mTitle.setError("This field is required");
            focusView = mTitle;
            cancel = true;
        } // else if its not a valid title?

        if (cancel) {
            focusView.requestFocus();
            return false;
        } else {
            Item.Status itemStatus = (Item.Status) mSpinner.getSelectedItem();

            Item item = new Item(title, "",Item.Category.TOY, itemStatus, FirebaseAuth.getInstance().getCurrentUser().getUid(), 0);

            String key = mDatabase.child("items").push().getKey();

            mDatabase.child("items").child(key).setValue(item);
            //TODO: add item key to User's item list

            Log.d("AddItem", "mDatabase:setValue");
            return true;
        }

    }
}

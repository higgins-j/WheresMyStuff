package edu.gatech.cs2340.wheresmystuff.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import edu.gatech.cs2340.wheresmystuff.R;
import edu.gatech.cs2340.wheresmystuff.model.Item;

/**
 * The main screen of the app that the user is taken to after logging in
 */
public class App extends AppCompatActivity {

    private DatabaseReference mItems;
    private Spinner mSpinner;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    private final ArrayList<String> lostItemList = new ArrayList<>();
    private final ArrayList<String> foundItemList = new ArrayList<>();
    private final ArrayList<String> neededItemList = new ArrayList<>();

    private ArrayList<String> selectedItemList = new ArrayList<>();
    private ArrayList<String> visibleItemList = new ArrayList<>();

    private boolean searching = false;
    private String lastText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mSpinner = new Spinner(getApplicationContext());
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                resetListViewToFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<Item.Status> filterTypeArrayAdapter = new ArrayAdapter<>(
                this, R.layout.spinner_app_filter, Item.Status.values());
        filterTypeArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        mSpinner.setAdapter(filterTypeArrayAdapter);
        mSpinner.setSelection(1);
        toolbar.addView(mSpinner);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);
                startActivity(intent);
            }
        });

        listView = (ListView) findViewById(R.id.list_items);
        arrayAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                visibleItemList);
        listView.setAdapter(arrayAdapter);
        //final ArrayList<String> itemList = new ArrayList<>();

        mItems = FirebaseDatabase.getInstance().getReference().child("items");
        mItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lostItemList.clear();
                foundItemList.clear();
                neededItemList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    switch (item.getValue(Item.class).getStatusVal()) {
                        case LOST:
                            lostItemList.add(item.getValue(Item.class).getTitle());
                            break;
                        case FOUND:
                            foundItemList.add(item.getValue(Item.class).getTitle());
                            break;
                        case NEEDED:
                            neededItemList.add(item.getValue(Item.class).getTitle());
                            break;
                    }
                }
                if (!searching) {
                    resetListViewToFilter();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Method used to reset the visible item list to the current filter
     */
    private void resetListViewToFilter() {
        visibleItemList.clear();
        switch ((Item.Status) mSpinner.getSelectedItem()) {
            case LOST:
                visibleItemList.addAll(lostItemList);
                break;
            case FOUND:
                visibleItemList.addAll(foundItemList);
                break;
            case NEEDED:
                visibleItemList.addAll(neededItemList);
                break;
        }
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_app, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        MenuItemCompat.OnActionExpandListener expandListener = new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                searching = true;
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                searching = false;

                resetListViewToFilter();

                return true;
            }
        };

        SearchView.OnQueryTextListener textListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (lastText.length() > newText.length()) {
                    resetListViewToFilter();
                }

                for (Iterator<String> iterator = visibleItemList.iterator(); iterator.hasNext(); ) {
                    String currentString = iterator.next();
                    boolean contains = false;
                    for (String split : currentString.split(" ")) {
                        if (split.contains(newText)) {
                            contains = true;
                        }
                    }
                    if (!contains) {
                        iterator.remove();
                    }
                }

                arrayAdapter.notifyDataSetChanged();
                lastText = newText;

                return false;
            }
        };

        searchView.setOnQueryTextListener(textListener);
        MenuItemCompat.setOnActionExpandListener(searchItem, expandListener);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            finish();
            return true;
        } else if (id == R.id.action_map_view) {
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            intent.putExtra("filter", (Item.Status) mSpinner.getSelectedItem());
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}

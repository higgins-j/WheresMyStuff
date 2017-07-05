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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import edu.gatech.cs2340.wheresmystuff.R;

/**
 * The main screen of the app that the user is taken to after logging in
 */
public class App extends AppCompatActivity {

    private DatabaseReference mItems;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    private final ArrayList<String> itemList = new ArrayList<>();
    private ArrayList<String> visibleItemList = new ArrayList<>();

    private boolean searching = false;
    private String lastText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);
                startActivity(intent);
            }
        });

        listView = (ListView) findViewById(R.id.list_items);
        arrayAdapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                visibleItemList);
        listView.setAdapter(arrayAdapter);
        //final ArrayList<String> itemList = new ArrayList<>();

        mItems = FirebaseDatabase.getInstance().getReference().child("items");
        mItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                itemList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    itemList.add((String) item.getValue());
                }
                if (!searching){
                    visibleItemList.clear();
                    visibleItemList.addAll(itemList);
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

                visibleItemList.clear();
                visibleItemList.addAll(itemList);
                arrayAdapter.notifyDataSetChanged();

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
                    visibleItemList.clear();
                    visibleItemList.addAll(itemList);
                }

                for (Iterator<String> iterator = visibleItemList.iterator(); iterator.hasNext();) {
                    String currentString = iterator.next();
                    boolean contains = false;
                    for (String split: currentString.split(" ")) {
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
        }

        return super.onOptionsItemSelected(item);
    }
}

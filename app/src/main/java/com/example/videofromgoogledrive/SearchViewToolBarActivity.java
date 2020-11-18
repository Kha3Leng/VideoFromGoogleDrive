package com.example.videofromgoogledrive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchViewToolBarActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<String> items;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view_tool_bar);

        lv = findViewById(R.id.searchViewMenu);
        items = new ArrayList<>();
        items.add("Apple");
        items.add("Banana");
        items.add("Pineapple");
        items.add("Orange");
        items.add("Lychee");
        items.add("Gavava");
        items.add("Peech");
        items.add("Melon");
        items.add("Watermelon");
        items.add("Papaya");

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, items);
        lv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchViewItem = menu.findItem(R.id.searchBar);
        SearchView sv = (SearchView) MenuItemCompat.getActionView(searchViewItem);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sv.clearFocus();
                if (items.contains(query)) {
                    adapter.getFilter().filter(query);
                } else {
                    Toast.makeText(SearchViewToolBarActivity.this, "Cannot find anything like that", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
package com.example.videofromgoogledrive;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchViewListView extends Fragment {


    private ListView listView;
    private ArrayList<String> items;
    private ArrayAdapter itemsAdapter;
    private SearchView searchView;

    public SearchViewListView() {
        // Required empty public constructor
    }

    public static SearchViewListView newInstance() {
        SearchViewListView myFragment = new SearchViewListView();

//        Bundle args = new Bundle();
//        myFragment.setArguments(args);

        return myFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_view_list_view, container, false);
        listView = view.findViewById(R.id.listView);

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

        itemsAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);

        searchView = view.findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (items.contains(query))
                    itemsAdapter.getFilter().filter(query);
                else {
                    Toast.makeText(getContext(),
                            R.string.hello,
                            Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return view;
    }
}
package com.example.videofromgoogledrive.PlayWithFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.videofromgoogledrive.R;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class LeftFragment extends Fragment {

    private ArrayList<String> list;
    private ArrayAdapter adapter;
    private ListView listView;

    public LeftFragment() {
        // Required empty public constructor
    }


    public static LeftFragment newInstance() {
        LeftFragment fragment = new LeftFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left, container, false);
        listView = view.findViewById(R.id.left);
        list = new ArrayList<String>();
        list.add("Apple");
        list.add("Banana");
        list.add("Pineapple");
        list.add("Orange");
        list.add("Lychee");
        list.add("Gavava");
        list.add("Peech");
        list.add("Melon");
        list.add("Watermelon");
        list.add("Papaya");
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RightFragment rightFragment = (RightFragment) getFragmentManager().findFragmentById(R.id.rightFragment);
                rightFragment.change(list.get(position));
                listView.setSelector(android.R.color.holo_blue_bright);
            }
        });
        return view;

    }
}


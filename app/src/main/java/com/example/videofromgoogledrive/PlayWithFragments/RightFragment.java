package com.example.videofromgoogledrive.PlayWithFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.videofromgoogledrive.R;


public class RightFragment extends Fragment {

    private TextView tx;

    public RightFragment() {
        // Required empty public constructor
    }

    public static RightFragment newInstance() {
        RightFragment fragment = new RightFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_right, container, false);
        tx = view.findViewById(R.id.right);
        return view;
    }

    public void change(String txt){
        tx.setText(txt);
    }
}
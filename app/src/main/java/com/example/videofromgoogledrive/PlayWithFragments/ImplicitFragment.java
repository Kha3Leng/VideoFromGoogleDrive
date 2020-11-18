package com.example.videofromgoogledrive.PlayWithFragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.videofromgoogledrive.R;

public class ImplicitFragment extends Fragment {



    public ImplicitFragment() {
        // Required empty public constructor
    }

    public static ImplicitFragment newInstance() {
        ImplicitFragment fragment = new ImplicitFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_implicit, container, false);
        EditText phoneNo = view.findViewById(R.id.phone);
        Button call = view.findViewById(R.id.call);
        Button share = view.findViewById(R.id.share);

        EditText urls = view.findViewById(R.id.url);
        Button browse = view.findViewById(R.id.browse);

        Spinner sp = view.findViewById(R.id.dropdown_menu);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_dropdown, android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "You clicked "+adapter.getItem(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri phone;
                String number = phoneNo.getText().toString();
                if (number.trim().isEmpty())
                    phone = Uri.parse("tel:09444000423");
                else
                    phone = Uri.parse("tel:"+phoneNo.getText().toString());
                Intent intent = new Intent(Intent.ACTION_CALL, phone);

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Please give permission to call", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                }else
                    getActivity().startActivity(intent);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                String message = "fuck you";
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "THISISTITLE");
                intent.putExtra(Intent.EXTRA_TEXT, message);

                getActivity().startActivity(Intent.createChooser(intent, "Shared through fu"));
            }
        });

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String urlss = urls.getText().toString();
                if (urlss.trim().isEmpty()){
                    Uri urlll = Uri.parse("https://www.google.com");
                    intent.setData(urlll);
                }else{
                    Uri urllll = Uri.parse("https://"+urlss);
                    intent.setData(urllll);
                }

                if (intent.resolveActivity(getActivity().getPackageManager()) != null)
                    startActivity(intent);
            }
        });
        return view;
    }
}
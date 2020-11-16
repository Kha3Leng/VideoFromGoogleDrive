package com.example.videofromgoogledrive;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

public class WebView extends Fragment {


    private android.webkit.WebView webView;
    public WebView() {
        // Required empty public constructor
    }
    public WebView newInstance() {
        WebView fragment = new WebView();
//        Bundle args = new Bundle();
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
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);
        webView = view.findViewById(R.id.yt);
        WebSettings setting = webView.getSettings();
        setting.setJavaScriptEnabled(true);

        webView.loadUrl("https://www.boredpanda.com");
        webView.setWebViewClient(new WebViewClient());
        return view;
    }
}
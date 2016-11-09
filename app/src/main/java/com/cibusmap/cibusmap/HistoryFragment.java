package com.cibusmap.cibusmap;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {


    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View View= inflater.inflate(R.layout.fragment_history, container, false);

        String url ="http://cibusmap.com/";

        WebView siteview=(WebView)View.findViewById(R.id.webView);

        siteview.getSettings().setJavaScriptEnabled(true);
        siteview.getSettings().setLoadWithOverviewMode(true);
        siteview.getSettings().setUseWideViewPort(true);

        siteview.setWebViewClient(new MywebViewClient());


        siteview.loadUrl(url);

        return View;
    }
    private class MywebViewClient extends WebViewClient {
        @Override
        public  boolean shouldOverrideUrlLoading(WebView view,String url){
            view.loadUrl(url);
            return true;
        }
    }

}

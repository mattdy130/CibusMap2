package com.cibusmap.cibusmap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.analytics.ecommerce.Product;

import org.json.JSONArray;
import org.json.JSONException;

public class Products extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);


        final LinearLayout prodBody =(LinearLayout) findViewById(R.id.prodBody);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String fname =  preferences.getString("fname","");
        String lname =  preferences.getString("lname","");
        String email =  preferences.getString("email","");
        String title =  preferences.getString("title","");
        int rID =  preferences.getInt("rID",1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Response.Listener<String> responseListener = new Response.Listener<String>() {


            LinearLayout prodPane;
            TextView prodName;


            @Override
            public void onResponse(String response) {

                try {


                    JSONArray jsonResponse = new JSONArray(response);
                    for(int x=0; x<jsonResponse.length();x++) {
                        String a = jsonResponse.getJSONObject(x).getString("prod_name");


                        prodPane = new LinearLayout(Products.this);
                        prodName = new TextView(Products.this);
                        prodPane.setOrientation(LinearLayout.VERTICAL);

                        prodBody.addView(prodPane, FrameLayout.LayoutParams.MATCH_PARENT, 250);
                        prodPane.addView(prodName);
                        prodName.setText(a);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

        };
        ProductRequest productRequest = new ProductRequest(rID,responseListener);
        RequestQueue queue = Volley.newRequestQueue(Products.this);
        queue.add(productRequest);




    }

}

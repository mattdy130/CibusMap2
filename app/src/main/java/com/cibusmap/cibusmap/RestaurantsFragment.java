package com.cibusmap.cibusmap;


import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantsFragment extends Fragment {





    public RestaurantsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_restaurants, container, false);

        final LinearLayout restBody =(LinearLayout) view.findViewById(R.id.restBody);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            RelativeLayout imgPane;
            LinearLayout restPane,topPane,midPane,titlePane,botPane;
            TextView title,desc;
            TextView rate,hrs,min,del,pick,res;
            ImageView restImg;
            @Override
            public void onResponse(String response) {
                try {
                    android.app.AlertDialog.Builder Checkbuilder = new android.app.AlertDialog.Builder(RestaurantsFragment.this.getActivity());
                    JSONArray jsonResponse = new JSONArray(response);
                    for(int x=0;x<jsonResponse.length();x++)
                    {
                        restPane = new LinearLayout(RestaurantsFragment.this.getActivity());
                        imgPane = new RelativeLayout(RestaurantsFragment.this.getActivity());
                        topPane = new LinearLayout(RestaurantsFragment.this.getActivity());
                        midPane = new LinearLayout(RestaurantsFragment.this.getActivity());
                        botPane = new LinearLayout(RestaurantsFragment.this.getActivity());
                        titlePane = new LinearLayout(RestaurantsFragment.this.getActivity());
                        restImg = new ImageView(RestaurantsFragment.this.getActivity());
                        title = new TextView(RestaurantsFragment.this.getActivity());
                        desc =  new TextView(RestaurantsFragment.this.getActivity());
                        rate =  new TextView(RestaurantsFragment.this.getActivity());
                        hrs =  new TextView(RestaurantsFragment.this.getActivity());
                        min =  new TextView(RestaurantsFragment.this.getActivity());
                        del = new TextView(RestaurantsFragment.this.getActivity());
                        pick =  new TextView(RestaurantsFragment.this.getActivity());
                        res =  new TextView(RestaurantsFragment.this.getActivity());

                        rate.setGravity(Gravity.CENTER_HORIZONTAL);
                        hrs.setGravity(Gravity.CENTER_HORIZONTAL);
                        min.setGravity(Gravity.CENTER_HORIZONTAL);
                        del.setGravity(Gravity.CENTER_HORIZONTAL);
                        pick.setGravity(Gravity.CENTER_HORIZONTAL);
                        res.setGravity(Gravity.CENTER_HORIZONTAL);

                        restPane.setOrientation(LinearLayout.VERTICAL);
                        topPane.setOrientation(LinearLayout.HORIZONTAL);
                        midPane.setOrientation(LinearLayout.HORIZONTAL);
                        botPane.setOrientation(LinearLayout.HORIZONTAL);
                        titlePane.setOrientation(LinearLayout.VERTICAL);
                        restBody.addView(restPane, FrameLayout.LayoutParams.MATCH_PARENT,300);
                        restPane.addView(topPane, FrameLayout.LayoutParams.MATCH_PARENT,150);
                        restPane.addView(midPane,FrameLayout.LayoutParams.MATCH_PARENT,75);
                        restPane.addView(botPane,FrameLayout.LayoutParams.MATCH_PARENT,75);
                        topPane.addView(imgPane,150, FrameLayout.LayoutParams.MATCH_PARENT);
                        topPane.addView(titlePane, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                        imgPane.addView(restImg, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                        titlePane.addView(title);
                        titlePane.addView(desc);
                        midPane.addView(rate, 100, FrameLayout.LayoutParams.MATCH_PARENT);
                        midPane.addView(hrs,190, FrameLayout.LayoutParams.MATCH_PARENT);
                        midPane.addView(min, 140, FrameLayout.LayoutParams.MATCH_PARENT);
                        botPane.addView(del, 140, FrameLayout.LayoutParams.MATCH_PARENT);
                        botPane.addView(pick, 140, FrameLayout.LayoutParams.MATCH_PARENT);
                        botPane.addView(res, 150, FrameLayout.LayoutParams.MATCH_PARENT);
                        restPane.setBackgroundResource(R.drawable.shapes);
                        topPane.setBackgroundResource(R.drawable.shapes);
                        midPane.setBackgroundResource(R.drawable.shapes);
                        botPane.setBackgroundResource(R.drawable.shapes);
                        imgPane.setBackgroundResource(R.drawable.shapes);
                        restImg.setImageResource(R.drawable.eatsleepburger);
                        LinearLayout.LayoutParams p = (LinearLayout.LayoutParams)restPane.getLayoutParams();
                        p.setMargins(0,0,0,10);

                        restPane.setLayoutParams(p);
                        final String rName = jsonResponse.getJSONObject(x).getString("rest_name");

                        String rDesc = jsonResponse.getJSONObject(x).getString("rest_description");
                        String rRate = jsonResponse.getJSONObject(x).getString("rest_rating");
                        String rOpen = jsonResponse.getJSONObject(x).getString("rest_opentime");
                        String rClose = jsonResponse.getJSONObject(x).getString("rest_closetime");
                        String rMin = jsonResponse.getJSONObject(x).getString("rest_minimum_order");
                        String rDel = jsonResponse.getJSONObject(x).getString("rest_delivery");
                        String rPick = jsonResponse.getJSONObject(x).getString("rest_pickup");
                        String rRes = jsonResponse.getJSONObject(x).getString("rest_reservation");
                        String rDelP = jsonResponse.getJSONObject(x).getString("rest_delivery_price");
                        String rPickP = jsonResponse.getJSONObject(x).getString("rest_pickup_price");
                        String rResP = jsonResponse.getJSONObject(x).getString("rest_reservation_price");
                        String rTime = "Open Hours:\n" + rOpen + "-" + rClose;
                        String Deli = "Delivery:\n" + rDel + ", P" + rDelP;
                        String Pick = "Pick up:\n" + rPick + ", P" + rPickP;
                        String Rese = "Reservation:\n" +  rRes + ", P" + rResP;
                        rMin = "Min Order:\n P" + rMin;
                        rRate ="Rating:\n" +   rRate + "/10";
                        title.setText(rName);
                        desc.setText(rDesc);
                        rate.setText(rRate);
                        hrs.setText(rTime);
                        min.setText(rMin);
                        del.setText(Deli);
                        pick.setText(Pick);
                        res.setText(Rese);

                        final int rID = jsonResponse.getJSONObject(x).getInt("rest_ID");
                        restPane.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(RestaurantsFragment.this.getActivity());
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("title", rName);
                                editor.putInt("rID", rID);
                                editor.apply();

                                Intent productIntent = new Intent(RestaurantsFragment.this.getActivity(),Products.class);
                                RestaurantsFragment.this.getActivity().startActivity(productIntent);
                            }
                        });

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RestaurantsRequest restaurantsRequest = new RestaurantsRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(RestaurantsFragment.this.getActivity());
        queue.add(restaurantsRequest);



        return view;
    }

}

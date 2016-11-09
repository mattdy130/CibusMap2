package com.cibusmap.cibusmap;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.List;

import static com.cibusmap.cibusmap.R.string.title_activity_user_area;


public class UserArea extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;

    SupportMapFragment sMapFragment;
    NavigationView mNavigationView;
    Toolbar toolbar;
    public static final String PREFS_NAME = "LoginPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        if (!isNetworkAvailable()) {
            //Create an alertdialog
            android.app.AlertDialog.Builder Checkbuilder = new android.app.AlertDialog.Builder(UserArea.this);
            Checkbuilder.setIcon(R.drawable.error);
            Checkbuilder.setTitle("Error!");
            Checkbuilder.setMessage("Check Your Internet Connection.");
            //Builder Retry Button

            Checkbuilder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    //Restart The Activity
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                }
            });

            Checkbuilder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            android.app.AlertDialog alert = Checkbuilder.create();
            alert.show();

        }

//////////////////////////

/////////////////////////////


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String fname = preferences.getString("fname", "");
        String lname = preferences.getString("lname", "");
        String email = preferences.getString("email", "");
        String FullName = fname + " " + lname;

        sMapFragment = SupportMapFragment.newInstance();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Home");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // navigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        TextView Name = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.tvName);
        TextView Email = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.tvEmail);
        Name.setText(FullName);
        Email.setText(email);

        sMapFragment.getMapAsync(this);
        android.support.v4.app.FragmentManager sfm = getSupportFragmentManager();
        sfm.beginTransaction().show(sMapFragment).commit();


        if (!sMapFragment.isAdded())
            sfm.beginTransaction().add(R.id.map, sMapFragment).commit();
        else
            sfm.beginTransaction().show(sMapFragment).commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_area, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Restaurant) {
            toolbar.setTitle(" ");
            android.support.v4.app.FragmentManager sfm = getSupportFragmentManager();
            sfm.beginTransaction().hide(sMapFragment).commit();

            RestaurantsFragment RestaurantsFragment = new RestaurantsFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .setCustomAnimations(R.anim.anim_slide_from_left, R.anim.slide_out_from_left)
                    .replace(R.id.RelativeLayout, RestaurantsFragment, RestaurantsFragment.getTag()).commit();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        android.support.v4.app.FragmentManager sfm = getSupportFragmentManager();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (sMapFragment.isAdded()) {
            sfm.beginTransaction().hide(sMapFragment).commit();
        }

        if (id == R.id.nav_Home) {
            //home
            toolbar.setTitle("Home");

            HomeFragment HomeFragment = new HomeFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .setCustomAnimations(R.anim.anim_slide_from_left, R.anim.slide_out_from_left)
                    .replace(R.id.RelativeLayout, HomeFragment, HomeFragment.getTag()).commit();
            if (!sMapFragment.isAdded())
                sfm.beginTransaction().add(R.id.map, sMapFragment).commit();
            else
                sfm.beginTransaction().show(sMapFragment).commit();


        } else if (id == R.id.nav_History) {
            toolbar.setTitle("History");

            HistoryFragment HistoryFragment = new HistoryFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .setCustomAnimations(R.anim.anim_slide_from_left, R.anim.slide_out_from_left)
                    .replace(R.id.RelativeLayout, HistoryFragment, HistoryFragment.getTag()).commit();

        } else if (id == R.id.nav_Account_Settings) {
            toolbar.setTitle("Account Settings");


        } else if (id == R.id.nav_ChangePass) {
            toolbar.setTitle("Change Password");


            ChangePasswordFragment ChangePasswordFragment = new ChangePasswordFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .setCustomAnimations(R.anim.anim_slide_from_left, R.anim.slide_out_from_left)
                    .replace(R.id.RelativeLayout, ChangePasswordFragment, ChangePasswordFragment.getTag()).commit();


        } else if (id == R.id.nav_logout) {


            final ProgressDialog progress;
            progress = ProgressDialog.show(UserArea.this, "Please Wait!", "Logging out...", true, true);

            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor1 = settings.edit();
            editor1.remove("logged");
            editor1.apply();
            finish();

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("fname");
            editor.remove("lname");
            editor.remove("email");
            editor.apply();
            finish();

            progress.dismiss();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private void prepareMovieData() {
        Movie movie = new Movie("Mad Max: Fury Road", "Action & Adventure", "2015");
        movieList.add(movie);

        movie = new Movie("Inside Out", "Animation, Kids & Family", "2015");
        movieList.add(movie);

        movie = new Movie("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
        movieList.add(movie);

        movie = new Movie("Shaun the Sheep", "Animation", "2015");
        movieList.add(movie);

        movie = new Movie("The Martian", "Science Fiction & Fantasy", "2015");
        movieList.add(movie);

        movie = new Movie("Mission: Impossible Rogue Nation", "Action", "2015");
        movieList.add(movie);

        movie = new Movie("Up", "Animation", "2009");
        movieList.add(movie);

        movie = new Movie("Star Trek", "Science Fiction", "2009");
        movieList.add(movie);

        movie = new Movie("The LEGO Movie", "Animation", "2014");
        movieList.add(movie);

        movie = new Movie("Iron Man", "Action & Adventure", "2008");
        movieList.add(movie);

        movie = new Movie("Aliens", "Science Fiction", "1986");
        movieList.add(movie);

        movie = new Movie("Chicken Run", "Animation", "2000");
        movieList.add(movie);

        movie = new Movie("Back to the Future", "Science Fiction", "1985");
        movieList.add(movie);

        movie = new Movie("Raiders of the Lost Ark", "Action & Adventure", "1981");
        movieList.add(movie);

        movie = new Movie("Goldfinger", "Action & Adventure", "1965");
        movieList.add(movie);

        movie = new Movie("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
        movieList.add(movie);

        mAdapter.notifyDataSetChanged();
    }
}




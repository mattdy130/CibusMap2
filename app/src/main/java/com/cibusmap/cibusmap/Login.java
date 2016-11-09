package com.cibusmap.cibusmap;
//hahahaaa
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    public static final String PREFS_NAME= "LoginPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
        if(settings.getString("logged", "").equals("logged")){
            Intent intent = new Intent(Login.this, UserArea.class);
            startActivity(intent);
        }
        else {
            if (!isNetworkAvailable()) {
                //Create an alert
                android.app.AlertDialog.Builder Checkbuilder = new android.app.AlertDialog.Builder(Login.this);
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


            final EditText etEmail = (EditText) findViewById(R.id.etEmail);
            final EditText etPassword = (EditText) findViewById(R.id.etPassword);
            final Button loginBtn = (Button) findViewById(R.id.loginBtn);
            final Button signupBtn = (Button) findViewById(R.id.signupBtn);


            signupBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent registerIntent = new Intent(Login.this, Register.class);
                    Login.this.startActivity(registerIntent);

                }
            });

            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String email = etEmail.getText().toString();
                    final String password = etPassword.getText().toString();

                    final ProgressDialog progress;
                    progress = ProgressDialog.show(Login.this, "Please Wait!", "Logging in...", true, true);

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (!email.isEmpty() && !password.isEmpty()) {
                                    if (success) {
                                        progress.dismiss();
                                        String fname = jsonResponse.getString("fname");
                                        String lname = jsonResponse.getString("lname");
                                        String cnumber = jsonResponse.getString("phone");

                                        AlertDialog.Builder build = new AlertDialog.Builder(Login.this);
                                        build.setMessage("Login Success")
                                                .setPositiveButton("Thank you", null)
                                                .create()
                                                .show();

                                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                                        SharedPreferences.Editor editor1 = settings.edit();
                                        editor1.putString("logged", "logged");
                                        editor1.apply();

                                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("fname", fname);
                                        editor.putString("lname", lname);
                                        editor.putString("email", email);
                                        editor.putString("CellphoneNumber", cnumber);
                                        editor.apply();
                                        Intent intent = new Intent(Login.this, UserArea.class);
                                        Login.this.startActivity(intent);

                                    } else {
                                        progress.dismiss();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                        builder.setMessage("Login Failed")
                                                .setNegativeButton("Retry", null)
                                                .create()
                                                .show();
                                    }
                                } else {
                                    progress.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                    builder.setMessage("Fields are empty!")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Login.this);
                    queue.add(loginRequest);
                }


            });
        }
    }
    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager=(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo=connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo !=null;
    }


}



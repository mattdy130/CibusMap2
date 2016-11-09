package com.cibusmap.cibusmap;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        final EditText etCnumber = (EditText) findViewById(R.id.etCnumber);
        final EditText etFname = (EditText) findViewById(R.id.etFname);
        final EditText etLname = (EditText) findViewById(R.id.etLname);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bRegister = (Button) findViewById(R.id.Registerbtn);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fname = etFname.getText().toString();
                final String lname = etLname.getText().toString();
                final String Email = etEmail.getText().toString();
                final String CellphoneNumber = etCnumber.getText().toString();
                final String password = etPassword.getText().toString();

                ProgressDialog progress;
                progress = new ProgressDialog(Register.this);
                progress.setTitle("Please Wait!!");
                progress.setMessage("Creating....");
                progress.setCancelable(true);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(!Email.isEmpty() && !password.isEmpty() && !fname.isEmpty() && !lname.isEmpty() && !CellphoneNumber.isEmpty()) {
                                if (success) {
                                    Intent intent = new Intent(Register.this, Login.class);
                                    Register.this.startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                                    builder.setMessage("Register Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }
                            }
                            else{
                                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Register.this);
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

                RegisterRequest registerRequest = new RegisterRequest(fname,lname, Email,CellphoneNumber,password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Register.this);
                queue.add(registerRequest);


            }
        });
    }


}

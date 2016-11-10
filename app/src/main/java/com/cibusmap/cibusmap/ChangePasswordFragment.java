package com.cibusmap.cibusmap;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment {


    public ChangePasswordFragment() {
        // Required empty public constructor


    }

     EditText etOldPassword ;
     EditText etNewPassword;
    Button bChange;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_change_password, container, false);

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ChangePasswordFragment.this.getActivity());
         etOldPassword = (EditText) view.findViewById(R.id.etOldPassword);
         etNewPassword = (EditText) view.findViewById(R.id.etNewPassword);
         bChange = (Button) view.findViewById(R.id.bChange);

        bChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =  preferences.getString("email","");
                final String OldPassword = etOldPassword.getText().toString();
                final String NewPassword = etNewPassword.getText().toString();

                final ProgressDialog progress;
                progress = ProgressDialog.show(ChangePasswordFragment.this.getActivity(),"Please Wait!","Changing Password...",true,true);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(!OldPassword.isEmpty() && !NewPassword.isEmpty()) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    Toast.makeText(ChangePasswordFragment.this.getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                    progress.dismiss();
                                    Intent intent = new Intent(getActivity(), UserArea.class);
                                    startActivity(intent);

                                } else {
                                    progress.dismiss();
                                    Toast.makeText(ChangePasswordFragment.this.getActivity(), "Error Old Password", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            progress.dismiss();
                            Toast.makeText(ChangePasswordFragment.this.getActivity(), "Fields are empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                UpdatePassRequest UpdatePassRequest = new UpdatePassRequest(email,OldPassword,NewPassword,responseListener);
                RequestQueue queue = Volley.newRequestQueue(ChangePasswordFragment.this.getActivity());
                queue.add(UpdatePassRequest);

            }
        });



        return view;


    }

}

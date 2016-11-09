package com.cibusmap.cibusmap;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://cibusmap.com/index.php/AppForms/register";
    private Map<String,String> params;
    public RegisterRequest(String fname,String lname, String Email, String CellphoneNumber,  String password, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("email", Email);
        params.put("password", password);
        params.put("fname", fname);
        params.put("lname", lname);
        params.put("CellphoneNumber", CellphoneNumber);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}


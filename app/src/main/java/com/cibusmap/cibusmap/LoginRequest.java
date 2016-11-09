package com.cibusmap.cibusmap;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class LoginRequest extends StringRequest {
    private static final String Login_REQUEST_URL = "http://cibusmap.com/index.php/AppForms/login";
    private Map<String,String> params;

    public LoginRequest(String email, String Password, Response. Listener<String> listener){
        super(Request.Method.POST, Login_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("email",email);
        params.put("password",Password);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

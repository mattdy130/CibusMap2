package com.cibusmap.cibusmap;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class UpdatePassRequest extends StringRequest {
    private static final String Login_REQUEST_URL = "http://cibusmap.com/index.php/AppForms/changepass";
    private Map<String,String> params;

    public UpdatePassRequest(String email, String OldPassword,String NewPassword, Response. Listener<String> listener){
        super(Request.Method.POST, Login_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("email",email);
        params.put("OldPassword",OldPassword);
        params.put("NewPassword",NewPassword);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
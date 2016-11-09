package com.cibusmap.cibusmap;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class ProductRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://cibusmap.com/index.php/AppForms/getproduct";
    private Map<String,String> params;
    public ProductRequest(int rID, Response.Listener<String> responseListener) {
        super(Method.POST, REGISTER_REQUEST_URL, responseListener, null);
        params = new HashMap<>();
        String ID = Integer.toString(rID);
        params.put("rID", ID);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
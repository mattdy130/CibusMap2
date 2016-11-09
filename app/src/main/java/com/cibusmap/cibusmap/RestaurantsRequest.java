package com.cibusmap.cibusmap;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by STUART on 11/4/2016.
 */

public class RestaurantsRequest extends StringRequest {
    private static final String Login_REQUEST_URL = "http://cibusmap.com/index.php/AppForms/getRest";


    public RestaurantsRequest(Response. Listener<String> listener){
        super(Request.Method.POST, Login_REQUEST_URL,listener,null);
    }


}

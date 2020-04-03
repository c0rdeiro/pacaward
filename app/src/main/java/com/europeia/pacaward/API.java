package com.europeia.pacaward;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class API {

    private JSONArray jsonArray;


    public JSONArray getJson(String field) {
        String url = String.format("https://api.fidel.uk/v1/%s/", field);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                       try{
                           Log.i("API",response.toString());
                           jsonArray = response.getJSONArray("items");
                       }
                       catch (JSONException e){ e.printStackTrace();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("fail", String.valueOf(error));
            }
        }) {
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("Fidel-Key", "sk_test_a79c137a-3fe7-4e74-8d21-f47f1108806f");
                return headers;
            }
        };

        return jsonArray;
    }
}
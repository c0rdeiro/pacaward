package com.europeia.pacaward;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

class API {

    private static final String TAG = "API";
    private static String url = "https://api.fidel.uk/v1/%s/";


    static void call(Context context, String endpoint, Queue queue, final VolleyCallback callback) {
        Log.i(TAG, "call");
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, String.format(url, endpoint), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    callback.onSuccess(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Fidel-Key", "sk_test_6c933a6f-4db4-42bb-a5ee-aba664d07512");
                return headers;
            }
        };
        queue.addToRequestQueue(req);
    }

    static void delete(Context context, String endpoint, Queue queue) {
        StringRequest req = new StringRequest(Request.Method.DELETE, String.format(url, endpoint),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "onResponse: delete success");

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                    }
                }
        ) {
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Fidel-Key", "sk_test_6c933a6f-4db4-42bb-a5ee-aba664d07512");
                return headers;
            }
        };
        queue.addToRequestQueue(req);
    }



}
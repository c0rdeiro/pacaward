package com.europeia.pacaward;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

class API {

    private static final String TAG = "API";
    private static String url = "https://api.fidel.uk/v1/%s/";


    static void get(String endpoint, Queue queue, final VolleyCallback callback) {
        Log.i(TAG, "get");
        JsonObjectRequest req = new JsonObjectRequest(String.format(url, endpoint), null, new Response.Listener<JSONObject>() {
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
                headers.put("Fidel-Key", "sk_test_a79c137a-3fe7-4e74-8d21-f47f1108806f");
                return headers;
            }
        };
        queue.addToRequestQueue(req);
    }

}
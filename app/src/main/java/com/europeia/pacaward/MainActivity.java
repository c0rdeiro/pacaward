package com.europeia.pacaward;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button profilebtn;
    Button transactionbtn;
    Button offersbtn;
    Button cardsbtn;
    private ArrayList<String> offersList = new ArrayList<String>();
    private RequestQueue offersQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        transactionbtn = findViewById(R.id.transactionsbtn);
        offersbtn = findViewById(R.id.offersbtn);
        cardsbtn = findViewById(R.id.cardsbtn);
        profilebtn = findViewById(R.id.profilebtn);
        ListView offersListView = findViewById(R.id.offersLv);

        offersQueue = Volley.newRequestQueue(this);
        initializeListView(offersListView);

    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void initializeListView(ListView listView){
        getOffers();

        ArrayAdapter<String> offersAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, offersList);

        listView.setAdapter(offersAdapter);


    }
    private void getOffers() {
        String url = "https://api.fidel.uk/v1/offers/";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //offersList.add(response);
                        Log.i("success", String.valueOf(response));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("fail", String.valueOf(error));
                error.printStackTrace();
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
        offersQueue.add(jsonObjectRequest);
    }
    }

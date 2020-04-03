package com.europeia.pacaward;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "Main Activity";
    Button profilebtn;
    Button transactionbtn;
    Button offersbtn;
    Button cardsbtn;
    private ArrayList<String> imageUrls = new ArrayList<>();
    private ArrayList<String> brandNames = new ArrayList<>();
    private ArrayList<String> brandLocations = new ArrayList<>();
    private ArrayList<String> offerDesc = new ArrayList<>();
    private API api = new API();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        transactionbtn = findViewById(R.id.transactionsbtn);
        offersbtn = findViewById(R.id.offersbtn);
        cardsbtn = findViewById(R.id.cardsbtn);
        profilebtn = findViewById(R.id.profilebtn);
        getOffers();

    }
    private void getOffers() {

        try {
            JSONArray jsonArray =  api.getJson("offers");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                imageUrls.add(object.getString("brandLogoURL"));
                brandNames.add(object.getString("brandName"));
                //brandLocations.add(object.getString(""));
                offerDesc.add(object.getString("name"));
            }

            Log.i(TAG, brandNames.toString());
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        initRecyclerView();
    }


    private void initRecyclerView(){
        Log.i("HERE", "initRecyclerView");
        Log.i("KAPPA",brandNames.toString());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.offersRV);
        recyclerView.setLayoutManager(layoutManager);
        Log.i(TAG, imageUrls.toString());
        OffersRecyclerViewAdapter adapter = new OffersRecyclerViewAdapter(this, imageUrls, brandNames, brandLocations, offerDesc);
        recyclerView.setAdapter(adapter);
    }
}

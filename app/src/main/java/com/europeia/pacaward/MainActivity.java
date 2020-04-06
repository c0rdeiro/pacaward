package com.europeia.pacaward;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "Main Activity";
    Button profileBtn;
    Button transactionBtn;
    Button offersBtn;
    Button cardsBtn;
    private ArrayList<String> imageUrls = new ArrayList<>();
    private ArrayList<String> brandNames = new ArrayList<>();
    private ArrayList<String> brandIds = new ArrayList<>();
    private ArrayList<String> offerDesc = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        transactionBtn = findViewById(R.id.transactionsbtn);
        offersBtn = findViewById(R.id.offersbtn);
        cardsBtn = findViewById(R.id.cardsbtn);
        profileBtn = findViewById(R.id.profilebtn);
        getOffers();

    }

    private void onOffers(JSONObject obj) {
        try {
            JSONArray jsonArray = obj.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                imageUrls.add(object.getString("brandLogoURL"));
                brandNames.add(object.getString("brandName"));
                brandIds.add(object.getString("brandId"));
                offerDesc.add(object.getString("name"));
            }

            Log.i(TAG, brandNames.toString());
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        initRecyclerView();
    }

    private void getOffers() {
        final VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                onOffers(result);
            }

            @Override
            public void onError(String result) {
                Log.e("getOffers",result);
            }
        };
        API.get("offers", Queue.getInstance(getApplicationContext()), callback);
    }

    private void initRecyclerView() {
        Log.i("HERE", "initRecyclerView");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.offersRV);
        recyclerView.setLayoutManager(layoutManager);
        Log.i(TAG, imageUrls.toString());
        OffersRecyclerViewAdapter adapter = new OffersRecyclerViewAdapter(this, imageUrls, brandNames,  offerDesc); //brandLocations,
        recyclerView.setAdapter(adapter);
    }
}
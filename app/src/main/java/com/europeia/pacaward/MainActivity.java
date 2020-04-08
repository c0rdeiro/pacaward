package com.europeia.pacaward;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
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

    private ArrayList<String> offersListGroup = new ArrayList<>();
    private LinearLayoutManager layoutManagerGroup;
    private OffersGroupAdp adapterGroup;

    private Button profileBtn;
    private Button transactionBtn;
    private Button offersBtn;
    private Button cardsBtn;
    private RecyclerView rvgroup;
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
        rvgroup = findViewById(R.id.offersgroupRV);
        populateCats();
        getOffers();

    }

    private void getOffers() {
        Log.d(TAG,"getoffers");
        final VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                onOffers(result);
                //getCategories();
            }

            @Override
            public void onError(String result) {
                Log.e("getOffers",result);
            }
        };
        API.get("offers", Queue.getInstance(getApplicationContext()), callback);
    }

//    private void getCategories() {
//        final VolleyCallback callback = new VolleyCallback() {
//            @Override
//            public void onSuccess(JSONObject result) {
//                onCategories(result);
//            }
//
//            @Override
//            public void onError(String result) {
//                Log.e("getCategories",result);
//            }
//        };
//
//
//        API.get("offers", Queue.getInstance(getApplicationContext()), callback);
//    }
//
//    private void onCategories(JSONObject result) {
//    }

    private void onOffers(JSONObject obj) {
        Log.d(TAG, "onoffers");
        try {
            JSONArray jsonArray = obj.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                imageUrls.add(object.getString("brandLogoURL"));
                brandNames.add(object.getString("brandName"));
                brandIds.add(object.getString("brandId"));
                offerDesc.add(object.getString("name"));
            }

        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        initRecyclerView();
    }

    private void populateCats(){
        offersListGroup.add("Food");
        offersListGroup.add("Clothing");
        offersListGroup.add("Books");
        offersListGroup.add("House");
    }



    private void initRecyclerView() {
        Log.i("HERE", "initRecyclerView");

        adapterGroup = new OffersGroupAdp(MainActivity.this, offersListGroup, imageUrls, brandNames, offerDesc);
        layoutManagerGroup = new LinearLayoutManager(this);
        rvgroup.setLayoutManager(layoutManagerGroup);
        rvgroup.setAdapter(adapterGroup);


    }
}
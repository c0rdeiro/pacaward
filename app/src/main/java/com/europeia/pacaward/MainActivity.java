package com.europeia.pacaward;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "Main Activity";

    private LinearLayoutManager layoutManagerGroup;
    private OffersGroupAdp adapterGroup;
    private Button profileBtn;
    private RecyclerView rvgroup;
    private int aux;
    private ArrayList<Offer> offerArrayList = new ArrayList<>();
    private ArrayList<OfferCategory> offersPerCategory = new ArrayList<>();

    private ArrayList<String> brandIds = new ArrayList<>();
    private ArrayList<String> categoriesPerOffer = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navbar);
        bottomNavigationView.setSelectedItemId(R.id.offers);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.transactions:
                        startActivity(new Intent(getApplicationContext(), TransactionsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.offers:

                        return true;
                    case R.id.cards:
                        startActivity(new Intent(getApplicationContext(), CardsActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });

        profileBtn = findViewById(R.id.profilebtn);
        rvgroup = findViewById(R.id.offersgroupRV);
        getOffers();
    }

    private void getOffers() {
        Log.d(TAG,"getoffers");
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
    private void onOffers(JSONObject obj) {
        Log.d(TAG, "onoffers");
        try {
            JSONArray jsonArray = obj.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                offerArrayList.add(new Offer(object.getString("brandName"), object.getString("name"), object.getString("brandLogoURL")));
                brandIds.add(object.getString("brandId"));
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        aux = brandIds.size();
        getCategories();
    }

    private void getCategories() {
        Log.i(TAG,"getcategories");
        final VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                onCategories(result);
            }
            @Override
            public void onError(String result) {
                Log.e("getCategories",result);
            }
        };
        for (String id : brandIds) {
            API.get("brands/"+ id, Queue.getInstance(getApplicationContext()), callback);
        };
    }

    private void onCategories(JSONObject result) {
        Log.i(TAG, "oncategories");
        aux --;
        try {
            JSONArray jsonArrayComplete = result.getJSONArray("items");
            JSONObject jsonObject = jsonArrayComplete.getJSONObject(0);
            categoriesPerOffer.add(jsonObject.getJSONObject("metadata").getString("customKey1"));
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        if(aux == 0) initRecyclerView();
    }

    private void initRecyclerView() {
        Log.i(TAG, "initRecyclerView");
        for(int i = 0; i < categoriesPerOffer.size(); i++)
            offerArrayList.get(i).setOfferCategory(categoriesPerOffer.get(i));

        HashSet<String> categories = new HashSet<>(categoriesPerOffer);

        for(String cat : categories) {
            ArrayList<Offer> catoffer = new ArrayList<>();
            for (Offer i : offerArrayList)
                if (cat.equals(i.getOfferCategory()))
                    catoffer.add(i);
            offersPerCategory.add(new OfferCategory(catoffer, cat));
        }
        adapterGroup = new OffersGroupAdp(MainActivity.this, offersPerCategory);
        layoutManagerGroup = new LinearLayoutManager(this);
        rvgroup.setLayoutManager(layoutManagerGroup);
        rvgroup.setAdapter(adapterGroup);
    }
}
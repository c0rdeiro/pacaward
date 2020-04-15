package com.europeia.pacaward;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.fidel.sdk.Fidel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CardsActivity extends AppCompatActivity {

    private static final String TAG = "CardsActivity";
    private RecyclerView rvcards;
    private CardsAdp cardsAdp;
    private ArrayList<Card> cardArrayList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private FloatingActionButton addCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navbar);
        bottomNavigationView.setSelectedItemId(R.id.cards);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.transactions:
                        startActivity(new Intent(getApplicationContext(), TransactionsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.offers:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.cards:
                        return true;

                }
                return false;
            }
        });
        addCard =(FloatingActionButton) findViewById(R.id.addingcardbtn);

        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), FidelSDKActivity.class));
            }
        });

        rvcards =(RecyclerView) findViewById(R.id.rv_cards);
        getCards();
    }

    private void getCards() {
        Log.i(TAG, "getCards");
        final VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                onCards(result);
            }
            @Override
            public void onError(String result) {
                Log.e("getCards",result);
            }
        };
        API.get("programs/dcf206fa-eeda-4e1d-b14d-0fd2f3bc7964/cards", Queue.getInstance(getApplicationContext()), callback);
    }

    private void onCards(JSONObject result) {
        Log.i(TAG, "oncards");
        try {
            JSONArray jsonArray = result.getJSONArray("items");
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                cardArrayList.add(new Card(jsonObject.getString("lastNumbers"), jsonObject.getString("expMonth"), jsonObject.getString("expYear"), jsonObject.getString("type")));
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        initializeRV();
    }

    private void initializeRV() {
        Log.i(TAG, "initializeRV");

        cardsAdp = new CardsAdp(cardArrayList);
        linearLayoutManager = new LinearLayoutManager(this);
        rvcards.setLayoutManager(linearLayoutManager);
        rvcards.setAdapter(cardsAdp);
    }


}




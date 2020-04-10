package com.europeia.pacaward;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TransactionsActivity extends AppCompatActivity {

    private static final String TAG = "TransactionsActivity";
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private RecyclerView rvtransactions;
    private TransactionsAdp transactionsAdp;
    private LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navbar);
        bottomNavigationView.setSelectedItemId(R.id.transactions);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.transactions:
                        return true;
                    case R.id.offers:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.cards:
                        startActivity(new Intent(getApplicationContext(), CardsActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });
        rvtransactions = findViewById(R.id.rv_transactions);
        getTransactions();
    }

    private void getTransactions() {
        Log.d(TAG,"getTransactions");
        final VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                onTransactions(result);
            }
            @Override
            public void onError(String result) {
                Log.e("gettransactions",result);
            }
        };
        API.get("programs/dcf206fa-eeda-4e1d-b14d-0fd2f3bc7964/transactions", Queue.getInstance(getApplicationContext()), callback);
    }
    private void onTransactions(JSONObject obj) {
        Log.d(TAG, "onTransactions");
        try {
            JSONArray jsonArray = obj.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                transactions.add(new Transaction(object.getJSONObject("brand").getString("name"), object.getJSONObject("brand").getString("logoURL"), object.getJSONObject("location").getString("address"), object.getString("datetime"), object.getString("amount"), object.getString("currency")));
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        initializeRV();
    }

    private void initializeRV() {
        transactionsAdp = new TransactionsAdp(this, transactions);
        linearLayoutManager = new LinearLayoutManager(this);
        rvtransactions.setLayoutManager(linearLayoutManager);
        rvtransactions.setAdapter(transactionsAdp);

    }
}

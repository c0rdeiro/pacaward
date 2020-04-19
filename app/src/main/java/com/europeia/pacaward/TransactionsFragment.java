package com.europeia.pacaward;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TransactionsFragment extends Fragment {

    private static final String TAG = "TransactionsActivity";
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private RecyclerView rvtransactions;
    private TransactionsAdp transactionsAdp;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_transactions, container, false);
        rvtransactions =(RecyclerView) root.findViewById(R.id.rv_transactions);
        getTransactions();

        return root;
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
        API.call("programs/dcf206fa-eeda-4e1d-b14d-0fd2f3bc7964/transactions",0, Queue.getInstance(getContext()), callback);
    }
    private void onTransactions(JSONObject obj) {
        Log.d(TAG, "onTransactions");
        try {
            JSONArray jsonArray = obj.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                if(!object.getJSONObject("offer").getString("cashback").equals("0"))
                    transactions.add(new Transaction(object.getJSONObject("brand").getString("name"), object.getJSONObject("brand").getString("logoURL"), object.getJSONObject("location").getString("address"), object.getString("datetime"), object.getString("amount"), object.getJSONObject("offer").getString("cashback"), object.getString("currency")));
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        initializeRV();
    }

    private void initializeRV() {
        transactionsAdp = new TransactionsAdp(getActivity(), transactions);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvtransactions.setLayoutManager(linearLayoutManager);
        rvtransactions.setAdapter(transactionsAdp);

    }
}

package com.europeia.pacaward;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CardsFragment extends Fragment implements CardsAdp.OnDeleteCardListener {

    private static final String TAG = "Cards Fragment";
    private RecyclerView rvcards;
    private CardsAdp cardsAdp;
    private ArrayList<Card> cardArrayList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private FloatingActionButton addCard;
    private TextView noCardstxt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.i(TAG, "onCreateView");
        View root = inflater.inflate(R.layout.fragment_cards, container, false);
        rvcards =(RecyclerView) root.findViewById(R.id.rv_cards);
        noCardstxt = (TextView) root.findViewById(R.id.no_cardstxt);
        getCards();

        addCard =(FloatingActionButton) root.findViewById(R.id.addingcardbtn);
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), FidelSDKActivity.class));
            }
        });

        return root;
    }


    private void getCards() {
        Log.i(TAG, "getCards");
        final VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    if(result.getInt("count") == 0)
                        noCardstxt.setText("You have no cards.\nClick the plus sign to link a card.");
                    else onCards(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(String result) {
                Log.e("getCards",result);
            }
        };
        API.call("programs/dcf206fa-eeda-4e1d-b14d-0fd2f3bc7964/cards",0, Queue.getInstance(getContext()), callback);
    }

    private void onCards(JSONObject result) {
        Log.i(TAG, "oncards");
        try {
            JSONArray jsonArray = result.getJSONArray("items");
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                cardArrayList.add(new Card(jsonObject.getString("id"), jsonObject.getString("lastNumbers"), jsonObject.getString("expMonth"), jsonObject.getString("expYear"), jsonObject.getString("type")));
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        initializeRV();
    }

    private void initializeRV() {
        Log.i(TAG, "initializeRV");

        cardsAdp = new CardsAdp(getActivity(), cardArrayList, this);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvcards.setLayoutManager(linearLayoutManager);
        rvcards.setAdapter(cardsAdp);
    }

    @Override
    public void onDeleteCardClick(int position) {
        Log.i(TAG, "onDeleteCardClick");

        API.delete(String.format("cards/%s",cardArrayList.get(position).getId()), Queue.getInstance(getContext()));
        removeItem(position);
    }

    public void removeItem(int position){
        cardArrayList.remove(position);
        cardsAdp.notifyItemRemoved(position);

    }


}


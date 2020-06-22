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
import java.util.HashMap;
import java.util.HashSet;


public class OffersFragment extends Fragment {

    private static final String TAG = "Offers Fragment";

    private LinearLayoutManager layoutManagerGroup;
    private OffersGroupAdp adapterGroup;
    private RecyclerView rvgroup;
    private int aux;
    private ArrayList<Offer> offerArrayList = new ArrayList<>();
    private ArrayList<OfferCategory> offersPerCategory = new ArrayList<>();

    private HashSet<String> brandIds = new HashSet<>();
    private HashMap<String, String> brandCategory = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_offers, container, false);
        rvgroup =(RecyclerView) root.findViewById(R.id.rv_offers_group);
        getOffers();

        return root;
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
        API.call("offers",0, Queue.getInstance(getContext()), callback);
    }
    private void onOffers(JSONObject obj) {
        Log.d(TAG, "onoffers");
        try {
            JSONArray jsonArray = obj.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                offerArrayList.add(new Offer(object.getString("id"), object.getString("brandName"), object.getString("name"), object.getString("brandLogoURL")));
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
            API.call("brands/"+ id,0, Queue.getInstance(getContext()), callback);
        }
    }

    private void onCategories(JSONObject result) {
        Log.i(TAG, "oncategories");
        aux --;
        try {
            JSONArray jsonArrayComplete = result.getJSONArray("items");
            JSONObject jsonObject = jsonArrayComplete.getJSONObject(0);
            brandCategory.put(jsonObject.getString("name") ,jsonObject.getJSONObject("metadata").getString("customKey1"));
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        if(aux == 0) initRecyclerView();
    }

    private void initRecyclerView() {
        Log.i(TAG, "initRecyclerView");
        for(int i = 0; i < offerArrayList.size(); i++){
            for(String key : brandCategory.keySet()) {
                if (offerArrayList.get(i).getBrandName().equals(key))
                    offerArrayList.get(i).setOfferCategory(brandCategory.get(key));
            }
        }

        HashSet<String> categories = new HashSet<>(brandCategory.values());

        for(String cat : categories) {
            ArrayList<Offer> catoffer = new ArrayList<>();
            for (Offer i : offerArrayList)
                if (cat.equals(i.getOfferCategory()))
                    catoffer.add(i);
            offersPerCategory.add(new OfferCategory(catoffer, cat));
        }

        adapterGroup = new OffersGroupAdp(getActivity(), offersPerCategory, getContext());
        layoutManagerGroup = new LinearLayoutManager(getContext());
        rvgroup.setLayoutManager(layoutManagerGroup);
        rvgroup.setAdapter(adapterGroup);


    }
}

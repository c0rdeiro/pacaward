package com.europeia.pacaward;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class OffersGroupAdp extends RecyclerView.Adapter<OffersGroupAdp.ViewHolder> {

    private static final String TAG = "Group adapter";

    private Activity activity;

    private ArrayList<OfferCategory> offersPerCat;

    OffersGroupAdp(Activity activity, ArrayList<OfferCategory> offersPerCat){
        this.activity = activity;
        this.offersPerCat = offersPerCat;
    }

    @Override
    public OffersGroupAdp.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offers_row_group, parent, false);
        return new OffersGroupAdp.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OffersGroupAdp.ViewHolder holder, int position) {

        holder.offer_row_name.setText(offersPerCat.get(position).getCategoryName());

        OffersMemberAdp offersMemberAdp = new OffersMemberAdp(activity, offersPerCat.get(position).getOffers());
        LinearLayoutManager layoutManagerMember = new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false);
        holder.rv_row.setLayoutManager(layoutManagerMember);
        holder.rv_row.setAdapter(offersMemberAdp);

    }

    @Override
    public int getItemCount() {
        return offersPerCat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView offer_row_name;
        RecyclerView rv_row;


        public ViewHolder(View itemView) {
            super(itemView);

            offer_row_name = itemView.findViewById(R.id.offer_row_name);
            rv_row = itemView.findViewById(R.id.rv_row);
        }
    }



}

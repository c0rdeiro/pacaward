package com.europeia.pacaward;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;


public class OffersGroupAdp extends RecyclerView.Adapter<OffersGroupAdp.ViewHolder> implements OffersMemberAdp.OnOffersListener {

    private Activity activity;
    private ArrayList<OfferCategory> offersPerCat;
    private Context context;

    OffersGroupAdp(Activity activity, ArrayList<OfferCategory> offersPerCat, Context context){
        this.activity = activity;
        this.offersPerCat = offersPerCat;
        this.context = context;
    }

    @Override
    public OffersGroupAdp.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offers_row_group, parent, false);
        return new OffersGroupAdp.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OffersGroupAdp.ViewHolder holder, int position) {

        holder.offer_row_name.setText(offersPerCat.get(position).getCategoryName());

        OffersMemberAdp offersMemberAdp = new OffersMemberAdp(activity, offersPerCat.get(position).getOffers(), this);
        LinearLayoutManager layoutManagerMember = new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false);
        holder.rv_row.setLayoutManager(layoutManagerMember);
        holder.rv_row.setAdapter(offersMemberAdp);
    }

    @Override
    public int getItemCount() {
        return offersPerCat.size();
    }

    @Override
    public void onOffersClick(Offer offer) {
        Intent intent = new Intent(context, OfferDetailedActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("offer", offer);
        context.startActivity(intent);

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

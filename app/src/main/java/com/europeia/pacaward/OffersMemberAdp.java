package com.europeia.pacaward;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class OffersMemberAdp extends RecyclerView.Adapter<OffersMemberAdp.ViewHolder> {
    private Activity activity;
    private ArrayList<Offer> offers;
    private OnOffersListener onOffersListener;

    public OffersMemberAdp(Activity activity, ArrayList<Offer> offers, OnOffersListener onOffersListener) {
        this.activity = activity;
        this.offers = offers;
        this.onOffersListener = onOffersListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offers_item, parent, false);
        return new OffersMemberAdp.ViewHolder(view, onOffersListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Glide.with(activity).asBitmap().load(offers.get(position).getImageUrl()).into(holder.logo);
        holder.brandname.setText(offers.get(position).getBrandName());
        holder.offer.setText(offers.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView logo;
        TextView brandname;
        TextView offer;
        OnOffersListener onOffersListener;

        public ViewHolder(View itemView, OnOffersListener onOffersListener) {
            super(itemView);
            logo = itemView.findViewById(R.id.offerimage);
            brandname = itemView.findViewById(R.id.brandname);
            offer = itemView.findViewById(R.id.brandofferdesc);
            this.onOffersListener = onOffersListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onOffersListener.onOffersClick(offers.get(getAdapterPosition()-1));
        }
    }
    public interface OnOffersListener{
        void onOffersClick(Offer offer);
    }
}

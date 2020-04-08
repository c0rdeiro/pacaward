package com.europeia.pacaward;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class OffersMemberAdp extends RecyclerView.Adapter<OffersMemberAdp.ViewHolder> {

    private static final String TAG = "Member adp";

    private ArrayList<String> imageUrls = new ArrayList<>();
    private ArrayList<String> brandNames = new ArrayList<>();
    private ArrayList<String> offerDesc = new ArrayList<>();
    Activity activity;

    public OffersMemberAdp(Activity activity, ArrayList<String> imageUrls, ArrayList<String> brandNames, ArrayList<String> offerDesc) {
        this.activity = activity;
        this.imageUrls = imageUrls;
        this.brandNames = brandNames;
        this.offerDesc = offerDesc;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offers_item, parent, false);
        return new OffersMemberAdp.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(activity).asBitmap().load(imageUrls.get(position)).into(holder.logo);

        holder.brandname.setText(brandNames.get(position));
        holder.offer.setText(offerDesc.get(position));
        
        holder.logo.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View view){
              //make on click opening detailed offer
          }  
        });
    }

    @Override
    public int getItemCount() {
        return brandNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView logo;
        TextView brandname;
        TextView offer;

        public ViewHolder(View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.offerimage);
            brandname = itemView.findViewById(R.id.brandname);
            offer = itemView.findViewById(R.id.brandofferdesc);
        }
    }
}

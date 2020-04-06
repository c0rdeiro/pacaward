package com.europeia.pacaward;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class OffersRecyclerViewAdapter extends RecyclerView.Adapter<OffersRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> imageUrls = new ArrayList<>();
    private ArrayList<String> brandNames = new ArrayList<>();
    private ArrayList<String> offerDesc = new ArrayList<>();
    private Context context;

    public OffersRecyclerViewAdapter(Context context, ArrayList<String> imageUrls, ArrayList<String> brandNames, ArrayList<String> offerDesc) {
        this.imageUrls = imageUrls;
        this.brandNames = brandNames;
        this.offerDesc = offerDesc;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_offeritem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context).asBitmap().load(imageUrls.get(position)).into(holder.logo);

        holder.brandname.setText(brandNames.get(position));
        holder.offer.setText(offerDesc.get(position));
        
        holder.logo.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View view){
              Toast.makeText(context, brandNames.get(position), Toast.LENGTH_SHORT).show();
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

package com.europeia.pacaward;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.ArrayList;

class CardsAdp extends RecyclerView.Adapter<CardsAdp.ViewHolder> {

    private ArrayList<Card> cards;
    private Activity activity;
    private OnDeleteCardListener onDeleteCardListener;
    public CardsAdp(Activity activity, ArrayList<Card> cards, OnDeleteCardListener onDeleteCardListener){
        this.activity = activity;
        this.cards = cards;
        this.onDeleteCardListener = onDeleteCardListener;
    }
    @NonNull
    @Override
    public CardsAdp.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_item, parent, false);
        return new CardsAdp.ViewHolder(view, onDeleteCardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CardsAdp.ViewHolder holder, int position) {

        switch (cards.get(position).getType()) {
            case "visa":
                Glide.with(activity).asBitmap().load(R.drawable.ic_visa_logo).into(holder.cardType);
                break;
            case "mastercard":
                Glide.with(activity).asBitmap().load(R.drawable.ic_mastercardlogo).into(holder.cardType);
                break;
            case "amex":
                Glide.with(activity).asBitmap().load(R.drawable.ic_amexcard).into(holder.cardType);
                break;
        }
        holder.lastNumber.setText(cards.get(position).getLastNumbers());
        holder.expDate.setText(cards.get(position).getDate());


    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView lastNumber;
        TextView expDate;
        ImageView cardType;
        Button deleteCard;
        OnDeleteCardListener onDeleteCardListener;

        public ViewHolder(@NonNull View itemView, OnDeleteCardListener onDeleteCardListener) {
            super(itemView);
            lastNumber = itemView.findViewById(R.id.cardnumber);
            expDate = itemView.findViewById(R.id.expirationdate);
            cardType = itemView.findViewById(R.id.card_type);
            deleteCard = itemView.findViewById(R.id.delete_card);
            this.onDeleteCardListener = onDeleteCardListener;
            deleteCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION)
                onDeleteCardListener.onDeleteCardClick(position);
        }
    }
    public interface OnDeleteCardListener{
        void onDeleteCardClick(int position);
    }
}

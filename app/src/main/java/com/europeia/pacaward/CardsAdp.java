package com.europeia.pacaward;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class CardsAdp extends RecyclerView.Adapter<CardsAdp.ViewHolder> {

    private ArrayList<Card> cards;
    public CardsAdp(ArrayList<Card> cards){
        this.cards = cards;
    }
    @NonNull
    @Override
    public CardsAdp.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_item, parent, false);
        return new CardsAdp.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardsAdp.ViewHolder holder, int position) {
        holder.lastNumber.setText(cards.get(position).getLastNumbers());
        holder.expDate.setText("Exp: " + cards.get(position).getExpMonth() + "/" + cards.get(position).getExpYear());
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView lastNumber;
        TextView expDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lastNumber = itemView.findViewById(R.id.cardnumber);
            expDate = itemView.findViewById(R.id.expirationdate);
        }
    }
}

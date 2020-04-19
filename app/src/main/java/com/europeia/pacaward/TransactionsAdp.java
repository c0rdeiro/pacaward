package com.europeia.pacaward;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

class TransactionsAdp extends RecyclerView.Adapter<TransactionsAdp.ViewHolder> {

    private ArrayList<Transaction> transactions = new ArrayList<>();
    private Activity activity;

    public TransactionsAdp(Activity activity, ArrayList<Transaction> transactions) {
        this.transactions = transactions;
        this.activity = activity;
    }

    @NonNull
    @Override
    public TransactionsAdp.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transactions_item, parent, false);
        return new TransactionsAdp.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsAdp.ViewHolder holder, int position) {
        Glide.with(activity).asBitmap().load(transactions.get(position).getLogoUrl()).into(holder.brandLogo);
        holder.brandName.setText(transactions.get(position).getBrandName());
        holder.location.setText(transactions.get(position).getLocation());
        holder.date.setText(transactions.get(position).getDate());
        holder.amountSpent.setText(transactions.get(position).getAmountSpent());
        holder.amountSaved.setText(transactions.get(position).getAmountSaved());
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView brandLogo;
        TextView brandName;
        TextView location;
        TextView date;
        TextView amountSpent;
        TextView amountSaved;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            brandLogo = itemView.findViewById(R.id.brandlogo);
            brandName = itemView.findViewById(R.id.brand_name);
            location = itemView.findViewById(R.id.transaction_location);
            date = itemView.findViewById(R.id.transaction_date);
            amountSpent = itemView.findViewById(R.id.amount_spent);
            amountSaved = itemView.findViewById(R.id.amount_saved);
        }
    }
}

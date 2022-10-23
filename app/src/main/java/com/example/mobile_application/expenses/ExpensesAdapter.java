package com.example.mobile_application.expenses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_application.R;

import java.util.ArrayList;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.MyViewHolder>{
    private Context context;
    private Activity activity;
    private ArrayList expenseId,type,amount, date;
    int tripId;

    ExpensesAdapter(Activity activity, Context context, ArrayList expenseId, ArrayList type, ArrayList amount, ArrayList date, int tripId) {
        this.activity = activity;
        this.context = context;
        this.expenseId = expenseId;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.tripId = tripId;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_expenses, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpensesAdapter.MyViewHolder holder, int position) {
        holder.expenseId.setText(String.valueOf(expenseId.get(position)));
        holder.typeExpense.setText(String.valueOf(type.get(position)));
        holder.amountExpense.setText(String.valueOf(amount.get(position)));
        holder.dateExpense.setText(String.valueOf(date.get(position)));
    }

    @Override
    public int getItemCount() {
        return expenseId.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView expenseId, typeExpense, dateExpense, amountExpense;
        LinearLayout expenseRowLayout;
        public MyViewHolder(View view) {
            super(view);
            expenseId = itemView.findViewById(R.id.expenseId);
            typeExpense = itemView.findViewById(R.id.typeExpense);
            dateExpense = itemView.findViewById(R.id.dateExpense);
            amountExpense = itemView.findViewById(R.id.amountExpense);

            expenseRowLayout = itemView.findViewById(R.id.expenseRowLayout);
        }
    }
}

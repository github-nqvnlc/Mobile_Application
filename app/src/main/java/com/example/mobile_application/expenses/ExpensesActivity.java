package com.example.mobile_application.expenses;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mobile_application.ConnectDB;
import com.example.mobile_application.MainActivity;
import com.example.mobile_application.R;
import com.example.mobile_application.trips.AddTripActivity;

import java.util.ArrayList;

public class ExpensesActivity extends AppCompatActivity {
    private ImageButton add_btn;
    private RecyclerView recyclerView;
    ConnectDB connectDB;
    ArrayList<String> expensesId, typeExpense,amountExpense, dateExpense;
    ExpensesAdapter expensesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Expenses of trip");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        Intent intent = getIntent();
        int tripId = Integer.parseInt(intent.getStringExtra("tripId"));
        recyclerView = findViewById(R.id.recyclerView);
        add_btn = findViewById(R.id.add_button);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpensesActivity.this, AddExpensivesActivity.class);
                intent.putExtra("tripId", String.valueOf(tripId));
                startActivity(intent);
            }
        });

        connectDB = new ConnectDB(ExpensesActivity.this);
        expensesId = new ArrayList<>();
        typeExpense = new ArrayList<>();
        amountExpense = new ArrayList<>();
        dateExpense = new ArrayList<>();
        getExpenses(tripId);

        expensesAdapter = new ExpensesAdapter(ExpensesActivity.this,this,expensesId, typeExpense,amountExpense,dateExpense,tripId);
        recyclerView.setAdapter(expensesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ExpensesActivity.this));
    }

    public void getExpenses(int tripId){
        Cursor cursor = connectDB.getAllExpenses(tripId);
        if(cursor.getCount() == 0){
            Toast.makeText(ExpensesActivity.this, "No data", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                expensesId.add(cursor.getString(0));
                typeExpense.add(cursor.getString(1));
                amountExpense.add(cursor.getString(2));
                dateExpense.add(cursor.getString(3));
            }
        }
    }
}
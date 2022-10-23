package com.example.mobile_application.expenses;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.mobile_application.ConnectDB;
import com.example.mobile_application.R;

import java.util.Calendar;

public class AddExpensivesActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button buttonDate,  date_input, add_button;
    private EditText amountExpense;
    private Spinner typeExpense;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Create new expenses of trip");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expensives);
        date_input = findViewById(R.id.buttonDate);

        Intent intent = getIntent();
        int tripId = Integer.parseInt(intent.getStringExtra("tripId"));

        typeExpense = findViewById(R.id.typeExpense);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.typeExpense, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        typeExpense.setAdapter(adapter);

        amountExpense = findViewById(R.id.amountExpense);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPut = new Intent(AddExpensivesActivity.this, ExpensesActivity.class);
                String type = typeExpense.getSelectedItem().toString().trim();
                double amount = Double.parseDouble(amountExpense.getText().toString().trim());
                String date = buttonDate.getText().toString().trim();
                ConnectDB connectDB = new ConnectDB(AddExpensivesActivity.this);
                connectDB.addExpense(type,amount,date, tripId);
                intentPut.putExtra("tripId", String.valueOf(tripId));
                startActivity(intentPut);
            }
        });

        initDatePicker();
        buttonDate = findViewById(R.id.buttonDate);
    }
    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                buttonDate.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);

    }

    private String getTodayDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1) return "JAN";
        if (month == 2) return "FEB";
        if (month == 3) return "MAR";
        if (month == 4) return "APR";
        if (month == 5) return "MAY";
        if (month == 6) return "JUN";
        if (month == 7) return "JUL";
        if (month == 8) return "AUG";
        if (month == 9) return "SEP";
        if (month == 10) return "OCT";
        if (month == 11) return "NOV";
        if (month == 12) return "DEC";

        return "JAN";
    }

    public void openDataPicker(View view) {
        datePickerDialog.show();

    }
}
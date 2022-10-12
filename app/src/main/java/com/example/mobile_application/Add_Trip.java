package com.example.mobile_application;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Calendar;

public class Add_Trip extends AppCompatActivity {
    private EditText name_input, destination_input,require_input, des_input;
    private RadioGroup radioGroup;
    private RadioButton btn_yes, btn_no;
    private Button save_btn;


    private DatePickerDialog datePickerDialog;
    private Button buttonDate,  date_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        name_input = findViewById(R.id.input_name);
        destination_input = findViewById(R.id.input_destination);
        date_input = findViewById(R.id.buttonDate);
        radioGroup = findViewById(R.id.radioGroup);
        btn_yes = findViewById(R.id.radioButton_yes);
        btn_no = findViewById(R.id.radioButton_no);
        des_input = findViewById(R.id.input_des);
        save_btn = findViewById(R.id.save_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectDB myDB = new ConnectDB(Add_Trip.this);
                String name = name_input.getText().toString().trim();
                String destination = destination_input.getText().toString().trim();
                String date = date_input.getText().toString().trim(); // sau khi tao datepicker thi chinh lai
                String des = des_input.getText().toString().trim();
                int idGroup = radioGroup.getCheckedRadioButtonId();
                RadioButton radioGroup = findViewById(idGroup);
                String require = radioGroup.getText().toString().trim();
                myDB.addNewTrip(name,destination,date,require,des);
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
                buttonDate.setText(getTodayDate());
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
package com.example.mobile_application.trips;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_application.ConnectDB;
import com.example.mobile_application.MainActivity;
import com.example.mobile_application.R;
import com.example.mobile_application.expenses.ExpensesActivity;

import java.text.ParseException;
import java.util.Calendar;

public class EditTripActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button buttonDate, date_input;
    private TextView alertName, alertDestination, alertDate, alertRequired;
    private EditText inputName, inputDestination, inputDes;
    private RadioGroup radioGroup;
    private RadioButton btn_yes, btn_no;
    private Button save_btn, delete_btn, expenses_btn;

    private String trip_id,name,destination,require, date, description;

    private Boolean setValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Edit Trip");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);
        inputName = findViewById(R.id.inputName);
        inputDestination = findViewById(R.id.inputDestination);
        date_input = findViewById(R.id.buttonDate);
        radioGroup = findViewById(R.id.radioGroup);
        btn_yes = findViewById(R.id.radioButtonYes);
        btn_no = findViewById(R.id.radioButtonNo);
        inputDes = findViewById(R.id.inputDes);
        save_btn = findViewById(R.id.save_btn);
        expenses_btn =  findViewById(R.id.expenses_btn);
        alertName = findViewById(R.id.alertName);
        alertDestination = findViewById(R.id.alertDestination);
        alertDate =findViewById(R.id.alertDate);
        alertRequired = findViewById(R.id.alertRequired);
        try {
            getAndSetIntentData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTripActivity.this, MainActivity.class);
                ConnectDB myDB = new ConnectDB(EditTripActivity.this);
                name = inputName.getText().toString().trim();
                date = date_input.getText().toString().trim();
                destination = inputDestination.getText().toString().trim();
                description = inputDes.getText().toString().trim();
                int requireGroup = radioGroup.getCheckedRadioButtonId();

                if (requireGroup < 0) {
                    validInput(name, alertName,"Name of trip");
                    validInput(destination, alertDestination,"Destination");
                    validInput(date, alertDate, "Date");
                    alertRequired.setText("You need to fill all required fields Require Risks Assessment!");
                } else {
                    RadioButton radioGroup = findViewById(requireGroup);
                    String strRequire = radioGroup.getText().toString().trim();
                    alertRequired.setText("");
                    if (setValid == false) {
                        validInput(name, alertName,"Name of trip");
                        validInput(destination, alertDestination,"Destination");
                        validInput(date, alertDate, "Date");
                    } else {

                        myDB.editTrip(trip_id,name,destination,date,require,description);
                        startActivity(intent);
                    }
                }
            }
        });
        initDatePicker();
        buttonDate = findViewById(R.id.buttonDate);

        delete_btn = findViewById(R.id.delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });

        Intent intent = getIntent();
        int tripId = Integer.parseInt(intent.getStringExtra("trip_id"));
        expenses_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTripActivity.this, ExpensesActivity.class);
                intent.putExtra("tripId", String.valueOf(tripId));
                startActivity(intent);
            }
        });

    }

    private void validInput(String field, TextView alert, String nameField) {
        if (field.length() == 0) {
            alert.setText("You need to fill all required fields "+nameField+" !");
            setValid = false;
        } else {
            alert.setText("");
            setValid = true;
        }
    }

    void confirm() {
        Intent intent = getIntent();
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Delete trip "+name+" ?");
        builder.setMessage("Are you sure? You want to delete trip ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ConnectDB connectDB = new ConnectDB( EditTripActivity.this);
                connectDB.deleteTrip(trip_id);
                Intent intent = new Intent( EditTripActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    public void getAndSetIntentData() throws ParseException {
        if(getIntent().hasExtra("trip_id")&&getIntent().hasExtra("destination")&&getIntent().hasExtra("date")&&getIntent().hasExtra("require")&&getIntent().hasExtra("des")){
            int requireGroup = radioGroup.getCheckedRadioButtonId();
            RadioButton radioGroup = findViewById(requireGroup);
            trip_id = getIntent().getStringExtra("trip_id");
            name = getIntent().getStringExtra("name");
            destination = getIntent().getStringExtra("destination");
            date = getIntent().getStringExtra("date");
            require = getIntent().getStringExtra("require");
            description = getIntent().getStringExtra("des");

            inputName.setText(name);
            inputDestination.setText(destination);
            date_input.setText(date);
            inputDes.setText(description);
            if (require.equals("Yes"))
            {
                btn_yes.setChecked(true);
                btn_no.setChecked(false);
            }
            else {

                btn_yes.setChecked(false);
                btn_no.setChecked(true);
            }
        }else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
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
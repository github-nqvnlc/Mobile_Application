package com.example.mobile_application;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ConnectDB extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "manager.db";

    private static final  String TABLE_TRIPS = "trips";
    private static final  String TABLE_EXPENSES = "expenses";
    public ConnectDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String trips_table = "CREATE TABLE " + TABLE_TRIPS + "(trip_id INTEGER primary key autoincrement, name TEXT, destination TEXT, date TEXT, require TEXT, des TEXT);";
        String expenses_table = "CREATE TABLE " + TABLE_EXPENSES + "(expenses_id INTEGER primary key autoincrement, trip_id INTEGER, type TEXT, amount DOUBLE, time TEXT, foreign key(trip_id) references trips(trip_id));";
        sqLiteDatabase.execSQL(trips_table);
        sqLiteDatabase.execSQL(expenses_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_EXPENSES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_TRIPS);
        onCreate(sqLiteDatabase);
    }

    public void addNewTrip(String name, String destination, String date, String require, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("destination", destination);
        cv.put("date", date);
        cv.put("require", require);
        cv.put("des", description);
        long result = db.insert(TABLE_TRIPS, null,cv);
        if(result ==-1){
            Toast.makeText(context, "Failed to add", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
        }
    }
}

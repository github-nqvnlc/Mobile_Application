package com.example.mobile_application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
        ContentValues value = new ContentValues();
        value.put("name", name);
        value.put("destination", destination);
        value.put("date", date);
        value.put("require", require);
        value.put("des", description);
        long result = db.insert(TABLE_TRIPS, null,value);
        if(result ==-1){
            Toast.makeText(context, "Failed to add", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Add trip successful", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getAllTrip() {
        String queryDB = "SELECT trip_id, name, destination, date, require, des FROM " + TABLE_TRIPS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(queryDB, null);
        }
        return cursor;
    }

    public void editTrip(String row_id, String name, String destinaton, String date, String require, String des){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("name", name);
        value.put("destination", destinaton);
        value.put("date", date);
        value.put("require", require);
        value.put("des", des);
        long result = db.update(TABLE_TRIPS, value, "trip_id=?",new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Edit Trip.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Edit trip successful", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteTrip(String tripId){
        SQLiteDatabase db = this.getWritableDatabase();
        long resultTrip = db.delete(TABLE_TRIPS, "trip_id=?",new String[]{tripId});
        long resultExpense = db.delete(TABLE_EXPENSES, "trip_id=?",new String[]{tripId});
        if(resultTrip == -1 && resultExpense == -1){
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Delete trip successful", Toast.LENGTH_SHORT).show();
        }
    }

    public void addExpense(String type, double amount,String time, int tripID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("trip_id", tripID);
        value.put("type", type);
        value.put("amount", amount);
        value.put("time", time);
        long result = db.insert(TABLE_EXPENSES,null,value);
        if(result == -1){
            Toast.makeText(context, "Failed to Add", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Add expense successful", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getAllExpenses(int tripId){
        String query ="SELECT expenses_id, type, amount, time FROM "+ TABLE_EXPENSES +" where trip_id = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, new String[]{String.valueOf(tripId)});
        }
        return cursor;
    }
}

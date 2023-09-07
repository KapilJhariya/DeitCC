package com.example.deitcc.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.deitcc.EditGoal;
import com.example.deitcc.model.Goal;
import com.example.deitcc.model.Today;
import com.example.deitcc.params.Params;
import com.example.deitcc.params.Params2;

import java.util.ArrayList;
import java.util.List;

public class MyDBHandler2 extends SQLiteOpenHelper {

    public MyDBHandler2(Context context) {
        super(context, Params2.DB_NAME, null, Params2.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create = "CREATE TABLE "+ Params2.TABLE_NAME+"(" +  Params2.KEY_CALORIES+" TEXT,"
                + Params2.KEY_CABS+" TEXT, "+ Params2.KEY_PROTEIN+" TEXT, "+ Params2.KEY_FAT+" " +
                "TEXT,"+ Params2.KEY_DATE+" TEXT)";
        Log.d("dbkapil","Queury being run is :"+create);
        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addToday(Today today){
        SQLiteDatabase db = this.getWritableDatabase();

        //MyDBHandler dbHandler = new MyDBHandler(EditGoal.this);
        //SQLiteDatabase db = dbHandler.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, Params2.TABLE_NAME);

        if (count > 0) {

            String temp = String.valueOf(new java.sql.Date(System.currentTimeMillis()));
            String delete = "DELETE FROM " + Params2.TABLE_NAME + " WHERE DATE != " +
                    "'" + temp + "'";
            db.execSQL(delete);

        }



        ContentValues values = new ContentValues();
        values.put(Params2.KEY_CALORIES , today.getCalories());
        values.put(Params2.KEY_CABS , today.getCarbohydrates());
        values.put(Params2.KEY_PROTEIN , today.getProtein());
        values.put(Params2.KEY_FAT , today.getFat());
        values.put(Params2.KEY_DATE , today.getDate());

        db.insert(Params2.TABLE_NAME , null , values);
        Log.d("dbkapil","Successfully inserted");
        db.close();
    }

    public List<Today> getAllContacts(){
        List<Today> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //generate the query to read from the database

        String select = "SELECT * FROM " + Params2.TABLE_NAME ;
        Cursor cursor = db.rawQuery(select,null);

        //Loop through now
        if(cursor.moveToFirst()){
            do{
                Today contact = new Today();
                contact.setCalories(cursor.getString(0));
                contact.setCarbohydrates(cursor.getString(1));
                contact.setProtein(cursor.getString(2));
                contact.setFat(cursor.getString(3));
                contactList.add(contact);
            }while (cursor.moveToNext());


        }

        return contactList;
    }

    public Double getSumOfColumn(String columnName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String temp = String.valueOf(new java.sql.Date(System.currentTimeMillis()));
        String query =
                "SELECT SUM(" + columnName + ") FROM "+Params2.TABLE_NAME+" WHERE DATE = "+
                        "'"+temp+"'";
        Cursor cursor = db.rawQuery(query, null);

        Double sum = 0.0;
        if (cursor.moveToFirst()) {
            sum = cursor.getDouble(0);
        }

        cursor.close();
        return sum;
    }
}


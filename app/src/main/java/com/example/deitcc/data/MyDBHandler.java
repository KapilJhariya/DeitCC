package com.example.deitcc.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.deitcc.model.Goal;
import com.example.deitcc.params.Params;
import com.example.deitcc.params.Params2;

import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper {

    public MyDBHandler(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create = "CREATE TABLE "+ Params.TABLE_NAME+"(" +  Params.KEY_CALORIES+" TEXT,"
                + Params.KEY_CABS+" TEXT, "+ Params.KEY_PROTEIN+" TEXT, "+ Params.KEY_FAT+" TEXT)";
        Log.d("dbkapil","Queury being run is :"+create);
        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addGOAL(Goal goal){
        SQLiteDatabase db = this.getWritableDatabase();
        String delete = "DELETE FROM "+Params.TABLE_NAME;
        db.execSQL(delete);

        ContentValues values = new ContentValues();
        values.put(Params.KEY_CALORIES , goal.getCalories());
        values.put(Params.KEY_CABS , goal.getCarbohydrates());
        values.put(Params.KEY_PROTEIN , goal.getProtein());
        values.put(Params.KEY_FAT , goal.getFat());

        db.insert(Params.TABLE_NAME , null , values);
        Log.d("dbkapil","Successfully inserted");
        db.close();
    }

    public List<Goal> getAllContacts(){
        List<Goal> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //generate the query to read from the database

        String select = "SELECT * FROM " + Params.TABLE_NAME ;
        Cursor cursor = db.rawQuery(select,null);

        //Loop through now
        if(cursor.moveToFirst()){
            do{
                Goal contact = new Goal();
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
        //String temp = String.valueOf(new java.sql.Date(System.currentTimeMillis()));
        String query =
                "SELECT SUM(" + columnName + ") FROM "+ Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        Double sum = 0.0;
        if (cursor.moveToFirst()) {
            sum = cursor.getDouble(0);
        }

        cursor.close();
        return sum;
    }
}

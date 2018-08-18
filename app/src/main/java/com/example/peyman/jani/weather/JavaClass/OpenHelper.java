package com.example.peyman.jani.weather.JavaClass;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC_peyman on 4/21/2018.
 */

public class OpenHelper extends SQLiteOpenHelper {
    public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String db_create_query = "" +
                "CREATE TABLE City (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " name TEXT ," +
                " responseString TEXT  " +")" +
                "";
        db.execSQL(db_create_query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void inserToDB(String city, int response) {
        String insertQuery = "INSERT INTO " + "City" +
                "(city , TEMP)" + "VALUES( '" + city + "'," + response + "  )";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(insertQuery);
        db.close();
    }
        public List<City> getCities() {
        String test = "";
            List<City> city = new ArrayList<>();
        SQLiteDatabase db = this. getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from " + "City", null);
        while (cursor.moveToNext()) {
            test += cursor.getString(0)+"\n";
            City city1=new City();
            city1.setName(cursor.getString(0));
            city1.setResponseString(cursor.getString(1));
            city.add(city1);
        }
        db.close();
        return city;
    }

}

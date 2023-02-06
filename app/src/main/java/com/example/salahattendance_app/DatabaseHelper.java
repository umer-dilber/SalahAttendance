package com.example.salahattendance_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Salah-Application";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE SalahRecord (Date STRING, Fajar INTEGER, Zuhr INTEGER, Asar INTEGER, Maghrib INTEGER, Esha INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // handle database upgrades
    }

    public void insertData(MyData data) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Date", data.getDate());
        values.put("Fajar", data.getFajar());
        values.put("Zuhr", data.getZuhr());
        values.put("Asar", data.getAsar());
        values.put("Maghrib", data.getMaghrib());
        values.put("Esha", data.getEsha());
        db.insert("SalahRecord", null, values);
    }

    public List<MyData> getAllData() {
        List<MyData> dataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SalahRecord", null);
        if (cursor.moveToFirst()) {
            do {
                MyData data = new MyData();
                data.setDate(cursor.getString(0));
                data.setFajar(cursor.getInt(1));
                data.setZuhr(cursor.getInt(2));
                data.setAsar(cursor.getInt(3));
                data.setMaghrib(cursor.getInt(4));
                data.setEsha(cursor.getInt(5));
                // add to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dataList;
    }


}

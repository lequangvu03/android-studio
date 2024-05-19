package com.example.taxi_lequangvu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.Console;
import java.util.ArrayList;

public class TaxiDatabase extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "HOA_DON_TAXI";
    public static final String ID_COLUMN = "ID";
    public static final String PLATE_NUMBER_COLUMN = "PLATE_NUMBER";
    public static final String DISTANCE_COLUMN = "DISTANCE";
    public static final String PRICE_COLUMN = "PRICE";
    public static final String DISCOUNT_COLUMN = "DISCOUNT";

    public TaxiDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME + "("
                + ID_COLUMN + " Text, "
                + PLATE_NUMBER_COLUMN + " Text, "
                + DISTANCE_COLUMN + " Text, "
                + PRICE_COLUMN + " Text, "
                + DISCOUNT_COLUMN + " Text)";

        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addRecord(HoaDonTaxi h){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(ID_COLUMN, h.getId());
        value.put(PLATE_NUMBER_COLUMN, h.getPlateNumber());
        value.put(DISTANCE_COLUMN, h.getDistance());
        value.put(PRICE_COLUMN, h.getPrice());
        value.put(DISCOUNT_COLUMN, h.getDiscountPercent());

        db.insert(TABLE_NAME, null, value);
        db.close();
    }

    public void updateRecord(HoaDonTaxi h, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(ID_COLUMN, h.getId());
        value.put(PLATE_NUMBER_COLUMN, h.getPlateNumber());
        value.put(DISTANCE_COLUMN, h.getDistance());
        value.put(PRICE_COLUMN, h.getPrice());
        value.put(DISCOUNT_COLUMN, h.getDiscountPercent());

        db.update(TABLE_NAME, value, ID_COLUMN + " =? ", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteRecord(int id){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE ID = '" + id + "'";
        db.execSQL(sql);
        db.close();
    }

    public ArrayList<HoaDonTaxi> getAllRecords(){
        ArrayList<HoaDonTaxi> list = new ArrayList<>();
        String SQL_COMMAND = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SQL_COMMAND, null);

        if(cursor != null){
            while (cursor.moveToNext()){
                HoaDonTaxi h = new HoaDonTaxi(cursor.getString(1),
                        cursor.getDouble(2), cursor.getInt(3),
                        cursor.getInt(4));
                h.setId(cursor.getInt(0));
                list.add(h);
            }
        }

        return list;
    }
}

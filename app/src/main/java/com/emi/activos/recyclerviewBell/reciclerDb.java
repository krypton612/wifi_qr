package com.emi.activos.recyclerviewBell;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.emi.activos.Adapter.ListElement;

import java.util.ArrayList;
import java.util.List;

public class reciclerDb extends SQLiteOpenHelper {
    private static final String nombre = "information_add.db";
    private static final int version2 = 1;
    private static final String table2 = "CREATE TABLE wifi(id_wifi INTEGER PRIMARY KEY AUTOINCREMENT, ssid TEXT NOT NULL UNIQUE, password TEXT, cifrado TEXT)";

    public reciclerDb(@Nullable Context context) {
        super(context, nombre, null, version2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(table2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS wifi");
        sqLiteDatabase.execSQL(table2);
    }

    public void agregarWifi(String ssid, String password, String seguridad){
        SQLiteDatabase bd = getWritableDatabase();
        if (bd!=null){
            bd.execSQL("INSERT INTO wifi VALUES(null, '"+ssid+"', '"+password+"', '"+seguridad+"')");
            bd.close();
        }
    }
    public List<ListElement> mostrar(){
        SQLiteDatabase bd = getReadableDatabase();

        Cursor cursor = bd.rawQuery("SELECT w.ssid, w.password, w.cifrado FROM wifi w", null);
        List<ListElement> wifi = new ArrayList<>();

        if (cursor.moveToFirst()){
            do {
                wifi.add(new ListElement(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            }while (cursor.moveToNext());
        }
        bd.close();
        return wifi;
    }

}

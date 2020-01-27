package com.example.crudsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Conexion extends SQLiteOpenHelper {

    private static final String name = "persona.db";
    private static final int version = 1;

    public Conexion(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table persona(id integer primary key autoincrement," +
                "nombre varchar(50), apellido varchar(50), telefono integer(8))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

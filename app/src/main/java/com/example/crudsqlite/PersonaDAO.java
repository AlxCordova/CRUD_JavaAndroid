package com.example.crudsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {

    private Conexion conexion;
    private SQLiteDatabase sqlite;

    public PersonaDAO(Context context) {
        conexion = new Conexion(context);
        sqlite = conexion.getWritableDatabase();
    }

    public long insert(Persona persona){
        ContentValues values = new ContentValues();
        values.put("nombre", persona.getNombre());
        values.put("apellido", persona.getApellido());
        values.put("telefono", persona.getTelefono());
        return sqlite.insert("persona", null, values);
    }

    public List<Persona> todos(){
        List<Persona> personas = new ArrayList<>();
        Cursor cursor = sqlite.query("persona", new String[]{"id", "nombre", "apellido", "telefono"},
                null,null, null, null, "nombre");
        while (cursor.moveToNext()){
            Persona p = new Persona();
            p.setId(cursor.getInt(0));
            p.setNombre(cursor.getString(1));
            p.setApellido(cursor.getString(2));
            p.setTelefono(cursor.getInt(3));
            personas.add(p);
        }
        return personas;
    }

    public void eliminar(Persona p){
        sqlite.delete("persona", "id = ?", new String[]{p.getId().toString()});
    }

    public void actualizar(Persona p){
        ContentValues values = new ContentValues();
        values.put("nombre", p.getNombre());
        values.put("apellido", p.getApellido());
        values.put("telefono", p.getTelefono());
        sqlite.update("persona", values, "id = ?", new String[]{p.getId().toString()});
    }
}

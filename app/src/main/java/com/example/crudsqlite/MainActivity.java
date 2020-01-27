package com.example.crudsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText nombre;
    private EditText apellido;
    private EditText telefono;
    private PersonaDAO dao;
    private Persona persona = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre = findViewById(R.id.txt_nombre);
        apellido = findViewById(R.id.txt_apellido);
        telefono = findViewById(R.id.txt_telefono);
        dao = new PersonaDAO(this);

        Intent it = getIntent();
        if (it.hasExtra("persona")){
            persona = (Persona) it.getSerializableExtra("persona");
            nombre.setText(persona.getNombre());
            apellido.setText(persona.getApellido());
            telefono.setText(String.valueOf(persona.getTelefono()));
            //telefono.setText(Integer.parseInt(persona.getTelefono().toString()));
        }
    }

    public void guardar(View view){
        if (persona == null){
            persona = new Persona();
            persona.setNombre(nombre.getText().toString());
            persona.setApellido(apellido.getText().toString());
            persona.setTelefono(Integer.parseInt(telefono.getText().toString()));
            long id = dao.insert(persona);
            Intent intent = new Intent(MainActivity.this, ListaPersonasActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Persona ingresada correctamente ", Toast.LENGTH_SHORT).show();
        }else {
            persona.setNombre(nombre.getText().toString());
            persona.setApellido(apellido.getText().toString());
            persona.setTelefono(Integer.parseInt(telefono.getText().toString()));
            dao.actualizar(persona);
            Intent intent = new Intent(MainActivity.this, ListaPersonasActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Persona actualizada correctamente ", Toast.LENGTH_SHORT).show();
        }
    }
}

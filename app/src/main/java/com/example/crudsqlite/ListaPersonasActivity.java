package com.example.crudsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class ListaPersonasActivity extends AppCompatActivity {

    private ListView listView;
    private PersonaDAO dao;
    private List<Persona> personas;
    private List<Persona> personasFiltradas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personas);

        listView = findViewById(R.id.lista_personas);
        dao = new PersonaDAO(this);
        personas = dao.todos();
        personasFiltradas.addAll(personas);
        //ArrayAdapter<Persona> adapter = new ArrayAdapter<Persona>(this, android.R.layout.simple_list_item_1, personasFiltradas);
        PersonaAdapter adapter = new PersonaAdapter(this, personasFiltradas);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal, menu);
        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s){
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s){
                buscarPersona(s);
                //System.out.println("word " + s);
                return false;
            }
        });

        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);
    }

    public void buscarPersona(String nombre){
        personasFiltradas.clear();
        for (Persona p : personas){
            if (p.getNombre().toLowerCase().contains(nombre.toLowerCase())){
                personasFiltradas.add(p);
            }
        }
        listView.invalidateViews();
    }

    public void eliminar(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Persona personaEliminar = personasFiltradas.get(menuInfo.position);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atención")
                .setMessage("Desea eliminar la persona?")
                .setNegativeButton("NO", null)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        personasFiltradas.remove(personaEliminar);
                        personas.remove(personaEliminar);
                        dao.eliminar(personaEliminar);
                        listView.invalidateViews();
                    }
                }).create();
        dialog.show();
    }

    public void añadir(MenuItem item){
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }

    public void actualizar(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Persona personaActualizar = personasFiltradas.get(menuInfo.position);
        Intent it = new Intent(this, MainActivity.class);
        it.putExtra("persona", personaActualizar);
        startActivity(it);
    }

    @Override
    public void onResume(){
        super.onResume();
        personas = dao.todos();
        personasFiltradas.clear();
        personasFiltradas.addAll(personas);
        listView.invalidateViews();
    }

}

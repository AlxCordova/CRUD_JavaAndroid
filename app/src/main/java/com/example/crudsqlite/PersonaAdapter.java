package com.example.crudsqlite;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PersonaAdapter extends BaseAdapter {

    private List<Persona> personas;
    private Activity activity;

    public PersonaAdapter(Activity activity, List<Persona> personas) {
        this.activity = activity;
        this.personas = personas;
    }

    @Override
    public int getCount() {
        return personas.size();
    }

    @Override
    public Object getItem(int position) {
        return personas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return personas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = activity.getLayoutInflater().inflate(R.layout.item, parent, false);
        TextView name = v.findViewById(R.id.txt_name);
        TextView lastname = v.findViewById(R.id.txt_lastname);
        TextView phone = v.findViewById(R.id.txt_phone);

        Persona p = personas.get(position);
        name.setText(p.getNombre());
        lastname.setText(p.getApellido());
        phone.setText(String.valueOf(p.getTelefono()));
        return v;
    }
}

package com.mas_aplicaciones.misfinanzas;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Listado extends Fragment {

    ListView listaMovimientos;
    List<String> datosListaMovimientos =  new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    public Listado() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listado, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listaMovimientos = view.findViewById(R.id.listaMovimientos);

        Button botonAgregarMovimiento = view.findViewById(R.id.botonAgregarMovimiento);
        botonAgregarMovimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_listado_to_agregarMovimiento);
            }
        });

         arrayAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                datosListaMovimientos);

        listaMovimientos.setAdapter(arrayAdapter);

        buscarMovimientos();
    }
    private void buscarMovimientos() {
        SQLiteDatabase baseDatos = (new BaseDatos(getContext(), "myBD", null, BaseDatos.versionBD)).getReadableDatabase();

        String sql = String.format("select * from finanzas where usuario=%d", BaseDatos.usuarioID);
        Cursor fila = baseDatos.rawQuery(sql, null);

        if (fila.moveToFirst()) {
            datosListaMovimientos.clear();
            do {
                String descripcion = fila.getString(fila.getColumnIndex("descripcion"));
                String monto = fila.getString(fila.getColumnIndex("monto"));
                datosListaMovimientos.add(String.format("%s -> %s", descripcion, monto));
            }while(fila.moveToNext());

            arrayAdapter.notifyDataSetChanged();
        }
    }
}

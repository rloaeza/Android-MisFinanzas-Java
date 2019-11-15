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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Listado extends Fragment {

    TextView textoBalance;
    TextView textoNombreUsuario;
    ListView listaMovimientos;
    List<Movimientos> datosListaMovimientos =  new ArrayList<>();
    private double balance = 0;
    ArrayAdapter<Movimientos> arrayAdapter;
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

        textoBalance = view.findViewById(R.id.textoBalance);
        textoNombreUsuario = view.findViewById(R.id.textoNombreUsuario);
        textoNombreUsuario.setText(BaseDatos.nombreUsuario);
        listaMovimientos = view.findViewById(R.id.listaMovimientos);

        Button botonBorrarMovimientos = view.findViewById(R.id.botonBorrarMovimientos);
        botonBorrarMovimientos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase baseDatos = (new BaseDatos(getContext(), BaseDatos.nombreBD, null, BaseDatos.versionBD)).getWritableDatabase();
                baseDatos.delete("finanzas", String.format("usuario=%d", BaseDatos.usuarioID), null);
                baseDatos.close();
                buscarMovimientos();
            }
        });

        Button botonAgregarMovimiento = view.findViewById(R.id.botonAgregarMovimiento);
        botonAgregarMovimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_listado_to_agregarMovimiento);
            }
        });

         arrayAdapter = new ArrayAdapter<Movimientos>(
                getContext(),
                android.R.layout.simple_list_item_1,
                datosListaMovimientos);
        listaMovimientos.setAdapter(arrayAdapter);
        buscarMovimientos();
    }
    private void buscarMovimientos() {
        SQLiteDatabase baseDatos = (new BaseDatos(getContext(), BaseDatos.nombreBD, null, BaseDatos.versionBD)).getReadableDatabase();
        String sql = String.format("SELECT * FROM finanzas WHERE usuario=%d", BaseDatos.usuarioID);
        Cursor fila = baseDatos.rawQuery(sql, null);
        datosListaMovimientos.clear();
        balance = 0;
        if (fila.moveToFirst()) {

            do {
                Movimientos movimiento = new Movimientos(
                        fila.getInt(fila.getColumnIndex("id")),
                        fila.getString(fila.getColumnIndex("descripcion")),
                        fila.getDouble(fila.getColumnIndex("monto")),
                        fila.getInt(fila.getColumnIndex("usuario"))
                );
                datosListaMovimientos.add(movimiento);
                balance += Double.valueOf(movimiento.getMonto());
            }while(fila.moveToNext());
        }
        fila.close();
        baseDatos.close();
        textoBalance.setText(String.format("%s: $%s", getString(R.string.balance), String.valueOf(balance)));
        arrayAdapter.notifyDataSetChanged();
    }
}

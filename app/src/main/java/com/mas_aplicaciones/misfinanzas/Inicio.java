package com.mas_aplicaciones.misfinanzas;


import android.content.ContentValues;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Inicio extends Fragment {

    private EditText usuario;
    private EditText clave;

    public Inicio() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usuario = view.findViewById(R.id.textoUsuario);
        clave = view.findViewById(R.id.textoClave);


        Button botonEntrar = view.findViewById(R.id.botonEntrar);
        Button botonRegistrar = view.findViewById(R.id.botonRegistrar);

        botonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entrar(v);
            }
        });

        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_inicio_to_registro);

            }
        });


    }



    public void entrar(View v) {
        SQLiteDatabase baseDatos = (new BaseDatos(getContext(), "myBD", null, BaseDatos.versionBD)).getReadableDatabase();

        String sql = String.format("select * from usuarios where usuario='%s' and clave='%s'", usuario.getText().toString(), clave.getText().toString());
        Cursor fila = baseDatos.rawQuery(sql, null);

        if( fila.moveToFirst()) {
            BaseDatos.usuarioID = fila.getInt(fila.getColumnIndex("id"));
            usuario.setText("");
            clave.setText("");
            Navigation.findNavController(this.getView()).navigate(R.id.action_inicio_to_listado);


        }else {
            Toast.makeText(getContext(), "No existe usuario", Toast.LENGTH_SHORT).show();
        }
        fila.close();
        baseDatos.close();


    }
   
}

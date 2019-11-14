package com.mas_aplicaciones.misfinanzas;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Registro extends Fragment {
    private EditText nombre;
    private EditText usuario;
    private EditText clave1;
    private EditText clave2;

    public Registro() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registro, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nombre = view.findViewById(R.id.textoNombreUsuario);
        usuario = view.findViewById(R.id.textoUsuario);
        clave1 = view.findViewById(R.id.textoClave1);
        clave2 = view.findViewById(R.id.textoClave2);

        Button bRegistrar = view.findViewById(R.id.botonRegistrar);
        bRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( clave1.getText().toString().equals(clave2.getText().toString()) ){
                    SQLiteDatabase baseDatos = (new BaseDatos(getContext(), "myBD", null, BaseDatos.versionBD)).getWritableDatabase();
                    ContentValues valores = new ContentValues();
                    valores.put("nombre", nombre.getText().toString());
                    valores.put("usuario", usuario.getText().toString());
                    valores.put("clave", clave1.getText().toString());

                    baseDatos.insert("usuarios", null, valores);
                    baseDatos.close();
                    Navigation.findNavController(v).popBackStack();

                }
                else {
                    Toast.makeText(getContext(), "Claves no coinciden", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }



}

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
import android.widget.Switch;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AgregarMovimiento extends Fragment {

    EditText descripcion;
    EditText monto;
    private Switch esRetiro;

    public AgregarMovimiento() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agregar_movimiento, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        descripcion = view.findViewById(R.id.textoDescripcion);
        monto = view.findViewById(R.id.textoMonto);
        esRetiro = view.findViewById(R.id.esRetiro);


        Button botonAgregarMovimiento = view.findViewById(R.id.botonAgregarMovimiento);

        botonAgregarMovimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( descripcion.getText().toString().isEmpty() || monto.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), getString(R.string.error_faltan_datos), Toast.LENGTH_SHORT).show();
                    return;
                }

                SQLiteDatabase baseDatos = (new BaseDatos(getContext(), BaseDatos.nombreBD, null, BaseDatos.versionBD)).getWritableDatabase();
                ContentValues valores = new ContentValues();

                valores.put("descripcion", descripcion.getText().toString());
                valores.put("monto", (esRetiro.isChecked()?-1:1)*Double.valueOf(monto.getText().toString()));
                valores.put("usuario", BaseDatos.usuarioID);

                baseDatos.insert("finanzas", null, valores);
                baseDatos.close();
                Navigation.findNavController(v).popBackStack();
            }
        });
    }
}

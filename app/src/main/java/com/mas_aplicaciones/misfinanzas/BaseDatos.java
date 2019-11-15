package com.mas_aplicaciones.misfinanzas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BaseDatos extends SQLiteOpenHelper {

    public static int versionBD  = 1;
    public static String nombreBD  = "misFinanzasBD";

    public static int usuarioID = -1;
    public static String nombreUsuario = "";

    private String sql_finanzas = "create table finanzas(" +
            "id integer primary key autoincrement," +
            "descripcion text," +
            "monto real," +
            "usuario integer" +
            ")";

    private String sql_usuarios = "create table usuarios(" +
            "id integer primary key autoincrement," +
            "nombre text," +
            "usuario text," +
            "clave text" +
            ")";

    public BaseDatos(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(sql_finanzas);
        db.execSQL(sql_usuarios);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists finanzas");
        db.execSQL("drop table if exists usuarios");
        db.execSQL(sql_finanzas);
        db.execSQL(sql_usuarios);
    }
}

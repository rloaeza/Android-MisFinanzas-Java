package com.mas_aplicaciones.misfinanzas;

public class Movimientos {
    private int id;
    private double monto;
    private String descripcion;
    private int usuario;

    public Movimientos(int id, String descripcion, double monto, int usuario) {
        this.id = id;
        this.monto = monto;
        this.descripcion = descripcion;
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s", descripcion, monto);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }
}

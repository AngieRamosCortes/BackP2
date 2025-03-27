package com.ecicredit.pagos.model;

import java.math.BigDecimal;

public class Articulo {
    private String nombre;
    private BigDecimal precioUnitario;
    private int cantidad;

    public String getNombre() {
        return nombre;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "nombre='" + nombre + '\'' +
                ", precioUnitario=" + precioUnitario +
                ", cantidad=" + cantidad +
                '}';
    }
}

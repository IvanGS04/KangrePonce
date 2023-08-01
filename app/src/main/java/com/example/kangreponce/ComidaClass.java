package com.example.kangreponce;

public class ComidaClass {
    private String nombre;
    private String ingredientes;
    private int precio;

    public ComidaClass(String nombre, String ingredientes, int precio) {
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIngredientes() {return ingredientes;}

    public int getPrecio() {
        return precio;
    }
}

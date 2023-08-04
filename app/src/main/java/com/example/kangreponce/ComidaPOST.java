package com.example.kangreponce;

public class ComidaPOST {
    private String nombre;
    private String ingredientes;
    private int precio;
    private String file;

    public ComidaPOST(String nombre, String ingredientes, int precio, String file) {
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.precio = precio;
        this.file = file;
    }
}

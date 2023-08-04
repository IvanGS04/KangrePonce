package com.example.kangreponce;

public class PostComida {
    private String nombre;
    private String ingredientes;
    private  int precio;
    private String file;

    public PostComida(String nombre, String ingredientes, int precio, String file) {
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.precio = precio;
        this.file = file;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public int getPrecio() {
        return precio;
    }

    public String getFile() {
        return file;
    }
}

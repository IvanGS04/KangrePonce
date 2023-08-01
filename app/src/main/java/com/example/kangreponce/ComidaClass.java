package com.example.kangreponce;

public class ComidaClass {
    private String nombre;
    private String ingredientes;
    private int precio;
    private String imgUrl;

    public ComidaClass(String nombre, String ingredientes, int precio, String imgUrl) {
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.precio = precio;
        this.imgUrl = imgUrl;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIngredientes() {return ingredientes;}

    public int getPrecio() {
        return precio;
    }

    public String getImgUrl() {return imgUrl;}
}

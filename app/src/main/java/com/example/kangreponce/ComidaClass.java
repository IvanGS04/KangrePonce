package com.example.kangreponce;

public class ComidaClass {
    private String _id;
    private String nombre;
    private String ingredientes;
    private int precio;

    private String imgUrl;

    public ComidaClass(String _id, String nombre, String ingredientes, int precio, String imgUrl) {
        this._id = _id;
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.precio = precio;
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return _id;
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

    public String getImgUrl() {
        return imgUrl;
    }
}

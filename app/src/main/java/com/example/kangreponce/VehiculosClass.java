package com.example.kangreponce;

public class VehiculosClass {

    private int _id;

    private String Marca;

    private String Modelo;

    private String Color;

    private int Precio;

    public VehiculosClass(int _id, String marca, String modelo, String color, int precio) {
        this._id = _id;
        Marca = marca;
        Modelo = modelo;
        Color = color;
        Precio = precio;
    }

    public VehiculosClass() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String modelo) {
        Modelo = modelo;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public int getPrecio() {
        return Precio;
    }

    public void setPrecio(int precio) {
        Precio = precio;
    }
}

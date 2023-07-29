package com.example.kangreponce;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class VehiculosClass {

    private String _id;
    private String Marca;
    private String Modelo;
    private String Color;
    private int Precio;
    private String Year;

    public VehiculosClass() {
    }

    public VehiculosClass(String _id, String marca, String modelo, String color, int precio, String year) {
        this._id = _id;
        this.Marca = marca;
        this.Modelo = modelo;
        this.Color = color;
        this.Precio = precio;
        this.Year = year;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
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

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }
}

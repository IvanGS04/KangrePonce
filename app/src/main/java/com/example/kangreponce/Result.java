package com.example.kangreponce;

public class Result {

    private String Marca;
    private String Modelo;
    private String Color;
    private int Precio;
    private float Year;

    public Result( String marca, String modelo, String color, int precio, float year) {

        Marca = marca;
        Modelo = modelo;
        Color = color;
        Precio = precio;
        Year = year;
    }

    public Result() {
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

    public float getYear() {
        return Year;
    }

    public void setYear(float year) {
        Year = year;
    }
}

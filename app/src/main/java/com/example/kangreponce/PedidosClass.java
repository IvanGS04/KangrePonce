package com.example.kangreponce;

public class PedidosClass {

    private String comida;
    private int precio;
    private String cliente;
    private String direccion;
    private String telefono;

    public PedidosClass(String comida, int precio, String cliente, String direccion, String telefono) {
        this.comida = comida;
        this.precio = precio;
        this.cliente = cliente;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public String getComida() {
        return comida;
    }

    public int getPrecio() {
        return precio;
    }

    public String getCliente() {
        return cliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }
}

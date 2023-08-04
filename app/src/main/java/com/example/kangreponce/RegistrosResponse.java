package com.example.kangreponce;

public class RegistrosResponse {
    private String Nombre;
    private String Apellido;
    private int Telefono;
    private String Correo;
    private String Password;

    public RegistrosResponse(String nombre, String apellido, int telefono, String correo, String password) {
        Nombre = nombre;
        Apellido = apellido;
        Telefono = telefono;
        Correo = correo;
        Password = password;
    }
}

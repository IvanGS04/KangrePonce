package com.example.kangreponce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroUsuarios extends AppCompatActivity {

    EditText txtNombre;

    EditText txtApellidos;

    EditText txtNumTelefono;

    EditText txtCorreoR;

    EditText txtPasswordR;

    Button btnRegistrar;

    ImageButton ImageButtonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellidos = findViewById(R.id.txtApellido);
        txtNumTelefono = findViewById(R.id.editTextPhone);
        txtCorreoR = findViewById(R.id.txtCorreoR);
        txtPasswordR = findViewById(R.id.txtPasswordR);
        btnRegistrar = findViewById(R.id.button2);
        ImageButtonBack = findViewById(R.id.ImageButtonBack);


        btnRegistrar.setOnClickListener(v -> {
            String Nombre = txtNombre.getText().toString();
            String Apellido = txtApellidos.getText().toString();
            int Telefono = Integer.parseInt(txtNumTelefono.getText().toString());
            String Correo = txtCorreoR.getText().toString();
            String Password = txtPasswordR.getText().toString();

            if(TextUtils.isEmpty(Nombre) || TextUtils.isEmpty(Apellido) || Integer.toString(Telefono).isEmpty() || TextUtils.isEmpty(Correo) || TextUtils.isEmpty(Password)) {
                Toast.makeText(RegistroUsuarios.this, "Rellena todos los campos vacios ", Toast.LENGTH_SHORT).show();
                return;
            }
   RegistrarUsuario(Nombre, Apellido, Telefono, Correo, Password);

        });

       ImageButtonBack.setOnClickListener(v -> {
           startActivity(new Intent(RegistroUsuarios.this, MainActivity.class));
       });

    }

    private void RegistrarUsuario(String Nombre, String Apellido, int Telefono, String Correo, String Password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.12:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Usuarios usuarios = retrofit.create(Usuarios.class);

        Call<RegistrosResponse> call = usuarios.createUsuario(new RegistrosResponse(Nombre, Apellido, Telefono, Correo, Password));

        call.enqueue(new Callback<RegistrosResponse>() {
            @Override
            public void onResponse(Call<RegistrosResponse> call, Response<RegistrosResponse> response) {
                if (response.isSuccessful()){
                    RegistrosResponse registrosResponse = response.body();
                    Toast.makeText(RegistroUsuarios.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistroUsuarios.this, MainActivity.class));
                }
                else {
                    Toast.makeText(RegistroUsuarios.this, "Error al registar ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegistrosResponse> call, Throwable t) {
                Toast.makeText(RegistroUsuarios.this, "Error en la solicitud al servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
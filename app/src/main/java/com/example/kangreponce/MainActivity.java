package com.example.kangreponce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    Button btnIniciar;

    EditText TxtCorreo;

    SharedPreferences sharedPreferences;

    EditText txtPassword;

    Switch recordar;

    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TxtCorreo = findViewById(R.id.txtCorreo);
        txtPassword = findViewById(R.id.txtPassword2);
        btnIniciar = findViewById(R.id.btnIniciar);
        recordar = findViewById(R.id.swRecordar);
        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        if (sharedPreferences.getBoolean("remind", false)) {
            if(sharedPreferences.contains("correo")) {
                String Correo = sharedPreferences.getString("correo", "");
                String Password = sharedPreferences.getString("password", "");
                TxtCorreo.setText(Correo);
                txtPassword.setText(Password);
                recordar.setChecked(true);
            }
        }

        btnIniciar.setOnClickListener(v -> {
            String Correo = TxtCorreo.getText().toString();
            String Password = txtPassword.getText().toString();

            if (TextUtils.isEmpty(Correo) || TextUtils.isEmpty(Password)) {
                Toast.makeText(MainActivity.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }
    loginUser(Correo,Password);

        });






        btnRegistrar.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RegistroUsuarios.class));
        });



    }

    private void loginUser(String Correo, String Password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.12:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Login login = retrofit.create(Login.class);

        Call<LoginResponse> call = login.loginUser(Correo,Password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    String mensaje = loginResponse.getMessage();
                    String status = response.body().getStatus();

                    if (mensaje.equals("login succesful") && status.equals("200")) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("correo", Correo);
                        editor.putString("password", Password);
                        editor.putBoolean("remind", recordar.isChecked());
                        if (Correo.equals("admin@correo.com")){
                            editor.putBoolean("admin", true);
                        }else{
                            editor.putBoolean("admin", false);
                        }
                        editor.apply();

                        startActivity(new Intent(MainActivity.this, Index.class));
                        finish();
                    } else if ("si no existe ".equals(mensaje)) {
                        Toast.makeText(MainActivity.this, "El usuario no existe", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Error al validar usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error en la solicitud al servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}